export default async function ({ store, params, error }) {
  try {
    let review = store.getters["reviews/findById"](params.id);
    if (review === null || review === undefined) {
      review = await store.dispatch("reviews/get", { id: params.id });
      let promises = [];
      promises.push(
        store.dispatch("reviews/getImages", { reviewId: review.id })
      );
      promises.push(
        store.dispatch("reviews/getVideos", { reviewId: review.id })
      );
      promises.push(
        store.dispatch("users/storeUser", { id: review.reviewedBy })
      );
      promises.push(store.dispatch("users/storeUser", { id: review.userId }));
      await Promise.all(promises);
    }

    let userIds = new Set(); // only unique users
    if (!review.comments) {
      let comments = await store.dispatch("reviews/getComments", {
        reviewId: review.id,
      });
      if (comments) {
        comments.forEach((comment) => userIds.add(comment.userId));
      }
    }

    if (!review.likes) {
      let comments = await store.dispatch("reviews/getLikes", {
        reviewId: review.id,
      });
      if (comments) {
        comments.forEach((comment) => userIds.add(comment.userId));
      }
    }
    let commentUserPromise = [];
    userIds.forEach((id) =>
      commentUserPromise.push(store.dispatch("users/storeUser", { id: id }))
    );

    await Promise.all(commentUserPromise); // get all user from comment userId
  } catch (e) {
    error({ statusCode: 500 });
  }
}
