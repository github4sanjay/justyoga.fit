<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Browse Media</q-toolbar-title>
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <q-carousel
      class="bg-grey-10"
      arrows
      swipeable
      infinite
      animated
      control-color="primary"
      navigation
      v-model="carousel"
      :fullscreen.sync="fullscreen"
    >
      <template v-slot:control>
        <q-carousel-control position="bottom-right" :offset="[18, 18]">
          <q-btn
            push
            round
            dense
            color="white"
            text-color="primary"
            :icon="fullscreen ? 'fullscreen_exit' : 'fullscreen'"
            v-close-popup
            @click="fullscreen = !fullscreen"
          />
        </q-carousel-control>
      </template>
      <q-carousel-slide
        v-for="(image, i) in images"
        :key="image.id"
        :name="i"
        full-screen
      >
        <div class="row bg-grey-10 justify-center items-center full-height">
          <q-img
            contain
            :src="image.url"
            basic
            style="max-height: 700px; max-width: 850px"
          />
        </div>
      </q-carousel-slide>
      <q-carousel-slide
        v-for="(video, i) in videos"
        :key="video.id"
        :name="i + (images ? images.length : 0)"
      >
        <div class="row bg-grey-10" style="height: 100%">
          <q-media-player
            type="video"
            background-color="black"
            :show-big-play-button="true"
            :source="video.url"
            track-language="English"
          >
            <template v-slot:overlay>
              <div>
                <img
                  src="~assets/white_logo_only.png"
                  style="width: 30vw; max-width: 50px; opacity: 0.25;"
                />
              </div>
            </template>
          </q-media-player>
        </div>
      </q-carousel-slide>
    </q-carousel>
  </div>
</template>

<script>
export default {
  name: "MediaCarousel",
  props: {
    videos: {
      type: Array,
      required: false
    },
    images: {
      type: Array,
      required: false
    },
    cycle: {
      type: Boolean,
      required: false,
      default: false
    },
    select: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      carousel: 0,
      fullscreen: true
    };
  },
  created() {
    let val = this.select;
    if (val.type && val.type === "IMAGE") {
      this.carousel = val.position; // consider from 0th
    } else if (val.type && val.type === "VIDEO") {
      this.carousel = val.position + (this.images ? this.images.length : 0); // consider from 0th
    }
  },
  methods: {
    closeBtnClick() {
      this.$emit("close");
    }
  },
  watch: {
    select(val) {
      if (val.type && val.type === "IMAGE") {
        this.carousel = val.position; // consider from 0th
      } else if (val.type && val.type === "VIDEO") {
        this.carousel = val.position + (this.images ? this.images.length : 0); // consider from 0th
      }
    }
  }
};
</script>

<style scoped></style>
