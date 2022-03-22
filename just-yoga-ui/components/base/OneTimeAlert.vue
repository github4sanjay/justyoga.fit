<template>
  <div>
    <v-alert
      dismissible
      color="success"
      border="left"
      elevation="2"
      colored-border
      icon="fa fa-info-circle"
    >
      {{text}}
    </v-alert>
  </div>
</template>

<script>
export default {
  name: "OneTimeAlert",
  props: {
    text: {
      type: String,
      required: false
    }
  }
};
</script>

<style scoped></style>
