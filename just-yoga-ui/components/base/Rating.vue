<template>
  <v-row class="ma-2">
    <v-col cols="12" class="py-0">
      <v-card flat>
        <v-card-text
          :class="{
            'pl-0': true,
            'py-0': true,
            'justify-center': $vuetify.breakpoint.smAndDown,
            headline: $vuetify.breakpoint.mdAndUp,
            title: $vuetify.breakpoint.sm,
            'subtitle-2': $vuetify.breakpoint.xsOnly,
          }"
        >
          Rate Here
          <span class="ml-4">{{ `(${rating}/10)` }}</span>
        </v-card-text>
      </v-card>
    </v-col>
    <v-col class="pa-0">
      <v-rating
        v-model="rating"
        :length="ratingLength"
        :empty-icon="emptyIcon"
        :full-icon="fullIcon"
        :half-icon="halfIcon"
        :half-increments="halfIncrements"
        :hover="hover"
        :small="$vuetify.breakpoint.smAndDown"
        :x-large="$vuetify.breakpoint.mdAndUp"
        :dense="dense"
        :color="color"
        :background-color="bgColor"
      />
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "Rating",
  props: {
    defaultValue: {
      type: Number,
      required: false,
    },
    clear: {
      type: Boolean,
      required: false,
    }
  },
  data() {
    return {
      rating: 0,
      ratingLength: 10,
      emptyIcon: "mdi-heart-outline",
      fullIcon: "mdi-heart",
      halfIcon: "mdi-heart-half-full",
      halfIncrements: true,
      hover: true,
      color: "red lighten-3",
      bgColor: "grey lighten-1",
      dense: false,
      size: "64",
    };
  },
  created() {
    if (this.defaultValue) {
      this.rating = this.defaultValue;
    }
  },
  watch: {
    rating(val) {
      this.$emit("rating", val);
    },
    clear(val) {
      if (val) {
        this.rating = 0;
      }
    }
  },
};
</script>

<style scoped></style>
