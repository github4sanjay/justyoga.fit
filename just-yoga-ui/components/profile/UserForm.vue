<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Update Profile Details</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="updating" @click="submit">
          Update
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
      <v-row dense>
        <v-col cols="3" sm="4">
          <v-subheader>Email</v-subheader>
        </v-col>
        <v-col cols="9" sm="8">
          <v-text-field v-model="email" label="Email" disabled></v-text-field>
        </v-col>
      </v-row>
      <v-row dense>
        <v-col cols="3" sm="4">
          <v-subheader>Name</v-subheader>
        </v-col>
        <v-col cols="9" sm="8">
          <v-text-field
            v-model="name"
            name="name"
            label="Name"
            :error-messages="nameErrors"
            :counter="30"
            @input="$v.name.$touch()"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row dense>
        <v-col cols="3" sm="4">
          <v-subheader>Mobile</v-subheader>
        </v-col>
        <v-col cols="9" sm="8">
          <v-text-field
            v-model="mobile"
            name="mobile"
            label="Mobile Number"
            :error-messages="mobileErrors"
            :counter="10"
            @input="$v.mobile.$touch()"
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
            :counter="150"
            @input="$v.description.$touch()"
          />
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, maxLength, numeric } from "vuelidate/lib/validators";
import BaseBtn from "@/components/base/BaseBtn";

const isCorrectLength = (value) =>
  value === "" || value === null || value.length === 10;

export default {
  name: "UserForm",
  mixins: [validationMixin],

  validations: {
    name: { required, maxLength: maxLength(30) },
    mobile: { numeric, isCorrectLength },
    description: { maxLength: maxLength(150) },
  },
  components: {
    BaseBtn,
  },
  props: {
    user: {
      type: Object,
      required: true,
    },
    updating: {
      type: Boolean,
      required: false,
    },
  },
  data: function () {
    return {
      name: "",
      email: "",
      mobile: "",
      description: "",
    };
  },
  methods: {
    submit() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.$emit("update", {
        id: this.user.id,
        name: this.name,
        description: this.description,
        phoneNumber: this.mobile,
      });
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
  },
  computed: {
    nameErrors() {
      const errors = [];
      if (!this.$v.name.$dirty) return errors;
      !this.$v.name.maxLength &&
        errors.push("Name must be at most 30 characters long");
      !this.$v.name.required && errors.push("Name is required.");
      return errors;
    },
    mobileErrors() {
      const errors = [];
      if (!this.$v.mobile.$dirty) return errors;
      !this.$v.mobile.numeric && errors.push("Mobile number can be integers.");
      !this.$v.mobile.isCorrectLength &&
        errors.push("Mobile number must be at most 10 characters long");
      return errors;
    },
    descriptionErrors() {
      const errors = [];
      if (!this.$v.description.$dirty) return errors;
      !this.$v.description.maxLength &&
        errors.push("Description must be at most 150 characters long");
      return errors;
    },
  },
  async created() {
    this.name = this.user.name;
    this.email = this.user.email;
    this.mobile = this.user.phoneNumber;
    this.description = this.user.description;
  },
  mounted() {},
};
</script>

<style scoped></style>
