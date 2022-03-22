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
          <q-breadcrumbs-el label="Photos" :to="`/users/${user.id}/images/0`" />
          <q-breadcrumbs-el :label="image.title" :to="`/images/${image.id}`" />
        </q-breadcrumbs>
      </div>
      <div class="col-sm-12 col-md-10 col-lg-8 q-mb-md">
        <q-card square flat bordered>
          <q-img
            spinner-color="primary"
            class="bg-dark"
            contain
            :src="image.url"
            :style="`max-height: ${$q.screen.gt.sm ? '600px' : '300px'};`"
          />
          <q-item>
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
              {{ image.description }}
            </div>
          </q-card-section>
        </q-card>
      </div>
      <div
        class="col-sm-12 col-md-10 col-lg-8 q-mb-md q-mt-md"
        v-if="user.images && user.images.length > 0"
      >
        <div class="row justify-center text-center items-center">
          <div class="col-3"><q-separator /></div>
          <div class="col-auto q-px-md text-bold">Browse More</div>
          <div class="col-3"><q-separator /></div>
        </div>
        <q-card square flat>
          <q-card-section class="q-pa-none">
            <div class="row justify-center">
              <div
                class="col-12 col-sm-6 col-md-4 col-lg-3 q-pa-sm"
                v-for="(image, i) in user.images"
                :key="i"
              >
                <image-card :image="image" />
              </div>
            </div>
          </q-card-section>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script>
import { mapGetters } from "vuex";
import { Loading } from "quasar";
import TimeUtil from "src/utils/TimeUtil";
import UserImage from "components/base/UserImage";
import ImageCard from "components/user/ImageCard";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";

export default {
  name: "ImageHome",
  components: {
    ImageCard,
    UserImage
  },
  middleware: "image",
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let image = store.getters["images/byId"](currentRoute.params.id);
    if (image === null || image === undefined) {
      try {
        let image = await store.dispatch("images/get", {
          id: currentRoute.params.id
        });
        let user = store.getters["users/user"](image.userId);
        if (user === null || user === undefined) {
          await store.dispatch("users/storeUser", { id: image.userId });
        }
      } catch (e) {
        redirect("/error404");
      }
    }
    Loading.hide();
  },
  data: function() {
    return {};
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    image() {
      return this.$store.getters["images/byId"](this.$route.params.id);
    },
    user() {
      if (this.image) {
        return this.$store.getters["users/user"](this.image.userId);
      } else {
        return null;
      }
    },
    time() {
      if (this.image) {
        return TimeUtil.timeDifference(this.image.updatedAt);
      } else {
        return "";
      }
    }
  },
  methods: {
    getTitle() {
      return this.image.title;
    },
    getDesc() {
      return this.image.description;
    },
    getUrl() {
      return `${config.uiUrl}/images/${this.image.id}`;
    },
    getImage() {
      if (this.image.url) {
        return this.image.url;
      } else {
        return Cms.DEFAULT_IMAGE_URL;
      }
    },
    getKeyWords() {
      return `yoga, image, trainer`;
    }
  },
  meta() {
    let title = this.getTitle();
    let description = this.getDesc();
    let url = this.getUrl();
    let image = this.getImage();
    let keywords = this.getKeyWords();
    return MetaUtil.getMeta(
      title,
      description,
      url,
      image,
      keywords,
      null,
      "image"
    );
  }
};
</script>
>
