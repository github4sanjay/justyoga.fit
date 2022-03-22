<template>
  <q-page padding>
    <div class="row justify-center">
      <div class="col-12 col-md-10 col-lg-8">
        <q-card flat square>
          <q-card-actions>
            <q-card-section>
              <div class="text-h6">
                {{ reviewer.name }}'s review for {{ reviewFor.name }}
              </div>
            </q-card-section>
            <q-space />
            <q-btn
              v-if="editable"
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
          <q-item>
            <q-item-section avatar>
              <q-avatar color="primary" text-color="white">
                <user-image :user="reviewer" />
              </q-avatar>
            </q-item-section>

            <q-item-section>
              <q-item-label>{{ reviewer.name }}</q-item-label>
              <q-item-label caption>
                {{ time }}
              </q-item-label>
            </q-item-section>
          </q-item>
          <q-card-section>
            <q-chip square color="orange" text-color="white" icon-right="star">
              Rated {{ review.rating }}
            </q-chip>
          </q-card-section>
          <q-card-section>
            <q-card-section v-html="review.reviewContent" />
          </q-card-section>
          <q-card-section>
            <div class="row">
              <div
                class="col-6 col-sm-4 col-md-3 q-pa-sm"
                v-for="(image, i) in review.images"
                :key="i"
              >
                <q-img basic :src="image.url" @click="clickMedia(i, `IMAGE`)" />
              </div>
            </div>
            <div class="row">
              <div
                class="col-6 col-sm-4 col-md-3 q-pa-sm"
                v-for="(video, i) in review.videos"
                :key="video.id"
                @click="clickMedia(i, `VIDEO`)"
              >
                <default-video-card :video="video" :editable="false" />
              </div>
            </div>
          </q-card-section>
          <q-separator />
          <q-card-actions>
            <q-btn
              flat
              color="primary"
              label="Comment"
              v-if="!currentUser"
              @click="showLoginMessage"
            />
            <q-card-section>
              <div>
                {{ review.comments.length }} comments,
                {{ review.likes.length }} likes
              </div>
            </q-card-section>
            <q-space />
            <q-btn
              flat
              round
              :color="isLiked ? `primary` : `grey`"
              :loading="savingLike"
              icon="favorite"
              @click="like"
            />
            <q-btn flat round color="primary" icon="share" @click="shareCopy" />
          </q-card-actions>
        </q-card>
        <q-card flat v-if="currentUser" class="q-mt-md">
          <comment-box
            v-if="comment"
            @submit="saveComment"
            :user="currentUser"
            :saving="savingComment"
            :clear="clearComment"
          />
        </q-card>
        <div class="row" v-if="review.comments && review.comments.length > 0">
          <div class="col-12 q-my-md">Comments</div>
          <div
            class="col-12 q-mt-sm"
            v-for="comment in review.comments"
            :key="comment.id"
          >
            <comment :comment="comment" />
            <q-separator />
          </div>
        </div>
      </div>
      <!-- <div
        :class="{
          'col-12': true,
          'col-sm-4': true,
          'q-pl-sm': $q.screen.gt.xs
        }"
      >
        <q-toolbar class="bg-primary text-white">
          <q-toolbar-title>Review Media</q-toolbar-title>
        </q-toolbar>
        <q-card square>
          <q-card-section
            v-if="
              !(
                (review.images && review.images.length > 0) ||
                (review.videos && review.images.videos > 0)
              )
            "
          >
            <div class="text-subtitle2">No Media with this review</div>
          </q-card-section>
          <q-card-section>
            <div class="row">
              <div
                class="col-12 col-sm-6 q-pa-sm"
                v-for="(image, i) in review.images"
                :key="i"
              >
                <q-img basic :src="image.url" @click="clickMedia(i, `IMAGE`)" />
              </div>
            </div>
            <div class="row">
              <div
                class="col-12 col-sm-6 q-pa-sm"
                v-for="(video, i) in review.videos"
                :key="video.id"
                @click="clickMedia(i, `VIDEO`)"
              >
                <default-video-card :video="video" :editable="false" />
              </div>
            </div>
          </q-card-section>
        </q-card>
      </div> -->
    </div>
    <q-dialog v-model="mediaDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '850px' : ''
        }"
      >
        <media-carousel
          :images="review.images"
          :videos="review.videos"
          :select="mediaSelected"
          @close="mediaDialogue = false"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="deleteReviewDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '850px' : ''
        }"
      >
        <confirm
          :loading="deletingReview"
          :model="review"
          :text="deleteReviewText"
          @close="deleteReviewDialogue = !deleteReviewDialogue"
          @confirm="deleteReview"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="reviewDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '850px' : ''
        }"
      >
        <review-form
          :loading="savingReview"
          :currentUser="currentUser"
          :user="reviewFor"
          :initial-review="review"
          @close="reviewDialogue = false"
          :clear="clearReviewForm"
        />
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script>
import config from "../../config";
import { mapGetters } from "vuex";
import NotifyUtil from "src/utils/NotifyUtil";
import CommentBox from "components/base/CommentBox";
import Comment from "components/base/Comment";
import ReviewForm from "components/profile/ReviewForm";
import Confirm from "components/base/Confirm";
import MediaCarousel from "components/base/MediaCarousel";
import DefaultVideoCard from "components/base/DefaultVideoCard";
import UserImage from "components/base/UserImage";
import TimeUtil from "src/utils/TimeUtil";
import { Loading } from "quasar";
import CopyUtil from "src/utils/CopyUtil";

