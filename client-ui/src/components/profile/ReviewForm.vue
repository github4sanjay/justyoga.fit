<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Add Your Review</q-toolbar-title>
      <q-btn flat label="Save" :loading="savingReview" @click="submit()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-xs': $q.screen.lt.md,
        'q-pa-md': $q.screen.gt.sm
      }"
    >
      <div class="q-mb-sm">
        <rating
          @rating="rating = $event"
          :default-value="defaultRating"
          :clear="clearRating"
        />
      </div>
      <div>
        <editor
          :default="initialReviewContent"
          @input="content = $event"
          :clear="clearEditor"
          :placeholder="placeHolder"
        />
      </div>
      <div class="q-my-md">
        <multi-select-image
          :clear="clearImagesSelected"
          :max="maxReviewImages"
          :headline="reviewImageHeadline"
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
                <q-tooltip>
                  Remove
                </q-tooltip>
              </q-btn>
            </q-img>
          </div>
        </div>
      </div>
      <div class="q-my-md">
        <multi-video-select
          :clear="clearImagesSelected"
          :max="maxReviewVideos"
          :headline="reviewVideoHeadline"
          @max-exceed="maxVideoExceed"
          @video-data="videos = $event"
        />
      </div>
      <div>
        <div class="row">
          <div class="col-4" v-for="video in initialVideos" :key="video.id">
            <q-card class="row bg-grey-10">
              <!-- <q-card-actions>
                <q-space />
                <q-btn
                  color="negative"
                  size="xs"
                  icon="fa fa-trash-alt"
                  fab
                  x-small
                  @click="removeVideo(video)"
                />
              </q-card-actions> -->
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
                      <!-- <img
                        src="~assets/white_logo_only.png"
                        style="width: 30vw; max-width: 50px; opacity: 0.25;"
                      /> -->
                    </div>
                  </template>
                </q-media-player>
                <!-- <div id="vue-core-video-player-box" class="example-player">
                  <vue-core-video-player
                    :src="video.url"
                    :autoplay="false"
                    :logo="require('@/assets/white_logo_only.png')"
                    :loop="false"
                  />
                </div> -->
              </q-card-section>
            </q-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Editor from "components/base/Editor";
import MultiSelectImage from "components/base/MultiSelectImage";
import NotifyUtil from "../../utils/NotifyUtil";
import MultiVideoSelect from "components/base/MultiVideoSelect";
import Rating from "../base/Rating";
import HtmlUtil from "src/utils/HtmlUtil";

