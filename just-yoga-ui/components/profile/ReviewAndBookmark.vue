<template>
  <v-card class="pa-2" flat>
    <v-row justify="end">
      <v-btn color="secondary" text v-if="!editable">
        Bookmark
      </v-btn>
      <v-btn
        color="secondary"
        text
        @click="clickBtn('REVIEW')"
        v-if="!editable"
      >
        Add Review
      </v-btn>
    </v-row>
    <v-dialog width="800" v-model="reviewDialogue" persistent>
      <review-form
        :loading="savingReview"
        @review-data="submit"
        @close="reviewDialogue = false"
        :clear="clearReviewForm"
      />
    </v-dialog>
  </v-card>
</template>

<script>
import VueEditor from "@/components/base/VueEditor";
import ImageSelectAndPreview from "../utils/ImageSelectAndPreview";
import MultiVideoSelect from "../utils/form/MultiVideoSelect";
import Rating from "../base/Rating";
import Util from "@/utils/Util";
import ReviewForm from "./ReviewForm";
export default {
  name: "ReviewAndBookmark",
  components: {
    ReviewForm,
    Rating,
    MultiVideoSelect,
    ImageSelectAndPreview,
    VueEditor,
  },
  props: {
    user: {
      type: Object,
      required: true,
    },
    currentUser: {
      type: Object,
      required: true,
    },
    editable: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      reviewDialogue: false,
      savingReview: false,
      clearReviewForm: false
    };
  },
  methods: {
    async submit(val) {
      this.clearReviewForm = false; // so that clear can be triggered
      let rating = val.rating;
      let content = val.content;
      let videos = val.videos;
      let images = val.images;
      if (rating === 0) {
        await this.$store.dispatch("shared/setErrorText", `Rating is required`);
        await this.$store.dispatch("shared/setErrorSnackbar", true);
        return;
      }
      if (!content.html || content.html === "<p><br></p>") {
        await this.$store.dispatch(
          "shared/setErrorText",
          `Review text is required`
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
        return;
      }
      this.savingReview = true;
      try {
        let review = await this.$store.dispatch("reviews/store", {
          userId: this.user.id,
          reviewedBy: this.currentUser.id,
          rating: rating,
          reviewText: content.text,
          reviewContent: content.html,
          published: true,
        });
        await this.$store.dispatch("reviews/storeImages", {
          reviewId: review.id,
          images: images,
        });

        await this.$store.dispatch("reviews/storeVideos", {
          reviewId: review.id,
          videos: videos,
        });
      } catch (e) {
        await this.$store.dispatch(
          "shared/setErrorText",
          `Something went wrong`
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
      } finally {
        this.reviewDialogue = false;
        this.savingReview = false;
        this.clearReviewForm = true
      }
    },
    clickBtn(val) {
      switch (val) {
        case "REVIEW":
          if (Util.isObjectEmpty(this.currentUser)) {
            this.$store.dispatch(
              "shared/setErrorText",
              `Please login to rate/review`
            );
            this.$store.dispatch("shared/setErrorSnackbar", true);
          } else {
            this.reviewDialogue = true;
          }
          break;
        case "BOOKMARK":
          break;
      }
    },
  },
};
</script>

<style scoped></style>
