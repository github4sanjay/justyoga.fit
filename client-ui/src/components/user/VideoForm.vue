<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Add Video</q-toolbar-title>
      <q-btn flat label="Save" :loading="savingVideo" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="videoForm">
        <div class="row">
          <div class="col-12 col-md-6">
            <q-file
              accept="video/*"
              v-model="file"
              label="Pick file"
              style="max-width: 300px"
              :rules="[val => !!val || '* Required']"
              lazy-rules
            >
              <template v-slot:prepend>
                <q-icon name="attach_file" />
              </template>
            </q-file>
          </div>
          <div class="col-12 col-md-6">
            <q-input
              :hint="durationHint"
              v-model="duration"
              label="Duration"
              maxlength="8"
              counter
              :rules="[
                val => !!val || '* Required',
                val => val.length === 8 || 'Max 8 characters',
                val => isDurationCorrect(val) || durationHint
              ]"
              lazy-rules
            />
          </div>
        </div>
        <div class="row items-center">
          <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
            <div>Title</div>
          </div>
          <div class="col-12 col-md-8">
            <q-input
              v-model="title"
              label="Title"
              maxlength="30"
              counter
              :rules="[
                val => !!val || '* Required',
                val => val.length < 30 || 'Max 30 characters'
              ]"
              lazy-rules
            />
          </div>
        </div>
        <div class="row">
          <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
            <div>Description</div>
          </div>
          <div class="col-12 col-md-8">
            <q-input
              outlined
              type="textarea"
              v-model="description"
              label="Description"
              maxlength="500"
              counter
              :rules="[
                val => !!val || '* Required',
                val =>
                  (val.length > 50 && val.length < 500) ||
                  'Min 50 and Max 500 characters'
              ]"
              lazy-rules
            />
          </div>
        </div>
        <div class="row">
          <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
            <div>Cover Pic</div>
          </div>
          <div class="col-12 col-md-8">
            <single-image-upload
              @image-data="onChangeInImages"
              :errors="coverErrors"
              :ratio="2"
              :clear="clearCover"
            />
          </div>
        </div>
      </q-form>
    </div>
  </div>
</template>

<script>
import SingleImageUpload from "components/util/SelectImage";

export default {
  name: "VideoForm",

  components: { SingleImageUpload },
  props: {
    user: {
      type: Object,
      required: true
    },
    data: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      file: null,
      title: null,
      description: null,
      coverPicData: null,
      errorMsg: null,
      duration: null,
      durationHint: "HH:MM:SS",
      clearCover: false,
      savingVideo: false
    };
  },
  methods: {
    isDurationCorrect(value) {
      if (!value) return false;
      let arr = value.split(":");
      if (arr.length !== 3) return false;
      for (let i = 0; i < 3; i++) {
        if (arr[i].length !== 2 || isNaN(arr[i])) return false;
      }
      return true;
    },
    upload() {
      this.$refs.videoForm.validate().then(success => {
        if (success) {
          this.saveVideo({
            video: this.file,
            coverPic: this.coverPicData,
            title: this.title,
            duration: this.duration,
            description: this.description
          });
        } else {
          return;
        }
      });
    },
    saveVideo(val) {
      this.savingVideo = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("videos/store", val)
        .then(() => {
          this.savingVideo = false;
          this.closeBtnClick();
          this.clear();
        })
        .catch(() => (this.savingVideo = false));
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
    onChangeInImages(val) {
      this.coverPicData = val;
      this.clearCover = false; // set false so that cab be cleared in future
    },
    clear() {
      this.duration = "";
      this.title = "";
      this.description = "";
      this.clearCover = true; // clear single-image-upload
    }
  },
  created() {
    if (this.data) {
      this.duration = this.data.duration;
      this.title = this.data.title;
      this.description = this.data.description;
    }
  },
  computed: {
    coverErrors() {
      const errors = [];
      if (!this.coverPicData || !this.coverPicData.blob) {
        errors.push("Cover pic is required");
      }
      return errors;
    }
  }
};
</script>

<style scoped></style>
