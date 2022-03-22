<template>
  <q-page>
    <div>
      <directory-trainer-section :model="sub" />
    </div>
  </q-page>
</template>

<script>
import DirectoryTrainerSection from "components/directory/TrainerSection";
import { Loading } from "quasar";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";

export default {
  name: "index",
  components: { DirectoryTrainerSection },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();

    let data = store.getters["directory/localityByPage"](
      currentRoute.params.locality,
      currentRoute.params.page
    );
    if (!data) {
      try {
        await store.dispatch("directory/get", {
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
      return this.$store.getters["directory/localityByPage"](
        this.$route.params.locality,
        this.$route.params.page
      );
    }
  },
  methods: {
    getTitle() {
      return `Find Yoga Trainers in ${this.locality.name}, ${this.state.name}, ${this.country.name} | JustYoga`;
    },
    getDesc() {
      return "Find the best yoga trainers accross the world on justyoga.fit. Trainers register themselves here.";
    },
    getUrl() {
      return `${config.uiUrl}/directory/${this.country.id}/${this.state.id}/${this.locality.id}/trainers/${this.$route.params.page}`;
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
        }/trainers/${+this.sub.page - 1}`;
      else null;
    },
    getNextLink() {
      if (this.sub.hasNext)
        return `${config.uiUrl}/directory/${this.country.id}/${this.state.id}/${
          this.locality.id
        }/trainers/${+this.sub.page + 1}`;
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
