<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Add Your Blog</q-toolbar-title>
      <q-btn flat label="Save" :loading="savingBlog" @click="submit()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-xs': $q.screen.lt.md,
        'q-pa-md': $q.screen.gt.sm
      }"
    >
      <div class="row items-center">
        <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
          <div>Title</div>
        </div>
        <div class="col-12 col-md-8">
          <q-input
            v-model="title"
            label="Title"
            maxlength="70"
            counter
            :rules="[
              val => !!val || '* Required',
              val => val.length <= 70 || 'Max 70 characters'
            ]"
            lazy-rules
          />
        </div>
      </div>

      <div>
        <editor
          :default="initialBlogContent"
          @input="content = $event"
          :clear="clearEditor"
          :placeholder="placeHolder"
        />
      </div>
      <!-- <div class="q-my-md">
        <multi-select-image
          :clear="clearImagesSelected"
          :max="maxBlogImages"
          :headline="blogImageHeadline"
          @max-exceed="maxImageExceed"
          @image-data="images = $event"
        />
      </div>
      <div>
        <div class="row">
          <div
            class="col-3 q-pa-sm"
            v-for="image in initialImages"
            :key="image.id"
          >
            <q-img :src="image.url" max-height="200">
              <q-btn
                class="absolute all-pointer-events"
                :size="$q.screen.lt.md ? 'sm' : 'md'"
                color="negative"
                style="top: 8px; right: 8px"
                dense
                round
                icon="delete_forever"
                @click="removeImage(image)"
              >
                <q-tooltip>Remove</q-tooltip>
              </q-btn>
            </q-img>
          </div>
        </div>
      </div>
      <div class="q-my-md">
        <multi-video-select
          :clear="clearImagesSelected"
          :max="maxBlogVideos"
          :headline="blogVideoHeadline"
          @max-exceed="maxVideoExceed"
          @video-data="videos = $event"
        />
      </div>
      <div>
        <div class="row">
          <div class="col-4" v-for="video in initialVideos" :key="video.id">
            <q-card class="row bg-grey-10">
              <q-card-section class="q-pa-none">
                <q-media-player
                  type="video"
                  background-color="black"
                  :source="video.url"
                >
                  <template v-slot:overlay>
                    <div class="row q-pa-xs justify-end">
                      <div class="col-2">
                        <q-btn
                          color="negative"
                          :size="$q.screen.lt.md ? 'sm' : 'md'"
                          dense
                          round
                          icon="delete_forever"
                          @click="removeVideo(video)"
                        />
                      </div>
                    </div>
                  </template>
                </q-media-player>
              </q-card-section>
            </q-card>
          </div>
        </div>
      </div> -->
    </div>
  </div>
</template>

<script>
import Editor from "components/base/Editor";
import MultiSelectImage from "components/base/MultiSelectImage";
import NotifyUtil from "../../utils/NotifyUtil";
import MultiVideoSelect from "components/base/MultiVideoSelect";
import HtmlUtil from "src/utils/HtmlUtil";

