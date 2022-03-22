<template>
  <v-card elevation="24" class="mx-auto">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Browse Media</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
      </v-toolbar-items>
    </v-toolbar>
    <v-carousel
      v-model="carousel"
      :cycle="cycle"
      height="800"
      hide-delimiter-background
      show-arrows-on-hover
    >
      <v-carousel-item v-for="(image, i) in images" :key="image.id">
        <v-sheet color="black" height="100%">
          <v-row class="fill-height" align="center" justify="center">
            <v-img
              max-height="800"
              contain
              class="grey darken-4"
              :src="image.url"
            />
          </v-row>
        </v-sheet>
      </v-carousel-item>
      <v-carousel-item v-for="(video, i) in videos" :key="video.id">
        <v-sheet color="black" height="100%">
          <v-row class="fill-height" align="center" justify="center">
            <div :id="video.id" class="example-player">
              <vue-core-video-player
                :cover="video.coverPic"
                :src="video.url"
                :loop="false"
                :autoplay="false"
              />
            </div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </v-card>
</template>

<script>
export default {
  name: "MediaCarousel",
  props: {
    videos: {
      type: Array,
      required: false,
    },
    images: {
      type: Array,
      required: false,
    },
    cycle: {
      type: Boolean,
      required: false,
      default: false,
    },
    select: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      carousel: 0,
    };
  },
  created() {
    let val = this.select;
    if (val.type && val.type === "IMAGE") {
      this.carousel = val.position; // consider from 0th
    } else if (val.type && val.type === "VIDEO") {
      this.carousel =
        val.position + (this.images ? this.images.length : 0); // consider from 0th
    }
  },
  methods: {
    closeBtnClick(){
      this.$emit("close");
    }
  },
  watch: {
    select(val) {
      if (val.type && val.type === "IMAGE") {
        this.carousel = val.position; // consider from 0th
      } else if (val.type && val.type === "VIDEO") {
        this.carousel =
          val.position + (this.images ? this.images.length : 0); // consider from 0th
      }
    },
  },
};
</script>

<style scoped></style>
