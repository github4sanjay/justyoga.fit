<<template>
  <v-card class="justify-center elevation-0 transparent lighten-3">
    <v-container mt-2 pa-0>
      <v-row>
        <v-col cols="12" lg="8">
          <v-row>
            <v-col class="pa-0" v-if="editable && alertText">
              <one-time-alert :text="alertText" />
            </v-col>
            <v-col cols="12">
              <user :user="model" :editable="editable" />
            </v-col>
            <v-col cols="12" class="pa-0">
              <review-and-bookmark
                :editable="editable"
                :user="model"
                :currentUser="currentUser ? currentUser : {}"
              />
            </v-col>
            <v-col cols="12" class="pa-0">
              <v-tabs background-color="primaryLight" show-arrows>
                <v-tabs-slider color="primary"></v-tabs-slider>
                <v-tab
                  v-for="(item, index) in tabItems"
                  :key="index"
                  nuxt
                  router
                  :to="'/' + item.link"
                >
                  {{ item.text }}
                </v-tab>
              </v-tabs>
              <div>
                <nuxt-child :key="$route.fullPath"></nuxt-child>
              </div>
            </v-col>
          </v-row>
        </v-col>
        <v-col>
          <v-toolbar dark color="primary">
            <v-toolbar-title> Recommendations</v-toolbar-title>
          </v-toolbar>
          <v-card>
            <v-row align="center" justify="center">
              <v-col v-for="n in 2" :key="n">
                <v-card class="mx-auto" max-width="344">
                  <v-img
                    src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
                    height="200px"
                  ></v-img>
                  <v-card-title>
                    Browse Instructors
                  </v-card-title>
                  <v-card-subtitle>
                    Explore thousands of yoga instructors who are registered
                    here
                  </v-card-subtitle>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="secondary" text>
                      Explore
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import SingleImageUpload from "@/components/utils/SingleImageUpload";
import images from "@/constants/images";
import CoverPic from "@/components/utils/CoverPic";
import UserForm from "@/components/profile/UserForm";
import { mapGetters } from "vuex";
import User from "@/components/profile/User";
import ReviewAndBookmark from "@/components/profile/ReviewAndBookmark";
import OneTimeAlert from "@/components/base/OneTimeAlert";

export default {
  name: "profileHome",
  components: {
    OneTimeAlert,
    ReviewAndBookmark,
    User,
    UserForm,
    SingleImageUpload,
    CoverPic,
  },
  middleware: "profile",
  data: function () {
    return {
      profilePicDialogue: false,
      profileDialogue: false,
      coverPicWidth: 755,
      coverPicHeight: 300,
      altProfilePic: "/images/util/profile_picture_blank_portrait.png",
      isTrainerCheckBox: false,
      CLASSROOM_BANNER: images.CLASSROOM_BANNER,
      alert: true,
    };
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user",
    }),
    model() {
      return this.$store.getters["users/user"](this.$route.params.id);
    },
    editable() {
      return (
        this.currentUser && this.model && this.currentUser.id === this.model.id
      );
    },
    tabItems() {
      let id = this.$route.params.id;
      let page = this.$route.params.page ? this.$route.params.page : 0;
      let tabItems = [
        { text: "Details", link: "profile/" + id + "/details" },
        { text: "Expertise", link: "profile/" + id + "/expertise" },
        { text: "Images", link: `profile/${id}/images/${page}` },
        { text: "Videos", link: `profile/${id}/videos/${page}` },
        { text: "Bookmarks", link: "profile/" + id + "/bookmarks" },
        { text: "Reviews", link: `profile/${id}/reviews/${page}` },
      ];
      if (this.currentUser !== null && this.currentUser.id === this.model.id) {
        tabItems.push({ text: "Manage", link: "profile/" + id + "/admin" });
      }
      return tabItems;
    },
    alertText() {
      if (!this.model.interest) {
        return "Please update your interest !";
      } else if (!this.model.basicInfo) {
        return "Please update your basic information !";
      } else if (!this.model.profilePic) {
        return "Please update your profile picture !";
      } else if (!this.model.images || this.model.images.length === 0) {
        return "Share your yoga experience by uploading images !";
      } else if (!this.model.videos || this.model.videos.length === 0) {
        return "Share your yoga experience by uploading videos !";
      } else if (this.model.interest && this.model.interest.trainer) {
        if (
          (!this.model.medicalExpertise ||
            this.model.medicalExpertise.length === 0) &&
          (!this.model.yogaExpertise ||
            this.model.yogaExpertise.length === 0) &&
          (!this.model.yogaCertificates ||
            this.model.yogaCertificates.length === 0)
        ) {
          return "Add your expertise so that you stand out to users !";
        }
      }

      return null;
    },
  },
  methods: {},
  created() {},
  mounted() {},
  head() {
    let model = this.model;
    let basicInfo = this.model.basicInfo;
    return {
      title: `${model.name} | Yoga ${basicInfo ? "Trainer in " : "Blogger"} ${
        basicInfo ? basicInfo.formattedAddress : ""
      } | Home`,
      meta: [
        {
          hid: `description`,
          name: "description",
          content: `${model.name} - ${model.description}`,
        },
        {
          hid: `keywords`,
          name: "keywords",
          keywords: `${model.name},details,rating,reviews,yoga,feeds,
            blog,contact,yoga classes`,
        },
      ],
    };
  },
};
</script>
>
