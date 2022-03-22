<template>
  <v-tooltip
    content-class="black pa-2 subtitle-2"
    max-width="400" top>
    <template v-slot:activator="{ on }">
      <v-chip color="orange" label outlined v-on="on">
        {{ medicalExpertise.name }}
      </v-chip>
    </template>
    <span class="pa-0">
        {{ medicalExpertise.description }}
    </span>
  </v-tooltip>
</template>

<script>
export default {
  name: "MedicalExpertise",
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data: function (){
    return {
      medicalExpertise: this.$store.getters["expertise/medicalExpertiseById"](this.id)
    }
  }
};
</script>

<style scoped></style>
