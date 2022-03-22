<template>
  <q-page>
    <div class="row" v-if="reviews.totalElements > 0">
      <div class="col-12">
        <q-card flat square bordered>
          <q-card-actions>
            <div
              :class="{
                'text-bold': true
              }"
            >
              Reviews
              <q-badge
                color="accent"
                text-color="white"
                :label="reviews.totalElements"
              />
            </div>
            <q-space />
            <q-btn
              :disable="!reviews.hasPrevious"
              flat
              round
              color="primary"
              icon="fas fa-arrow-circle-left"
              :to="`${+reviews.page - 1}`"
            />
            <q-separator vertical />
            <q-btn
              :disable="!reviews.hasNext"
              flat
              round
              color="primary"
              icon="fas fa-arrow-circle-right"
              :to="`${+reviews.page + 1}`"
            />
          </q-card-actions>
          <q-card-section class="q-pa-none"> </q-card-section>
        </q-card>
      </div>
      <div class="col-12 q-mt-md" v-for="(review, i) in reviews.data" :key="i">
        <review-card :review-id="review" />
      </div>
    </div>
    <div v-else>
      <div class="text-center q-mt-lg">
        <p>
          <img
            src="~assets/found-icon-20.jpg"
            style="width:30vw;max-width:150px;"
          />
        </p>
        <p class="text-weight-light">
          No one has reviewed yet
        </p>
      </div>
    </div>
  </q-page>
</template>

<script>
import { Loading } from "quasar";
import ReviewCard from "components/user/ReviewCard";

export default {
  name: "ReviewPageIndex",
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let page = currentRoute.params.page ? currentRoute.params.page : 0;
    let data = store.getters["profile_review/byPageAndUserId"](
      currentRoute.params.id,
      page
    );
    if (!data) {
      try {
        let reviews = await store.dispatch("profile_review/get", {
          page: page,
          userId: currentRoute.params.id
        });
        let promises = [];
        reviews.forEach(review => {
          promises.push(
            store.dispatch("reviews/getImages", { reviewId: review.id })
          );
          promises.push(
            store.dispatch("reviews/getVideos", { reviewId: review.id })
          );
          promises.push(
            store.dispatch("users/storeUser", { id: review.reviewedBy })
          );
        });
        await Promise.all(promises);
      } catch (e) {
        redirect("/error404");
      }
    }
    Loading.hide();
  },
  components: { ReviewCard },
  data: function() {
    return {};
  },
  computed: {
    reviews() {
      return this.$store.getters["profile_review/byPageAndUserId"](
        this.$route.params.id,
        this.$route.params.page
      );
    }
  }
};
</script>

<style scoped></style>
