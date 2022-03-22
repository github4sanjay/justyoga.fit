<template>
  <div class="row justify-center">
    <div class="col-12">
      <div class="q-mb-sm">
        {{ headline }}
        <q-btn
          color="primary"
          :label="buttonText"
          size="sm"
          @click="addVideo"
        />
      </div>
    </div>
    <div class="col-6 q-pa-sm" v-for="(video, index) in videos" :key="index">
      <q-card q-my-sm>
        <div class="row justify-center items-center">
          <div class="col-12 col-sm-8 q-pa-sm">
            <q-file
              accept="video/*"
              v-model="video.file"
              label="Pick file"
              :rules="[val => !!val || '* Required']"
              lazy-rules
            >
              <template v-slot:prepend>
                <q-icon name="attach_file" />
              </template>
            </q-file>
          </div>
          <div class="col-12 col-sm-4">
            <div class="column items-center">
              <div class="col">
                <q-btn
                  color="negative"
                  label="Remove"
                  size="sm"
                  @click="removeVideo(index)"
                />
              </div>
            </div>
          </div>
        </div>
      </q-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "MultiVideoSelect",
  components: {},
  props: {
    headline: {
      type: String,
      required: false,
      default: "Add Videos"
    },
    max: {
      type: Number,
      required: true
    },
    clear: {
      type: Boolean,
      required: false
    }
  },
  data() {
    return {
      videos: [],
      errorMsg: null
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
    }
  },
  computed: {
    buttonText() {
      return this.videos.length > 0 ? "Add More" : "Add";
    }
  },
  watch: {
    videos(val) {
      this.$emit("video-data", val);
    },
    clear(val) {
      if (val) {
        this.videos = [];
      }
    }
  }
};
</script>

<style scoped></style>
