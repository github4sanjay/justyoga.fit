<template>
  <div>
    <q-card flat bordered square>
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

      <q-separator />

      <q-card-section>
        <q-chip square color="orange" text-color="white" icon-right="star">
          Rated {{ review.rating }}
        </q-chip>
      </q-card-section>
      <q-card-section horizontal>
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
      <q-card-actions>
        <q-btn
          flat
          color="primary"
          label="Details"
          :to="`/reviews/${review.id}`"
        />
        <q-btn
          flat
          color="primary"
          label="Comment"
          :to="`/reviews/${review.id}`"
        />
      </q-card-actions>
    </q-card>
    <q-dialog v-model="mediaDialogue" maximized>
      <q-card>
        <media-carousel
          :images="review.images"
          :videos="review.videos"
          :select="mediaSelected"
          @close="mediaDialogue = false"
        />
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import MediaCarousel from "components/base/MediaCarousel";
import DefaultVideoCard from "components/base/DefaultVideoCard";
import TimeUtil from "src/utils/TimeUtil";
import UserImage from "components/base/UserImage";
export default {
  name: "ReviewCard",
  components: {
    UserImage,
    DefaultVideoCard,
    MediaCarousel
  },
  props: {
    reviewId: {
      type: String,
      required: true
    },
    editable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      imageDialog: false,
      index: 0,
      options: [{ text: "Delete" }],
      mediaSelected: { type: "IMAGE", position: 0 },
      mediaDialogue: false
    };
  },
  created() {},
  methods: {
    clickMedia(pos, type) {
      this.mediaDialogue = true;
      this.mediaSelected = { type: type, position: pos };
    },
    rightClick() {
      this.index++;
    },
    leftClick() {
      this.index--;
    },
    openCarousel(index) {
      this.index = index;
      this.imageDialog = true;
    },
    onOptionClick(val) {
      this.$store.dispatch("shared/setSuccessText", val);
      this.$store.dispatch("shared/setSuccessSnackbar", true);
    }
  },
  computed: {
    time() {
      if (this.review) {
        return TimeUtil.timeDifference(this.review.updatedAt);
      } else {
        return "";
      }
    },
    reviewer() {
      return this.$store.getters["users/user"](this.review.reviewedBy);
    },
    review() {
      return this.$store.getters["reviews/findById"](this.reviewId);
    }
  }
};
</script>

<style scoped></style>
