<template>
  <v-card class="mt-3">
    <v-card-actions>
      <h4>Reviews ({{ reviews.totalElements }})</h4>
      <v-spacer></v-spacer>
      <v-btn
        router
        :to="`/profile/${userId}/reviews/${previousPage}`"
        icon
        :disabled="!reviews.hasPrevious"
      >
        <v-icon color="primary">fa fa-arrow-alt-circle-left</v-icon>
      </v-btn>
      <v-divider vertical />
      <v-btn
        router
        :to="`/profile/${userId}/reviews/${nextPage}`"
        icon
        :disabled="!reviews.hasNext"
      >
        <v-icon color="primary">fa fa-arrow-alt-circle-right</v-icon>
      </v-btn>
    </v-card-actions>
    <v-divider />
    <v-row class="pa-3 pb-0">
      <v-col cols="12" class="pa-0 mt-3" v-for="(review, i) in reviews.data" :key="i">
        <review-card :review-id="review" />
        <v-divider/>
      </v-col>
    </v-row>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn
        router
        :to="`/profile/${userId}/reviews/${previousPage}`"
        icon
        :disabled="!reviews.hasPrevious"
      >
        <v-icon color="primary">fa fa-arrow-alt-circle-left</v-icon>
      </v-btn>
      <v-divider vertical />
      <v-btn
        router
        :to="`/profile/${userId}/reviews/${nextPage}`"
        icon
        :disabled="!reviews.hasNext"
      >
        <v-icon color="primary">fa fa-arrow-alt-circle-right</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import ReviewCard from "@/components/base/ReviewCard";
export default {
  name: "ReviewPageIndex",
  middleware: "profile_review",
  components: { ReviewCard },
  data: function () {
    return {
      page: this.$route.params.page,
      reviews: this.$store.getters["profile_review/byPageAndUserId"](
        this.$route.params.id,
        this.$route.params.page
      ),
      userId: this.$route.params.id,
      nextPage: +this.$route.params.page + 1,
      previousPage: +this.$route.params.page - 1,
    };
  },
};
</script>

<style scoped></style>
