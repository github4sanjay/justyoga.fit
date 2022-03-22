<template>
  <v-row>
    <v-col cols="12">
      <v-card flat>
        <v-card-text
          :class="{
            'justify-center': $vuetify.breakpoint.smAndDown,
            headline: $vuetify.breakpoint.mdAndUp,
            title: $vuetify.breakpoint.sm,
            'subtitle-2': $vuetify.breakpoint.xsOnly,
          }"
        >
          {{ headline }}
        </v-card-text>
        <v-slider class="mx-3" v-model="var_slider" thumb-label="always"/>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "BaseSlider",
  props: {
    slider: {
      type: Number,
      required: false,
      default: 0,
    },
    headline: {
      type: String,
      required: false,
      default: "Slide to select number",
    },
  },
  data() {
    return {
      var_slider: this.slider
    };
  },
  watch: {
    var_slider(val) {
      this.$emit("change-slider", val);
    },
  },
};
</script>

<style scoped></style>
