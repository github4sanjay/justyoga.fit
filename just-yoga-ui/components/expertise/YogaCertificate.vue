<template>
  <v-chip color="orange" label outlined>
    {{ yogaCertificate.name }}
  </v-chip>
</template>

<script>
export default {
  name: "YogaCertificate",
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data: function (){
    return {
      yogaCertificate: this.$store.getters["expertise/yogaCertificateById"](this.id)
    }
  }
};
</script>

<style scoped></style>
