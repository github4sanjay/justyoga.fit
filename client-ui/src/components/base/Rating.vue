<template>
  <div class="row">
    <div class="col-10">
      <q-rating
        v-model="rating"
        :size="$q.screen.lt.md ? '1.7em' : '2em'"
        :max="ratingLength"
        color="primary"
      />
    </div>
    <div class="col-2">
      <div class="q-mb-sm">
        <span class="ml-4">{{ `${rating}/10` }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Rating",
  props: {
    defaultValue: {
      type: Number,
      required: false
    },
    clear: {
      type: Boolean,
      required: false
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
      size: "64"
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
  }
};
</script>

<style scoped></style>
