<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Add New Collection</v-toolbar-title>
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
      <v-row>
        <v-col>
          <v-card flat>
            <v-card-text
              :class="{
                'justify-center': $vuetify.breakpoint.smAndDown,
                headline: $vuetify.breakpoint.mdAndUp,
                title: $vuetify.breakpoint.sm,
                'subtitle-2': $vuetify.breakpoint.xsOnly,
              }"
            >
              Enter location where user can search you
              <span v-if="locationError" class="error--text subtitle-2">
                {{ locationError }}
              </span>
            </v-card-text>
            <address-with-fetch
              :location="location"
              @change-location="location = $event"
            />
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import BaseBtn from "@/components/base/BaseBtn";
import { validationMixin } from "vuelidate";
import { maxLength, required } from "vuelidate/lib/validators";
import SingleImageUpload from "../utils/SingleImageUpload";
import AddressWithFetch from "../utils/form/AddressWithFetch";

export default {
  name: "CollectionForm",
  mixins: [validationMixin],

  validations: {
    name: { required, maxLength: maxLength(30) },
    description: { required, maxLength: maxLength(500) },
  },

  components: { AddressWithFetch, SingleImageUpload, BaseBtn },
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
    },
  },
  data() {
    return {
      name: null,
      description: null,
      location: null,
      locationError: null,
    };
  },
  methods: {
    upload() {
      this.locationError = null; //clear
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      if (!this.location) {
        this.locationError = "Location is mandatory";
      } else if (!this.location.country) {
        this.locationError = "Country is mandatory";
      } else if (!this.location.administrativeAreaLevel1) {
        this.locationError = "State is mandatory";
      } else if (!this.location.formattedAddress) {
        this.locationError = "Formatted Address is mandatory";
      } else if (!this.location.locality) {
        this.locationError = "City is mandatory";
      } else if (!this.location.subLocalityLevel1) {
        this.locationError = "Locality is mandatory";
      } else if (!this.location.subLocalityLevel2) {
        this.locationError = "Sub-locality is mandatory";
      } else if (!this.location.postalCode) {
        this.locationError = "Postal Code is mandatory";
      } else if (!this.location.latitude) {
        this.locationError = "Latitude is mandatory";
      } else if (!this.location.longitude) {
        this.locationError = "Longitude is mandatory";
      } else if (!this.location.geohash1) {
        this.locationError = "geohash1 is mandatory";
      } else if (!this.location.geohash5) {
        this.locationError = "geohash5 is mandatory";
      } else if (!this.location.geohash50) {
        this.locationError = "geohash50 is mandatory";
      } else if (!this.location.geohash150) {
        this.locationError = "geohash150 is mandatory";
      } else {
        this.$emit("save", {
          name: this.name,
          description: this.description,
          location: this.location,
        });
      }
    },
    closeBtnClick() {
      this.$emit("close", {});
    }
  },
  created() {
    if (this.data) {
      this.name = this.data.name;
      this.description = this.data.description;
      let aAL1 = this.$store.getters["place/administrativeAreaLevel1"](
        this.data.administrativeAreaLevel1Id
      );

      let country = this.$store.getters["place/country"](
        this.data.countryId
      );
      let locality = this.$store.getters["place/locality"](
        this.data.localityId
      );

      let subLocality1 = this.$store.getters["place/subLocalityLevel1"](
        this.data.subLocalityLevel1Id
      );

      let subLocality2 = this.$store.getters["place/subLocalityLevel2"](
        this.data.subLocalityLevel2Id
      );

      this.location = {
        administrativeAreaLevel1: aAL1 ? aAL1.name : "",
        country: country ? country.name : "",
        locality: locality ? locality.name : "",
        subLocalityLevel1: subLocality1 ? subLocality1.name : "",
        subLocalityLevel2: subLocality2 ? subLocality2.name : "",
        formattedAddress: this.data.formattedAddress,
        geohash1: this.data.geohash1,
        geohash150: this.data.geohash150,
        geohash5: this.data.geohash5,
        geohash50: this.data.geohash50,
        latitude: this.data.latitude,
        longitude: this.data.longitude,
        postalCode: this.data.postalCode,
      };
    }
  },
  computed: {
    nameErrors() {
      const errors = [];
      if (!this.$v.name.$dirty) return errors;
      !this.$v.name.maxLength &&
        errors.push("Name must be at most 30 characters long");
      !this.$v.name.required && errors.push("Name is required");
      return errors;
    },
    descriptionErrors() {
      const errors = [];
      if (!this.$v.description.$dirty) return errors;
      !this.$v.description.required && errors.push("Description is required");
      !this.$v.description.maxLength &&
        errors.push("Description must be at most 500 characters long");
      return errors;
    }
  },
  watch: {
    clear(val) {
      if (val) {
        this.$v.$reset();
        this.name = "";
        this.description = "";
      }
    },
    data(val) {
      if (val){
        this.name = this.data.name;
        this.description = this.data.description;
        let aAL1 = this.$store.getters["place/administrativeAreaLevel1"](
          this.data.administrativeAreaLevel1Id
        );

        let country = this.$store.getters["place/country"](
          this.data.countryId
        );
        let locality = this.$store.getters["place/locality"](
          this.data.localityId
        );

        let subLocality1 = this.$store.getters["place/subLocalityLevel1"](
          this.data.subLocalityLevel1Id
        );

        let subLocality2 = this.$store.getters["place/subLocalityLevel2"](
          this.data.subLocalityLevel2Id
        );

        this.location = {
          administrativeAreaLevel1: aAL1 ? aAL1.name : "",
          country: country ? country.name : "",
          locality: locality ? locality.name : "",
          subLocalityLevel1: subLocality1 ? subLocality1.name : "",
          subLocalityLevel2: subLocality2 ? subLocality2.name : "",
          formattedAddress: this.data.formattedAddress,
          geohash1: this.data.geohash1,
          geohash150: this.data.geohash150,
          geohash5: this.data.geohash5,
          geohash50: this.data.geohash50,
          latitude: this.data.latitude,
          longitude: this.data.longitude,
          postalCode: this.data.postalCode,
        };
      }
    }
  },
};
</script>

<style scoped></style>
