<<template>
  <v-container pa-0>
    <v-row>
      <v-col cols="12" lg="8">
        <v-row>
          <v-col cols="12" class="pa-0">
            <v-img :src="image.url">
              <template v-slot:placeholder>
                <v-row class="fill-height ma-0" align="center" justify="center">
                  <v-progress-circular
                    indeterminate
                    color="grey lighten-5"
                  ></v-progress-circular>
                </v-row>
              </template>
            </v-img>
          </v-col>
          <v-col cols="12" class="pa-0">
            <v-card>
              <user-header :user="user" />
              <v-divider />
              <v-card-text>
                <div class="font-weight-bold grey--text title mb-2">
                  {{ image.title }}
                </div>
                <div class="font-weight-light title mb-2">
                  {{ image.description }}
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col>
        <v-toolbar dark color="primary">
          <v-toolbar-title> More here</v-toolbar-title>
        </v-toolbar>
        <v-card class="pa-3">
          <v-row align="center" justify="center" v-show="user.images">
            <v-col cols="6" v-for="(image, i) in user.images" :key="i">
              <image-card :image="image" />
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
import UserHeader from "@/components/base/UserHeader";
import ImageCard from "@/components/base/ImageCard";
import ImageLoader from "@/components/base/ImageLoader";

export default {
  name: "videoHome",
  components: {ImageLoader, ImageCard, UserHeader, VideoCard },
  middleware: "image",
  asyncData({ store, params }) {
    let image = store.getters["images/byId"](params.id);
    let user = store.getters["users/user"](image.userId);
    return {
      image,
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
  },
  methods: {},
  created() {},
  mounted() {},
  head() {
    let image = this.image;
    let basicInfo = this.user.basicInfo;
    let user = this.user;
    return {
      title: `${image.title} | Video | ${user.name} | Yoga ${
        basicInfo ? "Trainer in " : "Blogger"
      } ${basicInfo ? basicInfo.formattedAddress : ""}`,
      meta: [
        {
          hid: `description`,
          name: "description",
          content: `${image.description}`,
        },
        {
          hid: `keywords`,
          name: "keywords",
          keywords: `${image.title},yoga videos,rating,reviews,yoga,feeds,
            blog,contact,yoga classes, yoga expertise, yoga, medical expertise`,
        },
      ],
    };
  },
};
</script>
>
