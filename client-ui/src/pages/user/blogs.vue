<template>
  <q-page>
    <div class="row" v-if="blogs.totalElements > 0">
      <div class="col-12">
        <q-card flat square bordered>
          <q-card-actions>
            <div
              :class="{
                'text-bold': true
              }"
            >
              Blogs
              <q-badge
                color="accent"
                text-color="white"
                :label="blogs.totalElements"
              />
            </div>
            <q-space />
            <q-btn
              :disable="!blogs.hasPrevious"
              flat
              round
              color="primary"
              icon="fas fa-arrow-circle-left"
              :to="`${+blogs.page - 1}`"
            />
            <q-separator vertical />
            <q-btn
              :disable="!blogs.hasNext"
              flat
              round
              color="primary"
              icon="fas fa-arrow-circle-right"
              :to="`${+blogs.page + 1}`"
            />
          </q-card-actions>
        </q-card>
      </div>
      <div class="col-12 q-mt-md" v-for="(blog, i) in blogs.data" :key="i">
        <blog-card :blog-id="blog" />
      </div>
    </div>
    <div v-else>
      <div class="text-center q-mt-lg">
        <p>
          <img
            src="~assets/found-icon-20.jpg"
            style="width:30vw;max-width:150px;"
          />
        </p>
        <p class="text-weight-light">
          Have not shared anything yet <br />
          Share your yoga experience by writing blogs, uploading images or
          videos.
        </p>
      </div>
    </div>
  </q-page>
</template>

<script>
import { Loading } from "quasar";
import BlogCard from "components/user/BlogCard";

export default {
  name: "BlogPageIndex",
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let page = currentRoute.params.page ? currentRoute.params.page : 0;
    let data = store.getters["profile_blog/byPageAndUserId"](
      currentRoute.params.id,
      page
    );
    if (!data) {
      try {
        let blogs = await store.dispatch("profile_blog/get", {
          page: page,
          userId: currentRoute.params.id
        });
        let promises = [];
        blogs.forEach(blog => {
          promises.push(store.dispatch("blogs/getImages", { blogId: blog.id }));
          promises.push(store.dispatch("blogs/getVideos", { blogId: blog.id }));
        });
        await Promise.all(promises);
      } catch (e) {
        redirect("/error404");
      }
    }
    Loading.hide();
  },
  components: { BlogCard },
  data: function() {
    return {};
  },
  computed: {
    blogs() {
      return this.$store.getters["profile_blog/byPageAndUserId"](
        this.$route.params.id,
        this.$route.params.page
      );
    }
  }
};
</script>

<style scoped></style>
