<template>
  <v-row class="ml-2">
    <v-col cols="12">
      <v-card color="background" flat>
        <v-card-text
          :class="{
            'pl-0': true,
            'justify-center': $vuetify.breakpoint.smAndDown,
            headline: $vuetify.breakpoint.mdAndUp,
            title: $vuetify.breakpoint.sm,
            'subtitle-2': $vuetify.breakpoint.xsOnly,
          }"
        >
          {{ headline }}
          <base-btn
            color="primary"
            class="ml-2"
            :text="buttonText"
            @click="addVideo"
          />
        </v-card-text>
      </v-card>
    </v-col>
    <v-col cols="12" v-for="(video, index) in videos" :key="index">
      <v-card elevation="1">
        <v-row align="center">
          <v-col cols="12" sm="8" class="py-0">
            <v-file-input
              accept="video/*"
              flat
              placeholder="Pick video"
              prepend-icon="mdi-camera"
              v-model="video.file"
            />
          </v-col>
          <v-col cols="12" sm="4">
            <base-btn
              color="error"
              class="ml-2"
              text="Remove"
              @click="removeVideo(index)"
            />
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import BaseBtn from "@/components/base/BaseBtn";
import SingleImageUpload from "../SingleImageUpload";
export default {
  name: "MultiVideoSelect",
  components: { SingleImageUpload, BaseBtn },
  props: {
    headline: {
      type: String,
      required: false,
      default: "Add Videos",
    },
    max: {
      type: Number,
      required: true,
    },
    clear: {
      type: Boolean,
      required: false,
    }
  },
  data() {
    return {
      videos: [],
      errorMsg: null,
    };
  },
  methods: {
    addVideo() {
      if (this.videos.length < this.max) {
        this.videos.push({});
      } else {
        this.$emit("max-exceed", true);
      }
    },
    removeVideo(index) {
      this.videos.splice(index, 1);
    },
  },
  computed: {
    buttonText() {
      return this.videos.length > 0 ? "Add More" : "Add";
    },
  },
  watch: {
    videos(val) {
      this.$emit("video-data", val);
    },
    clear(val){
      if (val){
        this.videos = [];
      }
    }
  },
};
</script>

<style scoped></style>
