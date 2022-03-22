<template>
  <v-container class="pa-0">
    <v-row v-if="location" dense>
      <v-col cols="12">
        <v-text-field
          dense
          label="Formatted Address"
          v-model="location.formattedAddress"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field
          dense
          label="Sub-Locality"
          :disabled="isSubLocalityDisable"
          v-model="location.subLocalityLevel2"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field
          dense
          label="Locality"
          :disabled="isLocalityDisable"
          v-model="location.subLocalityLevel1"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field
          dense
          label="City"
          :disabled="isCityDisable"
          v-model="location.locality"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field
          dense
          label="State"
          :disabled="isStateDisable"
          v-model="location.administrativeAreaLevel1"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field
          dense
          label="Country"
          :disabled="isCountryDisable"
          v-model="location.country"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field
          dense
          label="Postal Code"
          :disabled="isPostalCodeDisable"
          v-model="location.postalCode"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field
          dense
          label="Longitude"
          :disabled="isLongitudeDisable"
          v-model="location.longitude"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field
          dense
          label="Latitude"
          :disabled="isLatitudeyDisable"
          v-model="location.latitude"></v-text-field>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Address",
  props: ["location"],
  data() {
    return {
      isSubLocalityDisable: false,
      isLocalityDisable: false,
      isCityDisable: false,
      isStateDisable: false,
      isCountryDisable: false,
      isLatitudeyDisable: false,
      isLongitudeDisable: false,
      isPostalCodeDisable: false
    };
  },
  created() {
    if (
      this.location.subLocalityLevel2 !== null &&
      this.location.subLocalityLevel2 !== undefined
    ) {
      this.isSubLocalityDisable = true;
    }
    if (
      this.location.subLocalityLevel1 !== null &&
      this.location.subLocalityLevel1 !== undefined
    ) {
      this.isLocalityDisable = true;
    }
    if (
      this.location.locality !== null &&
      this.location.locality !== undefined
    ) {
      this.isCityDisable = true;
    }
    if (
      this.location.administrativeAreaLevel1 !== null &&
      this.location.administrativeAreaLevel1 !== undefined
    ) {
      this.isStateDisable = true;
    }
    if (this.location.country !== null && this.location.country !== undefined) {
      this.isCountryDisable = true;
    }
    if (
      this.location.postalCode !== null &&
      this.location.postalCode !== undefined
    ) {
      this.isPostalCodeDisable = true;
    }
    if (
      this.location.longitude !== null &&
      this.location.longitude !== undefined
    ) {
      this.isLongitudeDisable = true;
    }
    if (
      this.location.latitude !== null &&
      this.location.latitude !== undefined
    ) {
      this.isLatitudeyDisable = true;
    }
  }
};
</script>

<style scoped>
</style>
