<template>
  <q-page>
    <div>
      <directory-blog-section :model="sub" />
    </div>
  </q-page>
</template>

<script>
import DirectoryBlogSection from "components/directory/BlogSection";
import { Loading } from "quasar";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";

export default {
  name: "DirectoryBlogs",
  components: {
    DirectoryBlogSection
  },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let data = store.getters["directory_blogs/byPage"](
      currentRoute.params.page
    );
    if (!data) {
      try {
        await store.dispatch("directory_blogs/get", {
          page: currentRoute.params.page
        });
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
    sub() {
      return this.$store.getters["directory_blogs/byPage"](
        this.$route.params.page
      );
    }
  },
  methods: {
    getTitle() {
      return `Read yoga blogs shared by people accross the world | JustYoga`;
    },
    getDesc() {
      return "Read yoga blogs shared by people accross the world on justyoga.fit. Yoga trainers share blogs and articles here.";
    },
    getUrl() {
      return `${config.uiUrl}/directory/blogs/${this.$route.params.page}`;
    },
    getImage() {
      return Cms.DEFAULT_IMAGE_URL;
    },
    getKeyWords() {
      return `yoga, video, trainer, blogs, photos`;
    },
    getPrevLink() {
      if (this.sub.hasPrevious)
        return `${config.uiUrl}/directory/blogs/${+this.sub.page - 1}`;
      else null;
    },
    getNextLink() {
      if (this.sub.hasNext)
        return `${config.uiUrl}/directory/blogs/${+this.sub.page + 1}`;
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
