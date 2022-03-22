<template>
  <q-tabs
    align="justify"
    :dense="$q.screen.xs"
    :class="{
      'bg-grey-3': !this.$q.dark.isActive,
      'text-grey': !this.$q.dark.isActive,
      'bg-blue-grey-10': this.$q.dark.isActive
    }"
    active-color="primary"
    indicator-color="primary"
    v-model="tab"
  >
    <q-route-tab
      v-for="(tabItem, i) in tabItems"
      :key="i"
      :to="tabItem.link"
      :name="tabItem.text"
      :label="tabItem.text"
    />
  </q-tabs>
</template>

<script>
export default {
  name: "DirectoryTab",
  props: {
    tabItems: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      tab: this.tabItems[0].text
    };
  }
};
</script>

<style scoped></style>
