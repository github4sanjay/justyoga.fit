<template>
  <v-snackbar
    color="error"
    multi-line
    vertical
    top
    right
    :timeout="0"
    :value="errorSnackbar"
  >
    <div>
      {{ errorText }}
    </div>
    <v-btn dark text @click.native="closeErrorSnackbar">Close</v-btn>
  </v-snackbar>
</template>
<script>
export default {
  name: "ErrorSnackbar",
  methods: {
    closeErrorSnackbar() {
      this.$store.dispatch("shared/setErrorSnackbar", false);
    },
  },
  computed: {
    errorText() {
      return this.$store.getters["shared/errorText"];
    },
    errorSnackbar() {
      return this.$store.getters["shared/errorSnackbar"];
    },
  },
};
</script>
