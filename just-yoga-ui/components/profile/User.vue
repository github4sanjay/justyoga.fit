<template>
  <v-row v-if="user" class="lightbox">
    <v-col cols="12" sm="3">
      <v-row justify="center">
        <v-badge
          :value="editable"
          @click.native="editProfilePic"
          avatar
          bordered
          overlap
          offset-x="35"
          offset-y="35"
        >
          <template v-slot:badge>
            <v-avatar>
              <v-icon medium>
                edit
              </v-icon>
            </v-avatar>
          </template>
          <v-avatar
            :size="$vuetify.breakpoint.mdAndUp ? 200 : 150"
            color="primary"
          >
            <v-img
              v-if="user.profilePic"
              :src="user.profilePic"
              :alt="user.name"
            />
            <span v-else-if="user.name" class="white--text headline">
              {{ user.name.charAt(0) }}
            </span>
            <span v-else class="white--text headline">JY</span>
          </v-avatar>
        </v-badge>
      </v-row>
    </v-col>
    <v-col cols="12" sm="9" class="text-center">
      <v-row ml="2" dense justify="center" align="center">
        <v-col cols="12">
          <div
            :class="{
              headline: $vuetify.breakpoint.mdAndUp,
              title: $vuetify.breakpoint.smAndDown,
              'font-weight-bold': true,
            }"
          >
            {{ user.name }}
            <v-btn
              @click.native="profileDialogue = !profileDialogue"
              v-show="editable"
              class="ma-2"
              x-small
            >
              Edit
            </v-btn>
          </div>
        </v-col>
        <!--<v-col cols="12">
          <v-btn class="ma-2" tile outlined>
            32 Following
          </v-btn>
          <v-btn class="ma-2" tile outlined>
            10 Follower
          </v-btn>
        </v-col>-->
        <v-col cols="12" class="px-5">
          <div class="font-weight-regular subtitle-2 font-italic">
            {{ user.description }}
          </div>
        </v-col>
        <v-col cols="12">
          <v-row v-if="!user.interest && editable" align="center" justify="center">
            <v-btn
              @click="interestDialogue = true"
              small
              color="success font-weight-bold"
            >
              What are you interested in ?
            </v-btn>
          </v-row>
        </v-col>
        <v-col v-if="user.interest" cols="11" class="pa-0 ma-0">
          <v-chip
            v-if="user.interest.blogger"
            label
            class="ma-2"
            color="green"
            text-color="white"
          >
            Blogger
            <v-icon right small>fas fa-chalkboard-teacher</v-icon>
          </v-chip>
          <v-chip
            label
            v-if="user.interest.trainer"
            class="ma-2"
            color="primary"
            text-color="white"
          >
            Yoga Trainer
            <v-icon right small>fas fa-user-tie</v-icon>
          </v-chip>
          <v-chip
            label
            v-if="user.interest.lookingForTrainer"
            class="ma-2"
            color="primary"
            text-color="white"
          >
            I am looking for a trainer
            <v-icon right>search</v-icon>
          </v-chip>
        </v-col>
        <v-col>
          <v-row v-if="user.interest && editable" align="center" justify="start">
            <v-tooltip
              content-class="black pa-2"
              max-width="400" top>
              <template v-slot:activator="{ on }">
                <v-btn
                  v-on="on"
                  @click="interestDialogue = true"
                  fab
                  x-small
                >
                  <v-icon x-small>mdi-pencil</v-icon>
                </v-btn>
              </template>
              <span>Edit Your Interest</span>
            </v-tooltip>
          </v-row>
        </v-col>
      </v-row>
    </v-col>
    <v-dialog width="400" v-model="profilePicDialogue">
      <v-card flat>
        <profile-pic-form
          :uploading="updatingProfilePic"
          @close="profilePicDialogue = !profilePicDialogue"
          @save="updateProfilePic"
        />
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="profileDialogue">
      <v-card flat>
        <user-form
          @close="profileDialogue = !profileDialogue"
          :updating="updatingUser"
          :user="user"
          @update="updateUser($event)"
        />
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="interestDialogue">
      <v-card flat>
        <interests-form
          @close="interestDialogue = !interestDialogue"
          :interests="user.interest ? user.interest : {}"
          :updating="updatingInterests"
          @update="updateInterest($event)"
        />
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import SingleImageUpload from "../utils/SingleImageUpload";
import UserForm from "./UserForm";
import ProfilePicForm from "./ProfilePicForm";
import InterestsForm from "./InterestsForm";
export default {
  name: "User",
  props: {
    user: {
      type: Object,
      required: true,
    },
    trainer: {
      type: Object,
    },
    editable: {
      type: Boolean,
      required: false,
    },
  },
  components: { InterestsForm, ProfilePicForm, UserForm, SingleImageUpload },
  data: function () {
    return {
      profileDialogue: false,
      altProfilePic: "/images/util/profile_picture_blank_portrait.png",
      isTrainerCheckBox: !!this.trainer,
      updatingUser: false,

      updatingProfilePic: false,
      profilePicDialogue: false,

      interestDialogue: false,
      updatingInterests: false,
    };
  },
  computed: {},
  methods: {
    editProfilePic(){
      if (this.editable){
        this.profilePicDialogue = true;
      }
    },
    updateUser(val) {
      this.updatingUser = true;
      this.$store
        .dispatch("users/updateUser", val)
        .then(() => {
          this.updatingUser = false;
          this.profileDialogue = false;
        })
        .catch(() => (this.updatingUser = false));
    },
    updateProfilePic(val) {
      this.updatingProfilePic = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("users/updateProfilePic", val)
        .then(() => {
          this.updatingProfilePic = false;
          this.profilePicDialogue = false;
        })
        .catch(() => (this.updatingProfilePic = false));
    },
    updateInterest(val) {
      this.updatingInterests = true;
      val.userId = this.user.id;
      if (this.user.interest) val.id = this.user.interest.id; // need id for update
      this.$store
        .dispatch("users/updateInterest", val)
        .then(() => {
          this.updatingInterests = false;
          this.interestDialogue = false;
        })
        .catch(() => (this.updatingInterests = false));
    },
  },
};
</script>

<style scoped></style>