export default {
  name: "ReviewHome",
  components: {
    UserImage,
    DefaultVideoCard,
    MediaCarousel,
    Confirm,
    ReviewForm,
    Comment,
    CommentBox
  },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    try {
      let params = currentRoute.params;
      let review = store.getters["reviews/findById"](params.id);
      if (review === null || review === undefined) {
        review = await store.dispatch("reviews/get", { id: params.id });
        let promises = [];
        promises.push(
          store.dispatch("reviews/getImages", { reviewId: review.id })
        );
        promises.push(
          store.dispatch("reviews/getVideos", { reviewId: review.id })
        );
        promises.push(
          store.dispatch("users/storeUser", { id: review.reviewedBy })
        );
        promises.push(store.dispatch("users/storeUser", { id: review.userId }));
        await Promise.all(promises);
      }

      let userIds = new Set(); // only unique users
      if (!review.comments) {
        let comments = await store.dispatch("reviews/getComments", {
          reviewId: review.id
        });
        if (comments) {
          comments.forEach(comment => userIds.add(comment.userId));
        }
      }

      if (!review.likes) {
        let comments = await store.dispatch("reviews/getLikes", {
          reviewId: review.id
        });
        if (comments) {
          comments.forEach(comment => userIds.add(comment.userId));
        }
      }
      let commentUserPromise = [];
      userIds.forEach(id =>
        commentUserPromise.push(store.dispatch("users/storeUser", { id: id }))
      );

      await Promise.all(commentUserPromise); // get all user from comment userId
    } catch (e) {
      redirect("/error404");
    }
    Loading.hide();
  },
  data: function() {
    return {
      // review: this.$store.getters["reviews/findById"](this.$route.params.id),
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
      isLiked: null
    };
  },
  created() {
    if (this.review) {
      this.isLiked = this.getIsLiked(this.review);
    }
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    review() {
      return this.$store.getters["reviews/findById"](this.$route.params.id);
    },
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
      return TimeUtil.timeDifference(this.review.updatedAt);
    }
  },
  methods: {
    getIsLiked(review) {
      let likes = review.likes;
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
    saveComment(val) {
      this.clearComment = false;
      this.savingComment = true;
      this.$store
        .dispatch("reviews/storeComments", {
          userId: this.currentUser.id,
          reviewId: this.review.id,
          text: val
        })
        .finally(() => {
          this.savingComment = false;
          this.clearComment = true;
        });
    },
    showLoginMessage() {
      NotifyUtil.showError(`Please login to like/comment`);
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
            reviewId: this.review.id
          })
          .then(() => (this.isLiked = null))
          .finally(() => {
            this.savingLike = false;
          });
      } else {
        this.savingLike = true;
        this.$store
          .dispatch("reviews/storeLikes", {
            userId: this.currentUser.id,
            reviewId: this.review.id
          })
          .then(data => (this.isLiked = data))
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
    deleteReview(val) {
      this.deletingReview = true;
      this.$store
        .dispatch("reviews/remove", { id: val.id })
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
    shareCopy() {
      CopyUtil.copyToClipboard(`${config.uiUrl}/reviews/${this.review.id}`);
    }
  },
  watch: {
    review(val) {
      this.isLiked = this.getIsLiked(val);
    },
    currentUser(val) {
      this.isLiked = this.getIsLiked(this.review);
    }
  },
  head() {
    return {
      title: `${this.reviewer.name}\`s review for ${this.reviewFor.name} - Just Yoga`,
      meta: [
        {
          hid: `description`,
          name: "description",
          content: `${this.review.text}`
        },
        {
          hid: `keywords`,
          name: "keywords",
          keywords: `${this.reviewFor.name},${this.reviewer.name},review,trainer,
            yoga videos,rating,reviews,yoga,feeds,blog,contact,yoga classes,
            yoga expertise,yoga,medical expertise`
        }
      ]
    };
  }
};
</script>
>
