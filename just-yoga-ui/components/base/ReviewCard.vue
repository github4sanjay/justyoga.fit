<template>
  <v-card class="mx-auto pa-0" flat>
    <v-list-item>
      <v-list-item-avatar color="primary">
        <v-img
          v-if="reviewer.profilePic"
          :src="reviewer.profilePic"
          :alt="reviewer.name"
        />
        <span v-else-if="reviewer.name" class="white--text headline">
          {{ reviewer.name.charAt(0) }}
        </span>
        <span v-else class="white--text headline">JY</span>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="title">{{ reviewer.name }}</v-list-item-title>
        <!-- <v-list-item-subtitle>{{
          user.formattedAddress
          }}</v-list-item-subtitle>-->
      </v-list-item-content>
    </v-list-item>

    <v-chip small label class="ma-2" color="orange" text-color="white">
      Rated {{ review.rating }}
      <v-icon right small>mdi-star</v-icon>
    </v-chip>

    <v-card-text>
      <div class="content ql-editor" v-html="review.reviewContent" />
      <v-row>
        <v-col
          cols="3"
          v-for="(image, i) in review.images"
          :key="image.id"
          @click.stop="openCarousel(i)"
        >
          <v-img :src="image.url" @click="clickMedia(i, `IMAGE`)">
            <template v-slot:placeholder>
              <v-row
                class="fill-height ma-0"
                align="center"
                justify="center"
              >
                <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
              </v-row>
            </template>
          </v-img>
        </v-col>
        <v-col
          cols="4"
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
    </v-card-text>

    <v-card-actions>
      <v-btn
        router
        :to="`/reviews/${review.id}`"
        text
        color="deep-purple accent-4"
      >
        Details
      </v-btn>
      <v-btn
        router
        :to="`/reviews/${review.id}`"
        text
        color="deep-purple accent-4"
      >
        Comment
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
    </v-card-actions>
    <v-dialog width="1200" v-model="mediaDialogue">
      <media-carousel
        :images="review.images"
        :videos="review.videos"
        :select="mediaSelected"
        @close="mediaDialogue = false"
      />
    </v-dialog>
  </v-card>
</template>

<script>
import UserHeader from "./UserHeader";
import MediaCarousel from "./MediaCarousel";
import DefaultVideoCard from "./DefaultVideoCard";
export default {
  name: "ReviewCard",
  components: {DefaultVideoCard, MediaCarousel, UserHeader },
  props: {
    reviewId: {
      type: String,
      required: true,
    },
    editable: {
      type: Boolean,
      default: false,
    },
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
  created() {
  },
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
    },
  },
  computed: {
    reviewer() {
      return this.$store.getters["users/user"](this.review.reviewedBy);
    },
    review() {
      return this.$store.getters["reviews/findById"](this.reviewId);
    },
  },
};
</script>

<style scoped></style>
