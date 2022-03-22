<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="close()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Please Confirm</v-toolbar-title>
    </v-toolbar>
    <v-card>
      <v-card-title>
        {{ text }}
      </v-card-title>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text :loading="loading" @click="confirm()">
          Confirm
          <v-icon right color="red">check_box</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "Confirm",
  props: {
    loading: {
      type: Boolean,
      required: true,
    },
    text: {
      type: String,
      required: true,
    },
    model: {
      type: Object,
      required: true,
    }
  },
  methods: {
    confirm() {
      this.$emit("confirm", this.model);
    },
    close() {
      this.$emit("close", {});
    },
  },
};
</script>

<style scoped></style>
