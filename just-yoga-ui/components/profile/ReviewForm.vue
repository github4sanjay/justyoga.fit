<template>
  <v-card fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Add Your Review</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="loading" @click="submit">
          Submit
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-card flat>
      <rating
        @rating="rating = $event"
        :default-value="defaultRating"
        :clear="clearRating"
      />
      <vue-editor
        :placeholder="placeHolder"
        @input="content = $event"
        :value="changedContent"
        :clear="clearEditor"
      />
      <image-select-and-preview
        :clear="clearImagesSelected"
        :max="maxReviewImages"
        :headline="reviewImageHeadline"
        @max-exceed="maxImageExceed"
        @image-data="images = $event"
      />
      <v-card-text>
        <v-row>
          <v-col cols="3" v-for="(image, i) in initialImages" :key="image.id">
            <v-img :src="image.url" max-height="200">
              <v-container fill-height class="align-end pa-0">
                <v-spacer />
                <v-btn
                  color="error"
                  class="ma-1 white--text"
                  fab
                  x-small
                  @click="removeImage(image)"
                >
                  <v-icon x-small>fa fa-trash-alt</v-icon>
                </v-btn>
              </v-container>
            </v-img>
          </v-col>
        </v-row>
      </v-card-text>
      <multi-video-select
        :clear="clearImagesSelected"
        :max="maxReviewVideos"
        :headline="reviewVideoHeadline"
        @max-exceed="maxVideoExceed"
        @video-data="videos = $event"
      />
      <v-card-text>
        <v-row>
          <v-col cols="3" v-for="(video, i) in initialVideos" :key="video.id">
            <v-card>
              <v-card-text>
                <div id="vue-core-video-player-box" class="example-player">
                  <vue-core-video-player
                    :src="video.url"
                    :autoplay="false"
                    :logo="require('@/assets/white_logo_only.png')"
                    :loop="false"
                  />
                </div>
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn
                  color="error"
                  class="ma-1 white--text"
                  fab
                  x-small
                  @click="removeVideo(video)"
                >
                  <v-icon x-small>fa fa-trash-alt</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-card>
</template>

<script>
import VueEditor from "@/components/base/VueEditor";
import ImageSelectAndPreview from "../utils/ImageSelectAndPreview";
import MultiVideoSelect from "../utils/form/MultiVideoSelect";
import Rating from "../base/Rating";
export default {
  name: "ReviewForm",
  components: { Rating, MultiVideoSelect, ImageSelectAndPreview, VueEditor },
  props: {
    initialReview: {
      type: Object,
      required: false,
    },
    loading: {
      type: Boolean,
      required: true,
    },
    clear: {
      type: Boolean,
      required: false,
    },
  },
  data() {
    return {
      review: null,
      changedContent: {},
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
      clearImagesSelected: false,
      clearRating: false,
      clearEditor: false,
    };
  },
  methods: {
    submit() {
      this.clearImagesSelected = false; // reset all child components so that clear can be triggered again
      this.clearRating = false;
      this.clearEditor = false;
      this.$emit("review-data", {
        content: this.content,
        images: this.images,
        videos: this.videos,
        rating: this.rating,
        removeImages: this.removeImages,
        removeVideos: this.removeVideos,
      });
    },
    closeBtnClick() {
      this.$emit("close");
    },
    maxImageExceed() {
      this.$store.dispatch(
        "shared/setErrorText",
        `Maximum of ${this.maxReviewImages} images can be added`
      );
      this.$store.dispatch("shared/setErrorSnackbar", true);
    },
    maxVideoExceed() {
      this.$store.dispatch(
        "shared/setErrorText",
        `Maximum of ${this.maxReviewVideos} videos can be added`
      );
      this.$store.dispatch("shared/setErrorSnackbar", true);
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
    },
  },
  created() {
    if (this.initialReview) {
      this.review = this.initialReview;
      this.initialImages = Object.create(this.review.images); // do not mutate vuex directly
      this.initialVideos = Object.create(this.review.videos); // do not mutate vuex directly
      this.defaultRating = this.review.rating;
      this.changedContent = {
        innerHTML: this.review.reviewContent,
        text: this.review.reviewText,
      };
    }
  },
  watch: {
    clear(val) {
      if (val) {
        this.clearImagesSelected = true;
        this.removeVideos = [];
        this.removeImages = [];
        if (!this.initialReview) {
          this.clearRating = true;
          this.clearEditor = true;
        }
      }
    },
  },
};
</script>

<style scoped></style>
