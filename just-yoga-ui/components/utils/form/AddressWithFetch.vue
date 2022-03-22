<template>
  <v-container py-0 my-0>
    <v-row dense justify="center">
      <v-col cols="12">
        <v-alert type="error" :value="locationError" dismissible>
          {{ locationErrorText }}
        </v-alert>
      </v-col>
      <v-col cols="12">
        <v-autocomplete
          v-model="model"
          :items="items"
          :loading="isLoading"
          :search-input.sync="search"
          color="accent"
          :filter="filter"
          item-text="Description"
          placeholder="Start typing to Search"
          prepend-icon="search"
          return-object
          outlined
          dense
        />
      </v-col>
    </v-row>
    <v-expand-transition>
      <Address :location="var_location" v-if="var_location"/>
      <!-- <small>*indicates required field</small> -->
    </v-expand-transition>
    <v-row>
      <v-col cols="6" sm="4" md="4" v-if="!loadingLocation">
        <v-btn
          small
          color="success"
          @click.stop="fetchUserLocation"
        >Enable Location</v-btn
        >
      </v-col>
      <v-col cols="6" sm="2" md="2">
        <v-btn
          small
          :disabled="!var_location"
          color="success"
          @click="var_location = null"
        >
          Clear
          <v-icon right>highlight_off</v-icon>
        </v-btn>
      </v-col>
      <v-col v-if="loadingLocation" sm="6" class="text-xs-center mt-1">
        <v-progress-circular
          indeterminate
          class="accent--text"
          :width="3"
          :size="30"
        />
        <p>Fetching Location</p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Address from "@/components/utils/form/Address";
import axios from "axios";
import ApiEndpoints from "@/constants/ApiEndpoints";

export default {
  name: "AddressWithFetch",
  props: ["location"],
  components: { Address },
  data() {
    return {
      loadingLocation: false,
      locationError: false,
      locationErrorText: "",
      var_location: this.location,
      entries: [],
      isLoading: false,
      model: null,
      search: null,
      sessionId: null,
      idToken: null,
    };
  },
  computed: {
    items() {
      return this.entries.map((entry) => {
        const Description =
          entry.description.length > this.descriptionLimit
            ? entry.description.slice(0, this.descriptionLimit) + "..."
            : entry.description;
        return Object.assign({}, entry, { Description });
      });
    },
  },
  created() {
  },
  methods: {
    filter(item, queryText, itemText){
      return true;
    },
    /**
     * Fetches user current location
     * if user allows browser to fetch location,
     * if user do not allow browser to fetch location
     * then it will show error message
     *
     * After fetching the location it store location in the location variable
     */
    fetchUserLocation() {
      this.loadingLocation = true;
      this.locationError = null;
      if (navigator.geolocation) {
        try {
          navigator.geolocation.getCurrentPosition(
            async (position) => {
              const configData = {
                url: ApiEndpoints.REVERSE_GEOCODE,
                method: "get",
                params: {
                  latitude: position.coords.latitude,
                  longitude: position.coords.longitude,
                },
                withCredentials: true
              };
              let { data } = await axios
                .request(configData)
                .catch( error => {
                  if (error.response) {
                    this.locationErrorText = error.response.data.data.errMsg;
                    this.locationError = true;
                    this.loadingLocation = false;
                  }
                });
              this.var_location = data.data;
              this.loadingLocation = false;
            },
            (err) => {
              this.loadingLocation = false;
              this.locationErrorText = err.message;
              this.locationError = true;
            }
          );
        }catch (e) {
          this.$nuxt.error({ statusCode: 500})
        }
      } else {
        this.loadingLocation = false;
        this.locationErrorText = "Cannot access location.";
        this.locationError = true;
      }
    },
  },
  watch: {
    async search(val) {
      this.locationError = false;
      if (val == null) return;
      if (val.length === 1) this.model = null;

      if (val.length < 3 || this.model != null) return;
      this.isLoading = true;
      this.entries = []; //clear it before loading
      const configData = {
        url: ApiEndpoints.AUTOCOMPLETE_RESULTS,
        method: "get",
        params: {
          query: val,
          sessionId: this.sessionId === null ? "" : this.sessionId,
        },
        withCredentials: true
      };
      try {
        let { data } = await axios
          .request(configData)
          .catch( error => {
            if (error.response) {
              this.locationErrorText = error.response.data.data.errMsg;
              this.locationError = true;
              this.isLoading = false;
            }
          });
        this.entries = data.data.autocompleteList;
        this.sessionId = data.data.sessionId;
        this.isLoading = false;
      }catch (e) {
        this.$nuxt.error({ statusCode: 500})
      }
    },
    var_location(val) {
      this.$emit("change-location", val);
    },
    async model(val) {
      if (val == null || undefined) {
        this.var_location = null;
        return;
      }
      this.loadingLocation = true;

      const configData = {
        url: ApiEndpoints.PLACE_DETAILS,
        method: "get",
        params: {
          placeId: val.placeId,
          sessionId: this.sessionId === null ? "" : this.sessionId,
        },
        withCredentials: true
      };
      try {
        let { data } = await axios
          .request(configData)
          .catch( error => {
            if (error.response) {
              this.locationErrorText = error.response.data.data.errMsg;
              this.locationError = true;
              this.loadingLocation = false;
            }
          });
        this.var_location = data.data;
        this.loadingLocation = false;
      }catch (e) {
        this.$nuxt.error({ statusCode: 500})
      }
    },
  },
};
</script>

<style scoped></style>
