<template>
  <v-card class="mt-3">
    <v-card-actions>
      <h4>Yoga Instructors ({{ sub.data.totalElements }})</h4>
      <v-spacer></v-spacer>
      <v-btn router :to="prevTo" icon :disabled="!sub.hasPrevious">
        <v-icon color="primary">fa fa-arrow-alt-circle-left</v-icon>
      </v-btn>
      <v-divider vertical />
      <v-btn router :to="nextTo" icon :disabled="!sub.hasNext">
        <v-icon color="primary">fa fa-arrow-alt-circle-right</v-icon>
      </v-btn>
    </v-card-actions>
    <v-divider />
    <v-row class="pa-3">
      <v-col
        cols="12"
        sm="6"
        md="4"
        class=""
        v-for="(data, i) in sub.data.content"
        :key="i"
      >
        <trainer-card :user-info="data" />
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import TrainerCard from "@/components/base/TrainerCard";

export default {
  name: "index",
  components: { TrainerCard },
  middleware: "directory_sub1",
  data: function () {
    return {
      sub: this.$store.getters["directory/subLocalityLevel1ByPage"](
        this.$route.params.sub,
        this.$route.params.page
      ),
      page: this.$route.params.page,
      nextPage: +this.$route.params.page + 1,
      previousPage: +this.$route.params.page - 1,
      subLocality: this.$store.getters["place/subLocalityLevel1"](
        this.$route.params.sub
      ),
      locality: this.$store.getters["place/locality"](
        this.$route.params.locality
      ),
      state: this.$store.getters["place/administrativeAreaLevel1"](
        this.$route.params.state
      ),
      country: this.$store.getters["place/country"](this.$route.params.country),
    };
  },
  computed: {
    prevTo() {
      return `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/trainer/${this.previousPage}`;
    },
    nextTo() {
      return `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/trainer/${this.nextPage}`;
    },
  },
};
</script>

<style scoped></style>
