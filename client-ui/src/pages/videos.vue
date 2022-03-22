<template>
  <q-page :padding="$q.screen.gt.xs">
    <div class="row justify-center">
      <div
        :class="{
          'col-sm-12': true,
          'col-md-10': true,
          'col-lg-8': true,
          'q-mb-md': true,
          'q-mt-sm': $q.screen.xs
        }"
      >
        <q-breadcrumbs>
          <q-breadcrumbs-el
            :label="user.name"
            :to="`/users/${user.id}/detail`"
          />
          <q-breadcrumbs-el label="Videos" :to="`/users/${user.id}/videos/0`" />
          <q-breadcrumbs-el :label="video.title" :to="`/videos/${video.id}`" />
        </q-breadcrumbs>
      </div>
      <div class="col-sm-12 col-md-10 col-lg-8">
        <q-card square flat bordered>
          <q-media-player
            type="video"
            background-color="black"
            :show-big-play-button="true"
            :source="video.url"
            track-language="English"
            :poster="video.coverPic"
          >
            <template v-slot:overlay>
              <div class="q-pa-sm">
                <img
                  src="~assets/white_logo_only.png"
                  style="width: 30vw; max-width: 50px; opacity: 1;"
                />
              </div>
            </template>
          </q-media-player>
          <q-item v-if="user">
            <q-item-section avatar>
              <q-avatar color="primary" text-color="white">
                <user-image :user="user" />
              </q-avatar>
            </q-item-section>

            <q-item-section>
              <q-item-label>{{ user.name }}</q-item-label>
              <q-item-label caption>
                {{ time }}
              </q-item-label>
            </q-item-section>
          </q-item>
          <q-card-section>
            <div class="text-weight-bold text-grey">
              {{ video.description }}
            </div>
          </q-card-section>
        </q-card>
      </div>
      <div
        class="col-sm-12 col-md-10 col-lg-8 q-mt-md"
        v-if="user.videos && user.videos.length > 0"
      >
        <div class="row justify-center text-center items-center">
          <div class="col-3"><q-separator /></div>
          <div class="col-auto q-px-sm text-bold">Browse More</div>
          <div class="col-3"><q-separator /></div>
        </div>
        <q-card square flat>
          <q-card-section class="q-pa-none">
            <div class="row justify-center">
              <div
                class="col-12 col-sm-6 col-md-4 col-lg-3 q-pa-sm"
                v-for="(video, i) in user.videos"
                :key="i"
              >
                <video-card :video="video" />
              </div>
            </div>
          </q-card-section>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script>
import { Loading } from "quasar";
import { mapGetters } from "vuex";
import TimeUtil from "src/utils/TimeUtil";
import VideoCard from "components/user/VideoCard";
import UserImage from "components/base/UserImage";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";

export default {
  name: "videoHome",
  components: { VideoCard, UserImage },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let video = store.getters["videos/byId"](currentRoute.params.id);
    if (video === null || video === undefined) {
      try {
        let video = await store.dispatch("videos/get", {
          id: currentRoute.params.id
        });
        let user = store.getters["users/user"](video.userId);
        if (user === null || user === undefined) {
          await store.dispatch("users/storeUser", { id: video.userId });
        }
      } catch (e) {
        redirect("/error404");
      }
    }
    Loading.hide();
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    video() {
      return this.$store.getters["videos/byId"](this.$route.params.id);
    },
    user() {
      if (this.video) {
        return this.$store.getters["users/user"](this.video.userId);
      } else {
        return null;
      }
    },
    time() {
      if (this.video) {
        return TimeUtil.timeDifference(this.video.updatedAt);
      } else {
        return "";
      }
    }
  },
  methods: {
    getTitle() {
      return this.video.title;
    },
    getDesc() {
      return this.video.description;
    },
    getUrl() {
      return `${config.uiUrl}/videos/${this.video.id}`;
    },
    getImage() {
      if (this.video.coverPic) {
        return this.video.coverPic;
      } else {
        return Cms.DEFAULT_IMAGE_URL;
      }
    },
    getVideo() {
      return this.video.url;
    },
    getKeyWords() {
      return `yoga, video, trainer`;
    }
  },
  meta() {
    let title = this.getTitle();
    let description = this.getDesc();
    let url = this.getUrl();
    let image = this.getImage();
    let keywords = this.getKeyWords();
    let video = this.getVideo();
    return MetaUtil.getMeta(
      title,
      description,
      url,
      image,
      keywords,
      video,
      "video"
    );
  }
};
</script>
