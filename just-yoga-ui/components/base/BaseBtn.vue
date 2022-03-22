<template>
  <v-btn
    :small="$vuetify.breakpoint.smAndDown"
    :color="color"
    :class="var_class"
    :dark="dark"
    @click="click"
    :loading="loading"
  >
    {{ text !== undefined ? text : "" }}
    <v-icon v-if="icon">edit</v-icon>
  </v-btn>
</template>

<script>
export default {
  name: "BaseBtn",
  props: {
    color: {
      type: String,
      required: false,
      default: "primary",
    },
    var_class: {
      type: String,
      required: false,
    },
    text: {
      type: String,
      required: false,
      default: undefined,
    },
    icon: {
      type: String,
      required: false,
      default: null,
    },
    dark: {
      type: Boolean,
      required: false,
      default: false,
    },
    loading: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  methods: {
    click() {
      this.$emit("click", true);
    },
  },
  computed: {},
};
</script>

<style scoped></style>
