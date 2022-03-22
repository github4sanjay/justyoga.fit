<<template>
  <v-container pa-0>
    <v-row>
      <v-col cols="12" lg="8">
        <v-card class="mx-auto pa-0">
          <v-card flat>
            <v-card-actions class="pt-0">
              <v-card-title>
                {{ reviewer.name }}`s review for
                {{ reviewFor.name }}</v-card-title
              >
              <v-spacer />
              <v-menu bottom left v-if="editable">
                <template v-slot:activator="{ on }">
                  <v-btn icon v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item
                    v-for="(item, i) in items"
                    :key="i"
                    @click="onMenuClick(item)"
                  >
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-card-actions>
          </v-card>
          <v-list-item>
            <v-list-item-avatar color="primary">
              <base-avatar
                :name="reviewer.name"
                :url="reviewer.profilePic"
                size="50"
              />
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title class="title">{{
                reviewer.name
              }}</v-list-item-title>
              <v-list-item-subtitle>{{ time }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-chip small label class="ma-2" color="orange" text-color="white">
            Rated {{ review.rating }}
            <v-icon right small>mdi-star</v-icon>
          </v-chip>
          <v-card-text>
            <div class="content ql-editor" v-html="review.reviewContent" />
          </v-card-text>
          <v-card-actions>
            <v-btn
              v-if="!currentUser"
              @click="showLoginMessage"
              text
              color="deep-purple accent-4"
            >
              Comment
            </v-btn>
            <v-card-text class="font-italic">
              {{ review.comments.length }} comments,
              {{ review.likes.length }} likes
            </v-card-text>
            <v-spacer></v-spacer>
            <v-btn
              :color="isLiked ? `primary` : ``"
              :loading="savingLike"
              icon
              @click="like"
            >
              <v-icon>mdi-heart</v-icon>
            </v-btn>
            <v-btn icon @click="shareCopy">
              <v-icon>mdi-share-variant</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
        <v-card flat class="mt-3" v-if="currentUser">
          <comment-box
            v-if="comment"
            @submit="saveComment"
            :user="currentUser"
            :saving="savingComment"
            :clear="clearComment"
          />
          <v-card-text v-if="review.comments && review.comments.length > 0">
            Comments
          </v-card-text>
          <v-row>
            <v-col
              cols="12"
              v-for="comment in review.comments"
              :key="comment.id"
            >
              <comment :comment="comment" />
              <v-divider />
            </v-col>
          </v-row>
        </v-card>
      </v-col>
      <v-col cols="12" lg="4">
        <v-toolbar dark color="primary">
          <v-toolbar-title> Review Media</v-toolbar-title>
        </v-toolbar>
        <v-card class="pa-3">
          <v-card-text
            class="font-italic"
            v-if="
              !(
                (review.images && review.images.length > 0) ||
                (review.videos && review.images.videos > 0)
              )
            "
          >
            No Media with this review
          </v-card-text>
          <v-row>
            <v-col
              cols="6"
              v-for="(image, i) in review.images"
              :key="image.id"
              @click="clickMedia(i, `IMAGE`)"
            >
              <v-img max-height="200" :src="image.url" />
            </v-col>
            <v-col
              cols="6"
              v-for="(video, i) in review.videos"
              :key="video.id"
              @click="clickMedia(i, `VIDEO`)"
            >
              <!--<div id="vue-core-video-player-box" class="example-player">
                <vue-core-video-player
                  :src="video.url"
                  :autoplay="false"
                  :logo="require('@/assets/white_logo_only.png')"
                  :loop="false"
                />
              </div>-->
              <default-video-card :video="video" :editable="false" />
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <v-dialog width="800" v-model="reviewDialogue" persistent>
      <review-form
        :loading="savingReview"
        @review-data="submit"
        :initial-review="review"
        @close="reviewDialogue = false"
        :clear="clearReviewForm"
      />
    </v-dialog>
    <v-dialog width="600" v-model="deleteReviewDialogue">
      <v-card flat>
        <confirm
          :loading="deletingReview"
          :model="review"
          :text="deleteReviewText"
          @close="deleteReviewDialogue = !deleteReviewDialogue"
          @confirm="deleteReview"
        />
      </v-card>
    </v-dialog>
    <v-dialog width="1200" v-model="mediaDialogue">
      <media-carousel
        :images="review.images"
        :videos="review.videos"
        :select="mediaSelected"
        @close="mediaDialogue = false"
      />
    </v-dialog>
  </v-container>
</template>

<script>
import config from "@/config.js";
import { mapGetters } from "vuex";
import BaseAvatar from "@/components/base/BaseAvatar";
import TImeUtil from "@/utils/TImeUtil";
import CommentBox from "@/components/base/CommentBox";
import Comment from "@/components/base/Comment";
import ReviewForm from "@/components/profile/ReviewForm";
import Confirm from "@/components/base/Confirm";
import MediaCarousel from "../../../components/base/MediaCarousel";
import DefaultVideoCard from "../../../components/base/DefaultVideoCard";

export default {
  name: "ReviewHome",
  components: {
    DefaultVideoCard,
    MediaCarousel,
    Confirm,
    ReviewForm,
    Comment,
    CommentBox,
    BaseAvatar,
  },
  middleware: "review",
  data: function () {
    return {
      review: this.$store.getters["reviews/findById"](this.$route.params.id),
      clearComment: false,
      savingComment: false,
      comment: true,
      savingLike: false,
      items: [{ title: "Edit" }, { title: "Delete" }],
      reviewDialogue: false,
      savingReview: false,
      clearReviewForm: false,
      deletingReview: false,
      deleteReviewText: "Do you want to delete this review ?",
      deleteReviewDialogue: false,
      mediaDialogue: false,
      mediaSelected: { type: "IMAGE", position: 0 },
    };
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user",
    }),
    editable() {
      return !!(
        this.currentUser && this.currentUser.id === this.review.reviewedBy
      );
    },
    reviewer() {
      return this.$store.getters["users/user"](this.review.reviewedBy);
    },
    reviewFor() {
      return this.$store.getters["users/user"](this.review.userId);
    },
    time() {
      let time = TImeUtil.timeDifference(this.review.updatedAt);
      return time + " ago";
    },
    isLiked() {
      let likes = this.review.likes;
      let isLiked = null;
      if (likes && this.currentUser) {
        for (let i = 0; i < likes.length; i++) {
          if (likes[i].userId === this.currentUser.id) {
            isLiked = likes[i];
            break;
          }
        }
      }
      return isLiked;
    },
  },
  methods: {
    saveComment(val) {
      this.savingComment = true;
      this.$store
        .dispatch("reviews/storeComments", {
          userId: this.currentUser.id,
          reviewId: this.review.id,
          text: val,
        })
        .finally(() => {
          this.savingComment = false;
          this.clearComment = true;
        });
    },
    showLoginMessage() {
      this.$store.dispatch(
        "shared/setErrorText",
        `Please login to like/comment`
      );
      this.$store.dispatch("shared/setErrorSnackbar", true);
    },
    like() {
      if (!this.currentUser) {
        this.showLoginMessage();
        return;
      }
      if (this.isLiked) {
        this.savingLike = true;
        this.$store
          .dispatch("reviews/deleteLikes", {
            id: this.isLiked.id,
            reviewId: this.review.id,
          })
          .finally(() => {
            this.savingLike = false;
          });
      } else {
        this.savingLike = true;
        this.$store
          .dispatch("reviews/storeLikes", {
            userId: this.currentUser.id,
            reviewId: this.review.id,
          })
          .finally(() => {
            this.savingLike = false;
          });
      }
    },
    onMenuClick(val) {
      switch (val.title) {
        case "Edit":
          this.reviewDialogue = true;
          break;
        case "Delete":
          this.deleteReviewDialogue = true;
          break;
      }
    },
    async submit(val) {
      this.clearReviewForm = false; // so that clear can be triggered
      let rating = val.rating;
      let content = val.content;
      let videos = val.videos;
      let images = val.images;
      let removeImages = val.removeImages;
      let removeVideos = val.removeVideos;
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
        if (
          content.html !== this.review.reviewContent ||
          content.text === this.review.text ||
          rating !== this.review.rating
        ) {
          await this.$store.dispatch("reviews/store", {
            id: this.review.id, // to update review
            userId: this.review.userId,
            reviewedBy: this.currentUser.id,
            rating: rating,
            reviewText: content.text,
            reviewContent: content.html,
            published: true,
          });
        }
        if (images && images.length > 0) {
          // add if extra images
          await this.$store.dispatch("reviews/storeImages", {
            reviewId: this.review.id,
            images: images,
          });
        }

        if (videos && videos.length > 0) {
          // add if extra videos
          await this.$store.dispatch("reviews/storeVideos", {
            reviewId: this.review.id,
            videos: videos,
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
        await this.$store.dispatch(
          "shared/setErrorText",
          `Something went wrong`
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
      } finally {
        this.reviewDialogue = false;
        this.savingReview = false;
        this.clearReviewForm = true;
      }
    },
    deleteReview(val) {
      this.deletingReview = true;
      this.$store
        .dispatch("reviews/delete", { id: val.id })
        .then(() => {
          this.deletingReview = false;
          this.deleteReviewDialogue = false;
          this.$router.back(); // review deleted go back
        })
        .catch(() => {
          this.deletingReview = false;
        });
    },
    clickMedia(pos, type) {
      this.mediaDialogue = true;
      this.mediaSelected = { type: type, position: pos };
    },
    async shareCopy() {
      try {
        await this.$copyText(`${config.uiUrl}/reviews/${this.review.id}`);
        await this.$store.dispatch("shared/setSuccessText", "Copied");
        await this.$store.dispatch("shared/setSuccessSnackbar", true);
      } catch (e) {
        await this.$store.dispatch(
          "shared/setErrorText",
          "Something went wrong"
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
      }
    },
  },
  head() {
    return {
      title: `${this.reviewer.name}\`s review for ${this.reviewFor.name} - Just Yoga`,
      meta: [
        {
          hid: `description`,
          name: "description",
          content: `${this.review.text}`,
        },
        {
          hid: `keywords`,
          name: "keywords",
          keywords: `${this.reviewFor.name},${this.reviewer.name},review,trainer,
            yoga videos,rating,reviews,yoga,feeds,blog,contact,yoga classes,
            yoga expertise,yoga,medical expertise`,
        },
      ],
    };
  },
};
</script>
>
