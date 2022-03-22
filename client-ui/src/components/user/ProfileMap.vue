<template>
  <map-provider :markers="markers" :lines="[]" :center="center" />
</template>
<script>
import MapProvider from "components/base/MapProvider";
export default {
  name: "ProfileMap",
  components: { MapProvider },
  props: {
    basicInfo: {
      type: Object,
      required: true
    }
  },
  computed: {
    markers() {
      return [
        {
          id: "a",
          position: {
            lat: this.basicInfo.latitude,
            lng: this.basicInfo.longitude
          }
        }
      ];
    },
    center() {
      return { lat: this.basicInfo.latitude, lng: this.basicInfo.longitude };
    }
  }
};
</script>
