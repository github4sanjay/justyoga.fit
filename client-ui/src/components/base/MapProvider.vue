<template>
  <GoogleMapLoader
    :mapConfig="mapConfig"
    apiKey="AIzaSyCg9KqjmKWZOFws3MUGPdx4uPJgjlndwds"
  >
    <template slot-scope="{ google, map }">
      <GoogleMapMarker
        v-for="marker in markers"
        :key="marker.id"
        :marker="marker"
        :google="google"
        :map="map"
      />
      <GoogleMapLine
        v-for="line in lines"
        :key="line.id"
        :path.sync="line.path"
        :google="google"
        :map="map"
      />
    </template>
  </GoogleMapLoader>
</template>

<script>
import GoogleMapLoader from "components/base/GoogleMapLoader";
import GoogleMapMarker from "components/base/GoogleMapMarker";
import GoogleMapLine from "components/base/GoogleMapLine";
import { mapSettings } from "../../constants/MapSettings";

export default {
  name: "MapProvider",
  components: { GoogleMapLoader, GoogleMapMarker, GoogleMapLine },
  props: {
    markers: {
      type: Array,
      required: true
    },
    lines: {
      type: Array,
      required: true
    },
    center: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      // markers: [
      //   { id: "a", position: { lat: 3, lng: 101 } },
      //   { id: "b", position: { lat: 5, lng: 99 } },
      //   { id: "c", position: { lat: 6, lng: 97 } }
      // ],
      // lines: [
      //   {
      //     id: "1",
      //     path: [
      //       { lat: 3, lng: 101 },
      //       { lat: 5, lng: 99 }
      //     ]
      //   },
      //   {
      //     id: "2",
      //     path: [
      //       { lat: 5, lng: 99 },
      //       { lat: 6, lng: 97 }
      //     ]
      //   }
      // ]
    };
  },
  computed: {
    mapConfig() {
      return {
        ...mapSettings,
        center: this.center,
        mapTypeId: "terrain"
      };
    }

    // mapCenter() {
    //   return this.markers[1].position;
    // }
  }
};
</script>
