<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Add New Video</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="uploading" @click="upload()">
          Save
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
      <v-row>
        <v-col cols="12" sm="6">
          <v-file-input
            accept="video/*"
            flat
            placeholder="Pick video"
            prepend-icon="mdi-camera"
            v-model="file"
            :error-messages="fileErrors"
            @input="$v.file.$touch()"
          />
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
      <v-row dense>
        <v-col cols="3" sm="4">
          <v-subheader>Cover Pic</v-subheader>
        </v-col>
        <v-col cols="9" sm="4">
          <single-image-upload
            @image-data="onChangeInImages"
            :errors="coverErrors"
            :ratio="2"
            :clear="clearCover"
          />
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import BaseBtn from "@/components/base/BaseBtn";
import SingleImageUpload from "../SingleImageUpload";
import { validationMixin } from "vuelidate";
import { maxLength, required } from "vuelidate/lib/validators";

const isCorrectLength = (value) =>
  value === "" || value === null || value.length === 8;

const isDurationCorrect = (value) => {
  if (!value) return false;
  let arr = value.split(":");
  if (arr.length !== 3) return false;
  for (let i = 0; i < 3; i++) {
    if (arr[i].length !== 2 || isNaN(arr[i])) return false;
  }
  return true;
};

export default {
  name: "VideoForm",
  mixins: [validationMixin],

  validations: {
    title: { required, maxLength: maxLength(30) },
    duration: { required, isCorrectLength, isDurationCorrect },
    description: { required, maxLength: maxLength(500) },
    file: { required },
  },

  components: { SingleImageUpload, BaseBtn },
  props: {
    uploading: {
      type: Boolean,
      required: true,
    },
    clear: {
      type: Boolean,
      required: false,
    },
    data: {
      type: Object,
      required: false,
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
      clearCover: false
    };
  },
  methods: {
    upload() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.$emit("save", {
        video: this.file,
        coverPic: this.coverPicData,
        title: this.title,
        duration: this.duration,
        description: this.description,
      });
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
    onChangeInImages(val) {
      this.coverPicData = val;
      this.clearCover = false; // set false so that cab be cleared in future
    },
  },
  created() {
    if (this.data){
      this.duration = this.data.duration;
      this.title = this.data.title;
      this.description = this.data.description;
    }
  },
  computed: {
    fileErrors() {
      const errors = [];
      if (!this.$v.file.$dirty) return errors;
      !this.$v.title.required && errors.push("File is required");
      return errors;
    },
    durationErrors() {
      const errors = [];
      if (!this.$v.duration.$dirty) return errors;
      !this.$v.duration.isCorrectLength &&
        errors.push(
          "Duration must be 8 characters long and in HH:MM:SS format"
        );
      !this.$v.duration.isDurationCorrect &&
        errors.push("Duration must be in HH:MM:SS format");
      !this.$v.duration.required && errors.push("Duration is required");
      return errors;
    },
    titleErrors() {
      const errors = [];
      if (!this.$v.title.$dirty) return errors;
      !this.$v.title.maxLength &&
        errors.push("Name must be at most 30 characters long");
      !this.$v.title.required && errors.push("Name is required");
      return errors;
    },
    descriptionErrors() {
      const errors = [];
      if (!this.$v.description.$dirty) return errors;
      !this.$v.title.required && errors.push("Description is required");
      !this.$v.description.maxLength &&
        errors.push("Description must be at most 500 characters long");
      return errors;
    },
    coverErrors() {
      const errors = [];
      if (!this.coverPicData || !this.coverPicData.blob) {
        errors.push("Cover pic is required");
      }
      return errors;
    }
  },
  watch: {
    clear(val) {
      if (val) {
        this.$v.$reset();
        this.duration = "";
        this.title = "";
        this.description = "";
        this.clearCover = true; // clear single-image-upload
      }
    },
  },
};
</script>

<style scoped></style>
