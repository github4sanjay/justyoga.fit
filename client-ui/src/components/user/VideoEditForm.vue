<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Update Video Details</q-toolbar-title>
      <q-btn flat label="Save" :loading="updatingVideo" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="videoEditForm">
        <div class="row items-center">
          <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
            <div>Duration</div>
          </div>
          <div class="col-12 col-md-8">
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
      </q-form>
    </div>
  </div>
  <!-- <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Update Video Details</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="updating" @click="upload()">
          Update
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
      <v-row>
        <v-col cols="3" sm="4">
          <v-subheader>Duration</v-subheader>
        </v-col>
        <v-col cols="12" sm="6">
          <v-text-field
            persistent-hint
            :hint="durationHint"
            v-model="duration"
            name="mobile"
            label="Duration"
            :error-messages="durationErrors"
            :counter="8"
            @input="$v.duration.$touch()"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row dense>
        <v-col cols="3" sm="4">
          <v-subheader>Title</v-subheader>
        </v-col>
        <v-col cols="9" sm="8">
          <v-text-field
            v-model="title"
            name="mobile"
            label="Title"
            :error-messages="titleErrors"
            :counter="30"
            @input="$v.title.$touch()"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row dense>
        <v-col cols="3" sm="4">
          <v-subheader>Description</v-subheader>
        </v-col>
        <v-col cols="9" sm="8">
          <v-textarea
            outlined
            v-model="description"
            name="description"
            label="Description"
            textarea
            :error-messages="descriptionErrors"
            :counter="500"
            @input="$v.description.$touch()"
          />
        </v-col>
      </v-row>
    </v-container>
  </v-container> -->
</template>

<script>
export default {
  name: "VideoEditForm",

  components: {},
  props: {
    user: {
      type: Object,
      required: true
    },
    video: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      title: null,
      description: null,
      duration: null,
      durationHint: "HH:MM:SS",
      updatingVideo: false
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
      this.$refs.videoEditForm.validate().then(success => {
        if (success) {
          this.updateVideo({
            title: this.title,
            duration: this.duration,
            description: this.description,
            id: this.video.id,
            url: this.video.url,
            coverPic: this.video.coverPic
          });
        } else {
          return;
        }
      });
    },
    updateVideo(val) {
      this.updatingVideo = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("videos/update", val)
        .then(() => {
          this.updatingVideo = false;
          this.closeBtnClick();
        })
        .catch(() => (this.updatingVideo = false));
    },
    closeBtnClick() {
      this.$emit("close", {});
    }
  },
  created() {
    if (this.video) {
      this.duration = this.video.duration;
      this.title = this.video.title;
      this.description = this.video.description;
    }
  },
  computed: {},
  watch: {
    video(val) {
      if (val) {
        this.duration = val.duration;
        this.title = val.title;
        this.description = val.description;
      }
    }
  }
};
</script>

<style scoped></style>
