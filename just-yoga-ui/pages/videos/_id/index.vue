<<template>
  <v-container pa-0>
    <v-row>
      <v-col cols="12" lg="8">
        <v-row>
          <v-col cols="12" class="pa-0">
            <client-only>
              <div id="vue-core-video-player-box" class="example-player">
                <vue-core-video-player
                  :cover="video.coverPic"
                  :src="video.url"
                  :loop="false"
                  :autoplay="false"
                />
              </div>
            </client-only>
          </v-col>
          <v-col cols="12" class="pa-0">
            <v-card>
              <user-header :user="user"/>
              <v-divider/>
              <v-card-text>
                <div class="font-weight-bold grey--text title mb-2">
                  {{ video.title }}
                </div>
                <div class="font-weight-light title mb-2">
                  {{ video.description }}
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col>
        <v-toolbar dark color="primary">
          <v-toolbar-title> Recommendations</v-toolbar-title>
        </v-toolbar>
        <v-card class="pa-3">
          <v-row align="center" justify="center" v-show="user.videos">
            <v-col cols="6" v-for="(video, i) in user.videos" :key="i">
              <video-card :video="video" />
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import VideoCard from "@/components/base/VideoCard";
import UserHeader from "../../../components/base/UserHeader";

export default {
  name: "videoHome",
  components: {UserHeader, VideoCard },
  middleware: "video",
  asyncData({ store, params }) {
    let video = store.getters["videos/byId"](params.id);
    let user = store.getters["users/user"](video.userId);
    return {
      video,
      user,
    };
  },
  data: function () {
    return {};
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user",
    }),
    /*editable() {
      return (
        this.currentUser && this.model && this.currentUser.id === this.model.id
      );
    },*/
  },
  methods: {
    /*updateUser(val) {
      this.updatingUser = true;
      this.$store.dispatch("users/updateUser", val).then(() => {
        this.updatingUser = false;
        this.profileDialogue = false;
      });
    },
    toggleProfileDialogue() {
      this.profileDialogue = !this.profileDialogue;
    },
    toggleProfilePicDialogue() {
      this.profilePicDialogue = !this.profilePicDialogue;
    },*/
  },
  created() {},
  mounted() {},
  head() {
    let video = this.video;
    let basicInfo = this.user.basicInfo;
    let user = this.user;
    return {
      title: `${video.title} | Video | ${user.name} | Yoga ${
        basicInfo ? "Trainer in " : "Blogger"
      } ${basicInfo ? basicInfo.formattedAddress : ""}`,
      meta: [
        {
          hid: `description`,
          name: "description",
          content: `${video.description}`,
        },
        {
          hid: `keywords`,
          name: "keywords",
          keywords: `${video.title},yoga videos,rating,reviews,yoga,feeds,
            blog,contact,yoga classes, yoga expertise, yoga, medical expertise`,
        },
      ],
    };
  },
};
</script>
>
