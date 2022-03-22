<template>
  <v-row
    align="center"
    justify="center"
    v-show="basicInfo.latitude && basicInfo.longitude"
  >
    <GmapMap
      :center="{ lat: basicInfo.latitude, lng: basicInfo.longitude }"
      :zoom="10"
      map-type-id="terrain"
      style="width: 300px; height: 150px;"
    >
      <GmapMarker
        :position="{ lat: basicInfo.latitude, lng: basicInfo.longitude }"
        :clickable="true"
        :draggable="true"
        @click="center = { lat: basicInfo.latitude, lng: basicInfo.longitude }"
      />
    </GmapMap>
  </v-row>
</template>
<script>
export default {
  name: "profile-map",
  props: {
    basicInfo: {
      type: Object,
      required: true,
    },
  },
};
</script>
