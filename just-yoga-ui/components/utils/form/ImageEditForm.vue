<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Update Image Details</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="updating" @click="upload()">
          Update
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
    </v-container>
  </v-container>
</template>

<script>
import BaseBtn from "@/components/base/BaseBtn";
import { validationMixin } from "vuelidate";
import { maxLength, required } from "vuelidate/lib/validators";

export default {
  name: "ImageEditForm",
  mixins: [validationMixin],

  validations: {
    title: { required, maxLength: maxLength(30) },
    description: { required, maxLength: maxLength(500) },
  },

  components: { BaseBtn },
  props: {
    updating: {
      type: Boolean,
      required: true,
    },
    image: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      title: null,
      description: null,
    };
  },
  methods: {
    upload() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.$emit("save", {
        title: this.title,
        description: this.description,
        id: this.image.id
      });
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
  },
  created() {
    if (this.image){
      this.title = this.image.title;
      this.description = this.image.description;
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
    }
  },
  watch: {
    image(val) {
      if (val){
        this.title = val.title;
        this.description = val.description;
      }
    }
  },
};
</script>

<style scoped></style>
