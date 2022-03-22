<template>
  <v-img :src="initialImage ? initialImage : placeHolder" :aspect-ratio="2">
    <v-container fill-height class="align-end justify-center pa-0">
      <v-row>
        <v-col xs="12" class="pa-0">
          <v-row class="lightbox white--text" align="center" justify="center">
            <v-col sm="2">
              <v-row justify="center">
                <v-badge overlap color="transparent">
                  <v-avatar size="75" color="indigo">
                    <v-img
                      v-if="var_user.profilePic"
                      :src="var_user.profilePic"
                      :alt="var_user.name"
                    >
                      <v-container>
                        <v-row justify="end">
                          <v-tooltip top>
                            <template v-slot:activator="{ on }">
                              <v-icon
                                v-if="editable"
                                @click="
                                  profilePicDialogue = !profilePicDialogue
                                "
                                slot="badge"
                                dark
                                medium
                                v-on="on"
                              >
                                edit
                              </v-icon>
                            </template>
                            <span>Edit Profile Pic</span>
                          </v-tooltip>
                        </v-row>
                      </v-container>
                    </v-img>
                    <span
                      v-else-if="var_user.name"
                      class="white--text headline"
                      >{{ var_user.name.charAt(0) }}</span
                    >
                    <span v-else class="white--text headline">JS</span>
                  </v-avatar>
                </v-badge>
              </v-row>
            </v-col>
            <v-col sm="9">
              <v-row ml="2">
                <v-col>
                  <div class="white--text">
                    <h4>{{ var_user.name }}</h4>
                    <div style="font-size: 12px;">
                      Following 32, Follower 32
                    </div>
                  </div>
                </v-col>
              </v-row>
            </v-col>
            <v-col sm="1" v-if="editable">
              <v-row>
                <v-tooltip top>
                  <template v-slot:activator="{ on }">
                    <v-btn
                      text
                      fab
                      dark
                      v-on="on"
                      small
                      @click="coverPicDialogue = !coverPicDialogue"
                    >
                      <v-icon>edit</v-icon>
                    </v-btn>
                  </template>
                  <span>Edit Cover</span>
                </v-tooltip>
              </v-row>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <v-dialog width="400" v-model="profilePicDialogue">
      <v-card>
        <v-card-actions>
          <v-card-title>
            <div class="font-weight-bold title">
              Edit Profile Picture
            </div>
          </v-card-title>
          <v-spacer></v-spacer>
          <v-btn
            color="info"
            @click="uploadProfilePic"
            :loading="uploadingProfilePic"
            :disabled="uploadingProfilePic"
          >
            Upload
          </v-btn>
        </v-card-actions>
        <v-divider></v-divider>
        <v-layout row wrap pa-3>
          <single-image-upload :ratio="1" @image-data="profilePicData = $event">
          </single-image-upload>
        </v-layout>
      </v-card>
    </v-dialog>
    <v-dialog width="700" v-model="coverPicDialogue">
      <v-card>
        <v-card-actions>
          <v-card-title>
            <div class="font-weight-bold title">
              Edit Cover Picture
            </div>
          </v-card-title>
          <v-spacer></v-spacer>
          <v-btn
            color="info"
            @click="uploadCoverPic"
            :loading="uploadingCoverPic"
            :disabled="uploadingCoverPic"
          >
            Upload
          </v-btn>
        </v-card-actions>
        <v-divider></v-divider>
        <v-row wrap pa="3">
          <single-image-upload :ratio="2" @image-data="coverPicData = $event">
          </single-image-upload>
        </v-row>
      </v-card>
    </v-dialog>
  </v-img>
</template>

<script>
import SingleImageUpload from "@/components/utils/SingleImageUpload";
import ApiEndpoints from "@/constants/ApiEndpoints";
import axios from "axios";

export default {
  name: "CoverPic",
  components: { SingleImageUpload },
  props: {
    initialImage: {
      type: String,
      required: false,
      default: "/images/util/choose_image_placeholder.png",
    },
    user: {
      type: Object,
      required: true,
    },
    editable: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  data() {
    return {
      placeHolder: "/images/util/choose_image_placeholder.png",
      maxWidth: 700,
      maxHeight: 650,
      profilePicDialogue: false,
      coverPicDialogue: false,
      coverPicData: null,
      profilePicData: null,
      uploadingProfilePic: false,
      uploadingCoverPic: false,
      var_user: null,
    };
  },
  created() {
    this.var_user = this.user;
  },
  methods: {
    uploadProfilePic: async function () {
      if (!this.profilePicData) {
        await this.$store.dispatch(
          "shared/setErrorText",
          "Please choose a pic"
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
        return;
      }
      const formData = new FormData();
      formData.append("profilePic", this.profilePicData.blob);
      this.uploadingProfilePic = true;
      const configData = {
        url:
          ApiEndpoints.USERS +
          `/${this.var_user.id}` +
          ApiEndpoints.USER_PROFILE,
        method: "post",
        data: formData,
      };
      try {
        let { data } = await axios.request(configData);
        await this.$store.dispatch("users/updateProfilePic", {
          id: this.var_user.id,
          profilePic: data,
        });
        await this.$store.dispatch("login/updateProfilePic", data);
        this.var_user.profilePic = data;
      } catch (e) {
        await this.$store.dispatch(
          "shared/setErrorText",
          "Something went wrong"
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
      } finally {
        this.uploadingProfilePic = false;
      }
    },
    uploadCoverPic: async function () {
      if (!this.coverPicData) {
        await this.$store.dispatch(
          "shared/setErrorText",
          "Please choose a pic"
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
        return;
      }
      const formData = new FormData();
      formData.append("coverPic", this.coverPicData.blob);
      this.uploadingCoverPic = true;
      const configData = {
        url:
          ApiEndpoints.USERS + `/${this.var_user.id}` + ApiEndpoints.USER_COVER,
        method: "post",
        data: formData,
      };
      try {
        let { data } = await axios.request(configData);
        await this.$store.dispatch("users/updateCoverPic", {
          id: this.var_user.id,
          coverPic: data,
        });
        await this.$store.dispatch("login/updateCoverPic", data);
        this.var_user.coverPic = data;
      } catch (e) {
        await this.$store.dispatch(
          "shared/setErrorText",
          "Something went wrong"
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
      } finally {
        this.uploadingCoverPic = false;
      }
    },
  },
};
</script>

<style scoped>
.lightbox {
  box-shadow: 0 0 20px inset rgba(0, 0, 0, 0.2);
  background-image: linear-gradient(
    to top,
    rgba(0, 0, 0, 0.4) 0%,
    transparent 72px
  );
}
</style>
