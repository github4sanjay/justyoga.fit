<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Edit Profile Picture</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="uploading" @click="upload()">
          Save
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
      <single-image-upload
        @image-data="onChangeInImages"
        :errors="imageErrors"
        :ratio="1"
      />
    </v-container>
  </v-container>
</template>

<script>
import SingleImageUpload from "../utils/SingleImageUpload";
import {validationMixin} from "vuelidate";

export default {
  name: "ProfilePicForm",
  mixins: [validationMixin],

  validations: {
  },

  components: { SingleImageUpload },
  props: {
    uploading: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      imageData: null
    };
  },
  methods: {
    upload() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.$emit("save", {
        image: this.imageData,
      });
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
    onChangeInImages(val) {
      this.imageData = val;
    },
  },
  computed: {
    imageErrors() {
      const errors = [];
      if (!this.imageData || !this.imageData.blob) {
        errors.push("Image is required");
      }
      return errors;
    }
  },
};
</script>

<style scoped></style>
