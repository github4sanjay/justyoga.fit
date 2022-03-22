<template>
  <q-page padding>
    <div class="row">
      <div class="col-12">
        <q-card
          square
          flat
          bordered
          :class="{ 'q-pa-none': true, 'q-mx-md': $q.screen.gt.xs }"
        >
          <q-card-actions class="q-pa-none">
            <q-card-section>
              <div class="text-h6">
                {{ collection.name }}
              </div>
            </q-card-section>
            <q-space />
            <q-btn
              v-if="isAdmin"
              class="q-pr-sm"
              size="sm"
              name="info"
              color="grey"
              dense
              flat
              round
              icon="fas fa-ellipsis-v"
            >
              <q-menu transition-show="jump-down" transition-hide="jump-up">
                <q-list style="min-width: 100px">
                  <q-item
                    clickable
                    v-for="(item, i) in items"
                    :key="i"
                    @click="onMenuClick(item)"
                  >
                    <q-item-section>{{ item.title }}</q-item-section>
                  </q-item>
                </q-list>
              </q-menu>
            </q-btn>
          </q-card-actions>
          <q-card-section class="q-pt-none">
            <div>
              {{ collection.description }}
            </div>
          </q-card-section>
          <q-separator />
          <q-card-section>
            <q-chip size="12px" square>
              <q-avatar color="secondary" text-color="white">{{
                collection.videos.length
              }}</q-avatar>
              Videos
            </q-chip>
            <q-chip size="12px" square>
              <q-avatar color="secondary" text-color="white">{{
                collection.images.length
              }}</q-avatar>
              Images
            </q-chip>
            <q-chip size="12px" square>
              <q-avatar color="secondary" text-color="white">{{
                collection.blogs.length
              }}</q-avatar>
              Blogs
            </q-chip>
          </q-card-section>
        </q-card>
      </div>
      <div class="col-12 q-mt-sm">
        <q-card square flat class="shadow-1">
          <div class="row">
            <div
              :class="{
                'col-12': true,
                'col-sm-6': true,
                'col-md-4': true,
                'col-lg-3': true,
                'q-pa-md': $q.screen.gt.xs,
                'q-py-sm': $q.screen.xs
              }"
              v-for="blog in collection.blogs"
              :key="blog.id"
            >
              <directory-blog-card :blog-info="blog" />
            </div>
            <div
              :class="{
                'col-12': true,
                'col-sm-6': true,
                'col-md-4': true,
                'col-lg-3': true,
                'q-pa-md': $q.screen.gt.xs,
                'q-py-sm': $q.screen.xs
              }"
              v-for="video in collection.videos"
              :key="video.id"
            >
              <directory-video-card :video-info="video" />
            </div>
            <div
              :class="{
                'col-12': true,
                'col-sm-6': true,
                'col-md-4': true,
                'col-lg-3': true,
                'q-pa-md': $q.screen.gt.xs,
                'q-py-sm': $q.screen.xs
              }"
              v-for="image in collection.images"
              :key="image.id"
            >
              <directory-image-card :image-info="image" />
            </div>
          </div>
        </q-card>
      </div>
    </div>
    <q-dialog v-model="addImageDialogue">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.xs ? '400px' : '300px'
        }"
      >
        <add-image-form
          :collection-id="collection.id"
          @close="addImageDialogue = false"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="addBlogDialogue">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.xs ? '400px' : '300px'
        }"
      >
        <add-blog-form
          :collection-id="collection.id"
          @close="addBlogDialogue = false"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="addVideoDialogue">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.xs ? '400px' : '300px'
        }"
      >
        <add-video-form
          :collection-id="collection.id"
          @close="addVideoDialogue = false"
        />
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script>
import { Loading } from "quasar";
import { mapGetters } from "vuex";
import ImageCard from "components/user/ImageCard";
import DirectoryImageCard from "components/directory/DirectoryImageCard";
import DirectoryVideoCard from "components/directory/DirectoryVideoCard";
import DirectoryBlogCard from "components/directory/DirectoryBlogCard";
import AddImageForm from "components/collection/AddImageForm";
import AddBlogForm from "components/collection/AddBlogForm";
import AddVideoForm from "components/collection/AddVideoForm";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";

export default {
  name: "CollectionDetailIndex",
  components: {
    DirectoryImageCard,
    DirectoryVideoCard,
    DirectoryBlogCard,
    AddImageForm,
    AddVideoForm,
    AddBlogForm
  },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    try {
      let collection = store.getters["collection/byId"](currentRoute.params.id);
      if (!collection) {
        collection = await store.dispatch("collection/get", {
          id: currentRoute.params.id
        });
      }
      let requests = [];
      if (
        collection &&
        !(Array.isArray(collection.images) && collection.images.length)
      ) {
        requests.push(
          store.dispatch("collection/getImages", { id: currentRoute.params.id })
        );
      }
      if (
        collection &&
        !(Array.isArray(collection.videos) && collection.videos.length)
      ) {
        requests.push(
          store.dispatch("collection/getVideos", { id: currentRoute.params.id })
        );
      }
      if (
        collection &&
        !(Array.isArray(collection.blogs) && collection.blogs.length)
      ) {
        requests.push(
          store.dispatch("collection/getBlogs", { id: currentRoute.params.id })
        );
      }
      await Promise.all(requests);
    } catch (error) {
      redirect("/error500");
    } finally {
      Loading.hide();
    }
  },
  data: function() {
    return {
      items: [
        { title: "Add Blog", CODE: "ADD_BLOG" },
        { title: "Add Image", CODE: "ADD_IMAGE" },
        { title: "Add Video", CODE: "ADD_VIDEO" }
      ],
      addImageDialogue: false,
      addVideoDialogue: false,
      addBlogDialogue: false
    };
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    collection() {
      return this.$store.getters["collection/byId"](this.$route.params.id);
    },
    isAdmin() {
      return this.$store.getters["login/isUserAdmin"];
    }
  },
  methods: {
    onMenuClick(val) {
      switch (val.CODE) {
        case "ADD_BLOG":
          this.addBlogDialogue = true;
          break;
        case "ADD_IMAGE":
          this.addImageDialogue = true;
          break;
        case "ADD_VIDEO":
          this.addVideoDialogue = true;
          break;
      }
    },
    getTitle() {
      return `${this.collection.name} | JustYoga`;
    },
    getDesc() {
      return this.collection.description;
    },
    getUrl() {
      return `${config.uiUrl}/collection-detail/${this.collection.id}`;
    },
    getImage() {
      if (this.collection.coverUrl) {
        return this.collection.coverUrl;
      } else {
        return Cms.DEFAULT_IMAGE_URL;
      }
    },
    getKeyWords() {
      return `yoga, image, trainer, blogs, articles, videos, photos`;
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

<style scoped></style>