export default {
  name: "BlogForm",
  components: { Editor },
  props: {
    user: {
      type: Object,
      required: true
    },
    initialBlog: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      title: null,
      blog: null,
      content: {},
      maxBlogImages: 5,
      blogImageHeadline: "Add images to your blog",
      maxBlogVideos: 3,
      blogVideoHeadline: "Add videos to your blog",
      videos: null,
      images: null,
      placeHolder: "Please write your blog content here...",
      removeVideos: [],
      removeImages: [],
      initialImages: [],
      initialVideos: [],
      initialBlogContent: "",
      clearImagesSelected: false,
      clearEditor: false,
      savingBlog: false
    };
  },
  methods: {
    submit() {
      this.clearImagesSelected = false; // reset all child components so that clear can be triggered again
      this.clearEditor = false;
      if (this.blog) {
        this.update({
          title: this.title,
          content: this.content,
          images: this.images,
          videos: this.videos,
          removeImages: this.removeImages,
          removeVideos: this.removeVideos
        });
      } else {
        this.save({
          title: this.title,
          content: this.content,
          images: this.images,
          videos: this.videos,
          removeImages: this.removeImages,
          removeVideos: this.removeVideos
        });
      }
    },
    async save(val) {
      let title = val.title;
      let content = val.content;
      let videos = val.videos;
      let images = val.images;

      let text = content.replace(/(<\/?[^>]+(>|$)|&nbsp;|\s)/g, "");
      if (!text || text === "") {
        NotifyUtil.showError(`Blog text is required`);
        return;
      }
      this.savingBlog = true;
      try {
        let blog = await this.$store.dispatch("blogs/store", {
          blogTitle: this.title,
          userId: this.user.id,
          blogText: HtmlUtil.extractContent(content, true),
          blogContent: content,
          published: true
        });

        await this.$store.dispatch("blogs/storeImages", {
          blogId: blog.id,
          images: images
        });

        await this.$store.dispatch("blogs/storeVideos", {
          blogId: blog.id,
          videos: videos
        });
      } catch (e) {
        NotifyUtil.showError(`Something went wrong`);
      } finally {
        this.savingBlog = false;
        this.closeBtnClick();
        this.clear();
      }
    },
    async update(val) {
      let title = val.title;
      let content = val.content;
      let videos = val.videos;
      let images = val.images;
      let removeImages = val.removeImages;
      let removeVideos = val.removeVideos;

      let text = content.replace(/(<\/?[^>]+(>|$)|&nbsp;|\s)/g, "");
      if (!text || text === "") {
        NotifyUtil.showError(`Blog text is required`);
        return;
      }
      this.savingBlog = true;
      try {
        if (
          content !== this.blog.blogContent ||
          title !== this.blog.blogTitle
        ) {
          await this.$store.dispatch("blogs/store", {
            id: this.blog.id, // to update blog
            userId: this.blog.userId,
            blogText: HtmlUtil.extractContent(content, true),
            blogContent: content,
            published: true,
            blogTitle: this.title,
            coverUrl: this.blog.coverUrl,
            createAt: this.blog.createdAt,
            createBy: this.blog.createdBy
          });
        }
        if (images && images.length > 0) {
          // add if extra images
          await this.$store.dispatch("blogs/storeImages", {
            blogId: this.blog.id,
            images: images
          });
        }

        if (videos && videos.length > 0) {
          // add if extra videos
          await this.$store.dispatch("blogs/storeVideos", {
            blogId: this.blog.id,
            videos: videos
          });
        }

        if (removeVideos && removeVideos.length > 0) {
          // delete removed videos
          await this.$store.dispatch("blogs/deleteVideos", removeVideos);
        }

        if (removeImages && removeImages.length > 0) {
          // delete removed images
          await this.$store.dispatch("blogs/deleteImages", removeImages);
        }
      } catch (e) {
        NotifyUtil.showError(`Something went wrong`);
      } finally {
        this.savingBlog = false;
        this.closeBtnClick();
        this.clear();
      }
    },
    clear() {
      this.clearImagesSelected = true;
      this.removeVideos = [];
      this.removeImages = [];
      if (!this.initialBlog) {
        this.clearEditor = true;
      }
    },
    closeBtnClick() {
      this.$emit("close");
    },
    maxImageExceed() {
      NotifyUtil.showError(
        `Maximum of ${this.maxBlogImages} images can be added`
      );
    },
    maxVideoExceed() {
      NotifyUtil.showError(
        `Maximum of ${this.maxBlogVideos} videos can be added`
      );
    },
    removeVideo(video) {
      for (let j = 0; j < this.initialVideos.length; j++) {
        if (this.initialVideos[j].id === video.id) {
          this.initialVideos.splice(j, 1);
          break;
        }
      }
      this.removeVideos.push(video);
    },
    removeImage(image) {
      for (let j = 0; j < this.initialImages.length; j++) {
        if (this.initialImages[j].id === image.id) {
          this.initialImages.splice(j, 1);
          break;
        }
      }
      this.removeImages.push(image);
    }
  },
  created() {
    if (this.initialBlog) {
      this.blog = this.initialBlog;
      this.initialImages = this.blog.images.slice(); // do not mutate vuex directly
      this.initialVideos = this.blog.videos.slice(); // do not mutate vuex directly
      this.initialBlogContent = this.blog.blogContent;
      this.title = this.blog.blogTitle;
    }
  }
};
</script>

<style scoped></style>
