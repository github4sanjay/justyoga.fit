<template>
  <!-- <v-breadcrumbs :items="items">
    <template v-slot:divider>
      <v-icon>mdi-chevron-right</v-icon>
    </template>
    <template v-slot:item="{ item }">
      <v-breadcrumbs-item
        :to="item.href"
        :disabled="item.disabled"
        :exact="true"
      >
        {{ item.text }}
      </v-breadcrumbs-item>
    </template>
  </v-breadcrumbs> -->
  <q-breadcrumbs>
    <q-breadcrumbs-el
      v-for="(item, i) in items"
      :key="i"
      :label="item.text"
      :to="item.href"
    />
  </q-breadcrumbs>
</template>

<script>
export default {
  name: "DirectoryBreadcrumbs",
  props: {
    items: {
      type: Array,
      required: true
    }
  }
};
</script>

<style scoped></style>
