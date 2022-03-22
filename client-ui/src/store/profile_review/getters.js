export function byPageAndUserId(state) {
  return (userId, page) =>
    state.profile_reviews.find(
      profile_review =>
        profile_review.page == page && profile_review.userId == userId
    );
}
