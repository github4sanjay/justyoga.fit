<template>
  <div>
    <div v-if="location" class="row">
      <div class="col-12">
        <q-input
          label="Formatted Address"
          v-model="location.formattedAddress"
        />
      </div>
      <div class="col-6">
        <q-input
          label="Sub-Locality"
          :disable="isSubLocalityDisable"
          v-model="location.subLocalityLevel2"
        />
      </div>
      <div class="col-6">
        <q-input
          label="Locality"
          :disable="isLocalityDisable"
          v-model="location.subLocalityLevel1"
        />
      </div>
      <div class="col-6">
        <q-input
          label="City"
          :disable="isCityDisable"
          v-model="location.locality"
        />
      </div>
      <div class="col-6">
        <q-input
          label="State"
          :disable="isStateDisable"
          v-model="location.administrativeAreaLevel1"
        />
      </div>
      <div class="col-6">
        <q-input
          label="Country"
          :disable="isCountryDisable"
          v-model="location.country"
        />
      </div>
      <div class="col-6">
        <q-input
          label="Postal Code"
          :disable="isPostalCodeDisable"
          v-model="location.postalCode"
        />
      </div>
      <div class="col-6">
        <q-input
          label="Longitude"
          :disable="isLongitudeDisable"
          v-model="location.longitude"
        />
      </div>
      <div class="col-6">
        <q-input
          label="Latitude"
          :disable="isLatitudeyDisable"
          v-model="location.latitude"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Address",
  props: ["location"],
  data() {
    return {
      isSubLocalityDisable: true,
      isLocalityDisable: true,
      isCityDisable: true,
      isStateDisable: true,
      isCountryDisable: true,
      isLatitudeyDisable: true,
      isLongitudeDisable: true,
      isPostalCodeDisable: true
    };
  },
  methods: {
    checkDisableField(val) {
      if (
        this.location.subLocalityLevel2 !== null &&
        this.location.subLocalityLevel2 !== undefined
      ) {
        this.isSubLocalityDisable = true;
      } else {
        this.isSubLocalityDisable = false;
      }
      if (
        this.location.subLocalityLevel1 !== null &&
        this.location.subLocalityLevel1 !== undefined
      ) {
        this.isLocalityDisable = true;
      } else {
        this.isLocalityDisable = false;
      }
      if (
        this.location.locality !== null &&
        this.location.locality !== undefined
      ) {
        this.isCityDisable = true;
      } else {
        this.isCityDisable = false;
      }
      if (
        this.location.administrativeAreaLevel1 !== null &&
        this.location.administrativeAreaLevel1 !== undefined
      ) {
        this.isStateDisable = true;
      } else {
        this.isStateDisable = false;
      }
      if (
        this.location.country !== null &&
        this.location.country !== undefined
      ) {
        this.isCountryDisable = true;
      } else {
        this.isCountryDisable = false;
      }
      if (
        this.location.postalCode !== null &&
        this.location.postalCode !== undefined
      ) {
        this.isPostalCodeDisable = true;
      } else {
        this.isPostalCodeDisable = false;
      }
      if (
        this.location.longitude !== null &&
        this.location.longitude !== undefined
      ) {
        this.isLongitudeDisable = true;
      } else {
        this.isLongitudeDisable = false;
      }
      if (
        this.location.latitude !== null &&
        this.location.latitude !== undefined
      ) {
        this.isLatitudeyDisable = true;
      } else {
        this.isLatitudeyDisable = false;
      }
    }
  },
  watch: {
    location(val) {
      this.checkDisableField(val);
    }
  },
  created() {
    this.checkDisableField(this.location);
  }
};
</script>

<style scoped></style>
