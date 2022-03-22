<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Update Basic Information</q-toolbar-title>
      <q-btn
        flat
        label="Update"
        :loading="updatingBasicInfo"
        @click="submit()"
      />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="collectionDetailForm">
        <div class="row justify-center items-center">
          <div class="col-6">
            Select your age
          </div>
          <div class="col-6 col-sm-6">
            <q-input
              dense
              filled
              type="number"
              v-model="var_age"
              label="Your age *"
              lazy-rules
              :rules="[
                val => (val !== null && val !== '') || 'Please type your age',
                val => (val > 0 && val < 100) || 'Please type a real age'
              ]"
            />
          </div>
        </div>
        <div class="row justify-center items-center q-mt-sm">
          <div class="col-6">
            Select years of experience
          </div>
          <div class="col-3 q-pr-sm">
            <q-input
              dense
              filled
              type="number"
              v-model="var_experience_years"
              label="Years *"
              lazy-rules
              :rules="[
                val => (val !== null && val !== '') || 'Please type year',
                val => (val >= 0 && val < 100) || 'Please type a real year'
              ]"
            />
          </div>
          <div class="col-3">
            <q-input
              dense
              filled
              type="number"
              v-model="var_experience_months"
              label="Months *"
              lazy-rules
              :rules="[
                val => (val !== null && val !== '') || 'Please type month',
                val => (val >= 0 && val <= 12) || 'Please type a real month'
              ]"
            />
          </div>
        </div>
        <div class="row justify-center items-center q-mt-sm">
          <div class="col-12">
            Enter location where user can search you
          </div>
          <div class="col-12">
            <address-with-fetch
              :location="location"
              @change-location="location = $event"
            />
          </div>
        </div>
      </q-form>
    </div>
  </div>
</template>

<script>
import AddressWithFetch from "components/address/AddressWithFetch";
import { updateBasicInfo } from "../../store/users/mutations";

export default {
  name: "BasicInfoForm",

  components: {
    AddressWithFetch
  },

  props: {
    user: {
      type: Object,
      default: null
    }
  },

  data: function() {
    return {
      var_age: 0,
      var_experience_months: 0,
      var_experience_years: 0,
      location: null,
      updatingBasicInfo: false
    };
  },

  created() {
    if (this.user && this.user.basicInfo) {
      let basicInfo = this.user.basicInfo;
      let aAL1 = this.$store.getters["place/administrativeAreaLevel1"](
        basicInfo.administrativeAreaLevel1Id
      );

      let country = this.$store.getters["place/country"](basicInfo.countryId);
      let locality = this.$store.getters["place/locality"](
        basicInfo.localityId
      );

      let subLocality1 = this.$store.getters["place/subLocalityLevel1"](
        basicInfo.subLocalityLevel1Id
      );

      let subLocality2 = this.$store.getters["place/subLocalityLevel2"](
        basicInfo.subLocalityLevel2Id
      );

      this.var_experience_months = basicInfo.experienceMonths;
      this.var_experience_years = basicInfo.experienceYears;
      this.var_age = basicInfo.age;
      this.location = {
        administrativeAreaLevel1: aAL1 ? aAL1.name : "",
        country: country ? country.name : "",
        locality: locality ? locality.name : "",
        subLocalityLevel1: subLocality1 ? subLocality1.name : "",
        subLocalityLevel2: subLocality2 ? subLocality2.name : "",
        formattedAddress: basicInfo.formattedAddress,
        geohash1: basicInfo.geohash1,
        geohash150: basicInfo.geohash150,
        geohash5: basicInfo.geohash5,
        geohash50: basicInfo.geohash50,
        latitude: basicInfo.latitude,
        longitude: basicInfo.longitude,
        postalCode: basicInfo.postalCode
      };
    }
  },

  methods: {
    submit() {
      this.$refs.collectionDetailForm.validate().then(success => {
        if (!this.var_age) {
          this.showError("Age is mandatory");
        } else if (this.var_age < 18 || this.var_age > 100) {
          this.showError("Age should be between 18 and 100");
        } else if (
          this.var_experience_months &&
          this.var_experience_months < 0
        ) {
          this.showError("Experience years should be positive");
        } else if (
          this.var_experience_months &&
          this.var_experience_months > 12
        ) {
          this.showError("Experience months should not be greater than 12");
        } else if (!this.location) {
          this.showError("Location is mandatory");
        } else if (!this.location.country) {
          this.showError("Country is mandatory");
        } else if (!this.location.administrativeAreaLevel1) {
          this.showError("tate is mandatory");
        } else if (!this.location.formattedAddress) {
          this.showError("Formatted Address is mandatory");
        } else if (!this.location.locality) {
          this.showError("City is mandatory");
        } else if (!this.location.subLocalityLevel1) {
          this.showError("Locality is mandatory");
        } else if (!this.location.subLocalityLevel2) {
          this.showError("Sub-locality is mandatory");
        } else if (!this.location.postalCode) {
          this.showError("Postal Code is mandatory");
        } else if (!this.location.latitude) {
          this.showError("Latitude is mandatory");
        } else if (!this.location.longitude) {
          this.showError("Longitude is mandatory");
        } else if (!this.location.geohash1) {
          this.showError("geohash1 is mandatory");
        } else if (!this.location.geohash5) {
          this.showError("geohash5 is mandatory");
        } else if (!this.location.geohash50) {
          this.showError("geohash50 is mandatory");
        } else if (!this.location.geohash150) {
          this.showError("geohash150 is mandatory");
        } else {
          this.updateBasicInfo({
            location: this.location,
            experienceMonths: this.var_experience_months,
            experienceYears: this.var_experience_years,
            age: this.var_age
          });
        }
      });
    },
    async updateBasicInfo(val) {
      this.updatingBasicInfo = true;
      try {
        let basicInfo = await this.$store.dispatch("place/storePlace", val);

        basicInfo.age = val.age;
        basicInfo.experienceMonths = val.experienceMonths;
        basicInfo.experienceYears = val.experienceYears;
        basicInfo.userId = this.user.id;
        if (this.user.basicInfo) {
          basicInfo.id = this.user.basicInfo.id;
        }
        this.$store
          .dispatch("users/updateBasicInfo", basicInfo)
          .then(() => {
            this.updatingBasicInfo = false;
            this.closeBtnClick();
          })
          .catch(() => {
            this.updatingBasicInfo = false;
          });
      } catch (e) {
        this.showError("Sorry, something went wrong !");
      }
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
    showError(msg) {
      this.$q.notify({
        color: "negative",
        message: msg
      });
    }
  },

  computed: {}
};
</script>

<style scoped></style>
