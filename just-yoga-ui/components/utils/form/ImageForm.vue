<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Add New Image</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="uploading" @click="upload()">
          Save
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
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
          <v-subheader>Image</v-subheader>
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

export default {
  name: "ImageForm",
  mixins: [validationMixin],

  validations: {
    title: { required, maxLength: maxLength(30) },
    description: { required, maxLength: maxLength(500) },
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
      title: null,
      description: null,
      clearCover: false,
      coverPicData: null
    };
  },
  methods: {
    upload() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.$emit("save", {
        coverPic: this.coverPicData,
        title: this.title,
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
      this.title = this.data.title;
      this.description = this.data.description;
    }
  },
  computed: {
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
        errors.push("Image is required");
      }
      return errors;
    }
  },
  watch: {
    clear(val) {
      if (val) {
        this.$v.$reset();
        this.title = "";
        this.description = "";
        this.clearCover = true; // clear single-image-upload
      }
    },
  },
};
</script>

<style scoped></style>
