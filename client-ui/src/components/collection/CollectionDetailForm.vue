<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title
        :class="{
          'text-subtitle1': $q.screen.xs
        }"
        >Update collection details</q-toolbar-title
      >
      <q-btn flat label="Save" :loading="saving" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="collectionDetailForm">
        <div class="row q-pa-xs">
          <div class="col-12">
            <q-input
              v-model="name"
              label="Name"
              maxlength="30"
              counter
              :rules="[
                val => !!val || '* Required',
                val =>
                  (val.length > 10 && val.length <= 50) ||
                  'Min 10 and Max 50 characters'
              ]"
              lazy-rules
            />
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <q-input
              outlined
              v-model="description"
              label="Description"
              maxlength="500"
              counter
              :rules="[
                val => !!val || '* Required',
                val =>
                  (val.length > 100 && val.length < 500) ||
                  'Min 100 and Max 500 characters'
              ]"
              lazy-rules
              type="textarea"
            />
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="q-mt-md">
              Enter location where user can search you
              <span v-if="locationError" class="text-overline text-red q-ml-lg">
                {{ locationError }}
              </span>
              <address-with-fetch
                :clear="clear"
                :location="location"
                @change-location="location = $event"
              />
            </div>
          </div>
        </div>
      </q-form>
    </div>
  </div>
</template>

<script>
import AddressWithFetch from "components/address/AddressWithFetch";

export default {
  name: "CollectionForm",
  components: { AddressWithFetch },
  props: {
    data: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      name: null,
      description: null,
      location: null,
      locationError: null,
      saving: false,
      clear: false
    };
  },
  methods: {
    upload() {
      this.locationError = null; //clear
      this.$refs.collectionDetailForm.validate().then(success => {
        if (success) {
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
            this.save({
              name: this.name,
              description: this.description,
              location: this.location
            });
          }
        } else {
          return;
        }
      });
    },
    async save(val) {
      this.clear = false;
      this.saving = true;
      try {
        let collectionData = await this.$store.dispatch(
          "place/storePlace",
          val
        );
        collectionData.name = val.name;
        collectionData.description = val.description;
        if (this.data) {
          collectionData.id = this.data.id;
        }
        this.$store
          .dispatch("collection/store", collectionData)
          .then(() => {
            this.saving = false;
            this.$emit("close");
            this.clearForm(); // set again false so that it cab be cleared again
            this.clear = true;
          })
          .catch(() => {
            this.saving = false;
          });
      } catch (e) {
        this.$q.notify({
          color: "negative",
          message: "Error in saving collection details"
        });
      }
    },
    closeBtnClick() {
      this.$emit("close");
    },
    clearForm() {
      this.name = "";
      this.description = "";
      this.location = null;
    }
  },
  created() {
    if (this.data) {
      this.name = this.data.name;
      this.description = this.data.description;
      let aAL1 = this.$store.getters["place/administrativeAreaLevel1"](
        this.data.administrativeAreaLevel1Id
      );

      let country = this.$store.getters["place/country"](this.data.countryId);
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
        postalCode: this.data.postalCode
      };
    }
  },
  watch: {
    data(val) {
      if (val) {
        this.name = this.data.name;
        this.description = this.data.description;
        let aAL1 = this.$store.getters["place/administrativeAreaLevel1"](
          this.data.administrativeAreaLevel1Id
        );

        let country = this.$store.getters["place/country"](this.data.countryId);
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
          postalCode: this.data.postalCode
        };
      }
    }
  }
};
</script>

<style scoped></style>
