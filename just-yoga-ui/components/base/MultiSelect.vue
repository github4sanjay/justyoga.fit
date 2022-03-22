<template>
  <v-row>
    <v-col>
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
        <v-select
          return-object
          class="mx-3"
          v-model="var_selections"
          item-text="name"
          label="Select options"
          tags
          outlined
          :small-chips="$vuetify.breakpoint.smAndDown"
          :chips="$vuetify.breakpoint.mdAndUp"
          :dense="$vuetify.breakpoint.smAndDown"
          multiple
          :items="options"
        >
        </v-select>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "MultiSelect",
  props: {
    options: {
      type: Array,
      required: false,
      default: () => [],
    },
    headline: {
      type: String,
      required: false,
      default: "Choose options below",
    },
    selections: {
      type: Array,
      required: false,
      default: () => [],
    },
    reload: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      var_selections: this.selections,
    };
  },
  computed: {},
  watch: {
    var_selections(val) {
      this.$emit("change-selections", val);
    },
    reload(val) {
      if (val) {
        this.var_selections = this.selections;
      }
    },
  },
};
</script>

<style scoped></style>
