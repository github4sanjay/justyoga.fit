<template>
  <v-tabs grow slider-size="5">
    <v-tabs-slider color="primary"/>
    <v-tab v-for="(tabItem, i) in tabItems" :key="i" router :to="tabItem.link">
      {{ tabItem.text }}
    </v-tab>
  </v-tabs>
</template>

<script>
export default {
  name: "DirectoryTab",
  props: {
    tabItems: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style scoped></style>
