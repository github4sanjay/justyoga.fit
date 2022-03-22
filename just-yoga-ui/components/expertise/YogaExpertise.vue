<template>
  <v-chip color="orange" label outlined>
    {{ yogaExpertise.name }}
  </v-chip>
</template>

<script>
export default {
  name: "YogaExpertise",
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data: function (){
    return {
      yogaExpertise: this.$store.getters["expertise/yogaExpertiseById"](this.id)
    }
  }
};
</script>

<style scoped></style>
