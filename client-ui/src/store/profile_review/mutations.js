export function store(state, payload) {
  let present = state.profile_reviews.find(data => {
    return data.page === payload.page && data.userId === payload.userId;
  });
  if (present === null || present === undefined) {
    state.profile_reviews.push(payload);
  }
}

export function deleteReview(state, payload) {
  for (let i = 0; i < state.profile_reviews.length; i++) {
    let data = state.profile_reviews[i].data;
    let done = false;
    if (data && data.length > 0) {
      for (let j = 0; j < data.length; j++) {
        if (data[j] === payload.id) {
          state.profile_reviews[i].data.splice(j, 1);
          done = true;
          break;
        }
      }
    }
    if (done) break;
  }
}
