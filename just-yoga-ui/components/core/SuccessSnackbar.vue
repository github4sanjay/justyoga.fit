<template>
  <v-snackbar
    color="success"
    multi-line
    vertical
    top
    right
    :timeout="0"
    :value="successSnackbar"
  >
    <div class="subtitle-2">
      {{successText}}
    </div>
    <v-btn pa-3 dark text @click.native="closeSuccessSnackbar()">Close</v-btn>
  </v-snackbar>
</template>

<script>
export default {
  name: "SuccessSnackbar",
  methods: {
    closeSuccessSnackbar() {
      this.$store.dispatch("shared/setSuccessSnackbar", false);
    },
  },
  computed: {
    successText() {
      return this.$store.getters["shared/successText"];
    },
    successSnackbar() {
      return this.$store.getters["shared/successSnackbar"];
    },
  },
};
</script>

<style scoped></style>
