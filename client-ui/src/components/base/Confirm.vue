<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Please confirm</q-toolbar-title>
      <q-btn flat round icon="close" v-close-popup @click="close()" />
    </q-toolbar>
    <q-card>
      <q-card-section class="row items-center">
        <q-avatar icon="check_box" color="primary" text-color="white" />
        <span class="q-ml-sm">{{ text }}</span>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn
          flat
          label="Confirm"
          color="primary"
          @click="confirm()"
          :loading="loading"
        />
      </q-card-actions>
    </q-card>
  </div>
</template>

<script>
export default {
  name: "Confirm",
  props: {
    loading: {
      type: Boolean,
      required: true
    },
    text: {
      type: String,
      required: true
    },
    model: {
      type: Object,
      required: true
    }
  },
  methods: {
    confirm() {
      this.$emit("confirm", this.model);
    },
    close() {
      this.$emit("close", {});
    }
  }
};
</script>

<style scoped></style>