export default {
  name: "ReviewForm",
  components: { Rating, Editor, MultiSelectImage, MultiVideoSelect },
  props: {
    currentUser: {
      type: Object,
      required: true
    },
    user: {
      type: Object,
      required: true
    },
    initialReview: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      review: null,
      content: {},
      maxReviewImages: 5,
      reviewImageHeadline: "Add images to your review",
      maxReviewVideos: 3,
      reviewVideoHeadline: "Add videos to your review",
      rating: 0,
      defaultRating: 0,
      videos: null,
      images: null,
      placeHolder: "Please write your review here...",
      removeVideos: [],
      removeImages: [],
      initialImages: [],
      initialVideos: [],
      initialReviewContent: "",
      clearImagesSelected: false,
      clearRating: false,
      clearEditor: false,
      savingReview: false
    };
  },
  methods: {
    submit() {
      this.clearImagesSelected = false; // reset all child components so that clear can be triggered again
      this.clearRating = false;
      this.clearEditor = false;
      if (this.review) {
        this.update({
          content: this.content,
          images: this.images,
          videos: this.videos,
          rating: this.rating,
          removeImages: this.removeImages,
          removeVideos: this.removeVideos
        });
      } else {
        this.save({
          content: this.content,
          images: this.images,
          videos: this.videos,
          rating: this.rating,
          removeImages: this.removeImages,
          removeVideos: this.removeVideos
        });
      }
    },
    async save(val) {
      let rating = val.rating;
      let content = val.content;
      let videos = val.videos;
      let images = val.images;
      if (rating === 0) {
        NotifyUtil.showError(`Rating is required`);
        return;
      }
      let text = content.replace(/(<\/?[^>]+(>|$)|&nbsp;|\s)/g, "");
      if (!text || text === "") {
        NotifyUtil.showError(`Review text is required`);
        return;
      }
      this.savingReview = true;
      try {
        let review = await this.$store.dispatch("reviews/store", {
          userId: this.user.id,
          reviewedBy: this.currentUser.id,
          rating: rating,
          reviewText: HtmlUtil.extractContent(content, true),
          reviewContent: content,
          published: true
        });
        await this.$store.dispatch("reviews/storeImages", {
          reviewId: review.id,
          images: images
        });

        await this.$store.dispatch("reviews/storeVideos", {
          reviewId: review.id,
          videos: videos
        });
      } catch (e) {
        NotifyUtil.showError(`Something went wrong`);
      } finally {
        this.savingReview = false;
        this.closeBtnClick();
        this.clear();
      }
    },
    async update(val) {
      let rating = val.rating;
      let content = val.content;
      let videos = val.videos;
      let images = val.images;
      let removeImages = val.removeImages;
      let removeVideos = val.removeVideos;
      if (rating === 0) {
        NotifyUtil.showError(`Rating is required`);
        return;
      }
      let text = content.replace(/(<\/?[^>]+(>|$)|&nbsp;|\s)/g, "");
      if (!text || text === "") {
        NotifyUtil.showError(`Review text is required`);
        return;
      }
      this.savingReview = true;
      try {
        if (
          content !== this.review.reviewContent ||
          rating !== this.review.rating
        ) {
          await this.$store.dispatch("reviews/store", {
            id: this.review.id, // to update review
            userId: this.review.userId,
            reviewedBy: this.currentUser.id,
            rating: rating,
            reviewText: HtmlUtil.extractContent(content, true),
            reviewContent: content,
            published: true
          });
        }
        if (images && images.length > 0) {
          // add if extra images
          await this.$store.dispatch("reviews/storeImages", {
            reviewId: this.review.id,
            images: images
          });
        }

        if (videos && videos.length > 0) {
          // add if extra videos
          await this.$store.dispatch("reviews/storeVideos", {
            reviewId: this.review.id,
            videos: videos
          });
        }

        if (removeVideos && removeVideos.length > 0) {
          // delete removed videos
          await this.$store.dispatch("reviews/deleteVideos", removeVideos);
        }

        if (removeImages && removeImages.length > 0) {
          // delete removed images
          await this.$store.dispatch("reviews/deleteImages", removeImages);
        }
      } catch (e) {
        NotifyUtil.showError(`Something went wrong`);
      } finally {
        this.savingReview = false;
        this.closeBtnClick();
        this.clear();
      }
    },
    clear() {
      this.clearImagesSelected = true;
      this.removeVideos = [];
      this.removeImages = [];
      if (!this.initialReview) {
        this.clearRating = true;
        this.clearEditor = true;
      }
    },
    closeBtnClick() {
      this.$emit("close");
    },
    maxImageExceed() {
      NotifyUtil.showError(
        `Maximum of ${this.maxReviewImages} images can be added`
      );
    },
    maxVideoExceed() {
      NotifyUtil.showError(
        `Maximum of ${this.maxReviewVideos} videos can be added`
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
    if (this.initialReview) {
      this.review = this.initialReview;
      this.initialImages = this.review.images.slice(); // do not mutate vuex directly
      this.initialVideos = this.review.videos.slice(); // do not mutate vuex directly
      this.defaultRating = this.review.rating;
      this.initialReviewContent = this.review.reviewContent;
    }
  }
};
</script>

<style scoped></style>
