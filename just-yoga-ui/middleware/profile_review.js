export default async function ({ store, params, error }) {
  let page = params.page ? params.page : 0;
  let data = store.getters["profile_review/byPageAndUserId"](
    params.id,
    page
  );
  if (!data) {
    try {
      let reviews = await store.dispatch("profile_review/get", {
        page: page,
        userId: params.id,
      });
      let promises = [];
      reviews.forEach( review => {
        promises.push(store.dispatch("reviews/getImages", { reviewId: review.id }));
        promises.push(store.dispatch("reviews/getVideos", { reviewId: review.id }));
        promises.push(store.dispatch("users/storeUser", { id: review.reviewedBy }));
      });
      await Promise.all(promises);
    } catch (e) {
      error({ statusCode: 500 });
    }
  }
}
