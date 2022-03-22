<template>
  <v-avatar :size="size" color="primary">
    <v-img v-if="url" :src="url" :alt="name" />
    <span v-else-if="name" class="white--text headline">
      {{ name.charAt(0) }}
    </span>
    <span v-else class="white--text headline">JY</span>
  </v-avatar>
</template>

<script>
export default {
  name: "BaseAvatar",
  props: {
    name: {
      type: String,
      required: true,
    },
    url: {
      type: String,
      required: true,
    },
    size: {
      type: String,
      required: true,
    }
  },
};
</script>

<style scoped></style>
