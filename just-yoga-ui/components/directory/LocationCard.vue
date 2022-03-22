<template>
  <v-card outlined hover>
    <v-card-text class="py-0">
    <span>{{ model.name }}</span>
    <v-btn
      icon
      class="mr-0"
      router
      :to="to"
    >
      <v-icon dark>chevron_right</v-icon>
    </v-btn>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "LocationCard",
  props: {
    model: {
      type: Object,
      required: true,
    },
    to: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped></style>
