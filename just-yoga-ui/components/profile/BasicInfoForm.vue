<template>
  <v-container class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Update Basic Information</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="updating" @click="submit">
          Update
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
    <v-row>
      <v-col>
        <v-card flat>
          <v-row dense>
            <v-col cols="6" sm="8">
              <v-card flat>
                <v-card-text
                  :class="{
                    'justify-center': $vuetify.breakpoint.smAndDown,
                    headline: $vuetify.breakpoint.mdAndUp,
                    title: $vuetify.breakpoint.sm,
                    'subtitle-2': $vuetify.breakpoint.xsOnly,
                  }"
                >
                  Select your age
                </v-card-text>
              </v-card>
            </v-col>
            <v-col cols="3" sm="2">
              <v-text-field
                v-model="var_age"
                dense
                hide-details
                label="Years"
                single-line
                type="number"
                style="width: 60px;"
                max="100"
                min="0"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <v-row dense>
      <v-col cols="12">
        <v-card flat>
          <v-row dense>
            <v-col cols="6" sm="8">
              <v-card flat>
                <v-card-text
                  :class="{
                    'justify-center': $vuetify.breakpoint.smAndDown,
                    headline: $vuetify.breakpoint.mdAndUp,
                    title: $vuetify.breakpoint.sm,
                    'subtitle-2': $vuetify.breakpoint.xsOnly,
                  }"
                >
                  Select years of experience
                </v-card-text>
              </v-card>
            </v-col>
            <v-col cols="3" sm="2">
              <v-text-field
                v-model="var_experience_years"
                dense
                hide-details
                label="Years"
                single-line
                type="number"
                style="width: 60px;"
                max="100"
                min="0"
              ></v-text-field>
            </v-col>
            <v-col cols="3" sm="2">
              <v-text-field
                v-model="var_experience_months"
                dense
                hide-details
                label="Months"
                single-line
                type="number"
                style="width: 70px;"
                max="12"
                min="0"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-card>
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
import AddressWithFetch from "@/components/utils/form/AddressWithFetch";
import BaseBtn from "@/components/base/BaseBtn";

export default {
  name: "BasicInfoForm",

  components: {
    AddressWithFetch,
    BaseBtn,
  },

  props: {
    user: {
      type: Object,
      default: null,
    },
    updating: {
      type: Boolean,
      required: true,
      default: false,
    },
  },

  data: function () {
    return {
      var_age: 0,
      var_experience_months: 0,
      var_experience_years: 0,
      location: null,
    };
  },

  created() {
    if (this.user) {
      let aAL1 = this.$store.getters["place/administrativeAreaLevel1"](
        this.user.administrativeAreaLevel1Id
      );

      let country = this.$store.getters["place/country"](
        this.user.countryId
      );
      let locality = this.$store.getters["place/locality"](
        this.user.localityId
      );

      let subLocality1 = this.$store.getters["place/subLocalityLevel1"](
        this.user.subLocalityLevel1Id
      );

      let subLocality2 = this.$store.getters["place/subLocalityLevel2"](
        this.user.subLocalityLevel2Id
      );

      this.var_experience_months = this.user.experienceMonths;
      this.var_experience_years = this.user.experienceYears;
      this.var_age = this.user.age;
      this.location = {
        administrativeAreaLevel1: aAL1 ? aAL1.name : "",
        country: country ? country.name : "",
        locality: locality ? locality.name : "",
        subLocalityLevel1: subLocality1 ? subLocality1.name : "",
        subLocalityLevel2: subLocality2 ? subLocality2.name : "",
        formattedAddress: this.user.formattedAddress,
        geohash1: this.user.geohash1,
        geohash150: this.user.geohash150,
        geohash5: this.user.geohash5,
        geohash50: this.user.geohash50,
        latitude: this.user.latitude,
        longitude: this.user.longitude,
        postalCode: this.user.postalCode,
      };
    }
  },

  methods: {
    submit() {
      if (!this.var_age) {
        this.$emit("error", "Age is mandatory");
      } else if (this.var_age < 18 || this.var_age > 100) {
        this.$emit("error", "Age should be between 18 and 100");
      } else if (this.var_experience_months && this.var_experience_months < 0) {
        this.$emit("error", "Experience years should be positive");
      } else if (
        this.var_experience_months &&
        this.var_experience_months > 12
      ) {
        this.$emit("error", "Experience months should not be greater than 12");
      } else if (!this.location) {
        this.$emit("error", "Location is mandatory");
      } else if (!this.location.country) {
        this.$emit("error", "Country is mandatory");
      } else if (!this.location.administrativeAreaLevel1) {
        this.$emit("error", "State is mandatory");
      } else if (!this.location.formattedAddress) {
        this.$emit("error", "Formatted Address is mandatory");
      } else if (!this.location.locality) {
        this.$emit("error", "City is mandatory");
      } else if (!this.location.subLocalityLevel1) {
        this.$emit("error", "Locality is mandatory");
      } else if (!this.location.subLocalityLevel2) {
        this.$emit("error", "Sub-locality is mandatory");
      } else if (!this.location.postalCode) {
        this.$emit("error", "Postal Code is mandatory");
      } else if (!this.location.latitude) {
        this.$emit("error", "Latitude is mandatory");
      } else if (!this.location.longitude) {
        this.$emit("error", "Longitude is mandatory");
      } else if (!this.location.geohash1) {
        this.$emit("error", "geohash1 is mandatory");
      } else if (!this.location.geohash5) {
        this.$emit("error", "geohash5 is mandatory");
      } else if (!this.location.geohash50) {
        this.$emit("error", "geohash50 is mandatory");
      } else if (!this.location.geohash150) {
        this.$emit("error", "geohash150 is mandatory");
      } else {
        this.$emit("update-trainer", {
          location: this.location,
          experienceMonths: this.var_experience_months,
          experienceYears: this.var_experience_years,
          age: this.var_age,
        });
      }
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
  },

  computed: {},
};
</script>

<style scoped></style>
