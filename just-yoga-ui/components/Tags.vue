<template>
  <v-container pa-0>
    <base-subheading>Tags</base-subheading>
    <v-chip-group>
    <v-chip
      v-for="(category, i) in categories"
      :key="i"
      label
      small
      color="secondary"
      text-color="white"
    >
      {{ category.text }}
    </v-chip>
    </v-chip-group>
  </v-container>
</template>

<script>
// Utilities
import {
  mapGetters
} from 'vuex'

export default {
  computed: {
    ...mapGetters({
      categories: 'store/categories'
    })
  }
}
</script>
