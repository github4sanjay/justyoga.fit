<template>
  <q-page>
    <div>
      <directory-video-section :model="sub" />
    </div>
  </q-page>
</template>

<script>
import DirectoryVideoSection from "components/directory/VideoSection";
import { Loading } from "quasar";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";

export default {
  name: "DirectoryBlogIndex",
  components: { DirectoryVideoSection },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let data = store.getters["directory_videos/localityByPage"](
      currentRoute.params.locality,
      currentRoute.params.page
    );
    if (!data) {
      try {
        await store.dispatch("directory_videos/get", {
          page: currentRoute.params.page,
          localityId: currentRoute.params.locality
        });
      } catch (e) {
        redirect("/error404");
      }
    }

    Loading.hide();
  },
  data: function() {
    return {
      locality: this.$store.getters["place/locality"](
        this.$route.params.locality
      ),
      state: this.$store.getters["place/administrativeAreaLevel1"](
        this.$route.params.state
      ),
      country: this.$store.getters["place/country"](this.$route.params.country)
    };
  },
  computed: {
    sub() {
      return this.$store.getters["directory_videos/localityByPage"](
        this.$route.params.locality,
        this.$route.params.page
      );
    }
  },
  methods: {
    getTitle() {
      return `Watch yoga videos shared by people in ${this.locality.name}, ${this.state.name}, ${this.country.name} | JustYoga`;
    },
    getDesc() {
      return "Watch yoga videos shared by people accross the world on justyoga.fit. Yoga trainers share videos here.";
    },
    getUrl() {
      return `${config.uiUrl}/directory/${this.country.id}/${this.state.id}/${this.locality.id}/videos/${this.$route.params.page}`;
    },
    getImage() {
      return Cms.DEFAULT_IMAGE_URL;
    },
    getKeyWords() {
      return `yoga, video, trainer, blogs, photos`;
    },
    getPrevLink() {
      if (this.sub.hasPrevious)
        return `${config.uiUrl}/directory/${this.country.id}/${this.state.id}/${
          this.locality.id
        }/videos/${+this.sub.page - 1}`;
      else null;
    },
    getNextLink() {
      if (this.sub.hasNext)
        return `${config.uiUrl}/directory/${this.country.id}/${this.state.id}/${
          this.locality.id
        }/videos/${+this.sub.page + 1}`;
      else null;
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
      "website",
      this.getPrevLink(),
      this.getNextLink()
    );
  }
};
</script>

<style scoped></style>
