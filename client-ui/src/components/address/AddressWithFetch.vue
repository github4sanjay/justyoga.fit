<template>
  <div>
    <div class="row">
      <div class="col-12" v-if="locationErrorText">
        <q-banner inline-actions rounded class="bg-negative text-white">
          {{ locationErrorText }}
          <template v-slot:action>
            <q-btn flat label="Dismiss" @click="locationErrorText = null" />
          </template>
        </q-banner>
      </div>
      <div class="col-12">
        <q-select
          dense
          outlined
          v-model="model"
          use-input
          fill-input
          input-debounce="500"
          label="Search Address"
          :options="entries"
          option-value="placeId"
          option-label="description"
          @filter="filterFn"
        >
          <template v-slot:no-option>
            <q-item>
              <q-item-section class="text-grey">
                No results
              </q-item-section>
            </q-item>
          </template>
        </q-select>
      </div>
    </div>
    <q-slide-transition>
      <div v-show="var_location">
        <Address :location="var_location" v-if="var_location" />
      </div>
    </q-slide-transition>
    <div class="row q-mt-md">
      <span class="q-pa-sm">
        <q-btn
          color="positive"
          :size="$q.screen.lt.md ? 'sm' : '10px'"
          :loading="loadingLocation"
          label="Current Location"
          @click.stop="fetchUserLocation"
        />
      </span>
      <span class="q-pa-sm">
        <q-btn
          color="negative"
          :size="$q.screen.lt.md ? 'sm' : '10px'"
          :disable="!var_location"
          label="Clear Location"
          @click="var_location = null"
        />
      </span>
      <!-- <div class="col-6 col-md-4">
        <q-btn
          color="positive"
          :size="$q.screen.lt.md ? 'sm' : '10px'"
          :loading="loadingLocation"
          label="Current Location"
          @click.stop="fetchUserLocation"
        />
      </div>
      <div class="col-6 col-md-4">
        <q-btn
          color="negative"
          :size="$q.screen.lt.md ? 'sm' : '10px'"
          :disable="!var_location"
          label="Clear Location"
          @click="var_location = null"
        />
      </div> -->
    </div>
  </div>
</template>

<script>
import Address from "./Address";
import { instance } from "boot/axios";
import ApiEndpoints from "../../constants/ApiEndpoints";

export default {
  name: "AddressWithFetch",
  props: {
    location: {
      type: Object,
      required: false
    },
    clear: {
      type: Boolean,
      required: false
    }
  },
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
      sessionId: null
    };
  },
  created() {},
  methods: {
    filter(item, queryText, itemText) {
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
            async position => {
              const configData = {
                url: ApiEndpoints.REVERSE_GEOCODE,
                method: "get",
                params: {
                  latitude: position.coords.latitude,
                  longitude: position.coords.longitude
                },
                withCredentials: true
              };
              let { data } = await instance.request(configData).catch(error => {
                if (error.response) {
                  this.locationErrorText = error.response.data.data.errMsg;
                  this.locationError = true;
                  this.loadingLocation = false;
                }
              });
              this.var_location = data.data;
              this.loadingLocation = false;
            },
            err => {
              this.loadingLocation = false;
              this.locationErrorText = err.message;
              this.locationError = true;
            }
          );
        } catch (e) {
          this.$q.notify({
            color: "negative",
            message: "Error in fetching location"
          });
        }
      } else {
        this.loadingLocation = false;
        this.locationErrorText = "Cannot access location.";
        this.locationError = true;
      }
    },
    async filterFn(val, update, abort) {
      this.locationError = false;
      if (val == null) {
        abort();
        return;
      }
      if (val.length === 1) {
        this.model = null;
        abort();
        return;
      }
      if (val.length < 3 || this.model != null) {
        abort();
        return;
      }
      this.isLoading = true;
      this.entries = []; //clear it before loading
      const configData = {
        url: ApiEndpoints.AUTOCOMPLETE_RESULTS,
        method: "get",
        params: {
          query: val,
          sessionId: this.sessionId === null ? "" : this.sessionId
        },
        withCredentials: true
      };
      try {
        let { data } = await instance.request(configData).catch(error => {
          if (error.response) {
            this.locationErrorText = error.response.data.data.errMsg;
            this.locationError = true;
            this.isLoading = false;
          }
        });
        this.entries = data.data.autocompleteList;
        this.sessionId = data.data.sessionId;
        this.isLoading = false;
        update();
        return;
      } catch (e) {
        this.$q.notify({
          color: "negative",
          message: "Error in getting location search results"
        });
      }
    }
  },
  watch: {
    clear(val) {
      if (val) {
        this.model = null;
        this.var_location = null;
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
          sessionId: this.sessionId === null ? "" : this.sessionId
        },
        withCredentials: true
      };
      try {
        let { data } = await instance.request(configData).catch(error => {
          if (error.response) {
            this.locationErrorText = error.response.data.data.errMsg;
            this.locationError = true;
            this.loadingLocation = false;
          }
        });
        this.var_location = data.data;
        this.loadingLocation = false;
      } catch (e) {
        this.$q.notify({
          color: "negative",
          message: "Place detail not working"
        });
      }
    }
  }
};
</script>

<style scoped></style>
