export function store(state, payload) {
  let present = state.reviews.find(data => {
    return data.id === payload.id;
  });
  if (present === null || present === undefined) {
    state.reviews.push(payload);
  }
}

export function updateReview(state, payload) {
  for (let i = 0; i < state.reviews.length; i++) {
    if (state.reviews[i].id === payload.id) {
      state.reviews[i].createdAt = payload.createdAt;
      state.reviews[i].updatedAt = payload.updatedAt;
      state.reviews[i].createdBy = payload.createdBy;
      state.reviews[i].modifiedBy = payload.modifiedBy;
      state.reviews[i].reviewText = payload.reviewText;
      state.reviews[i].reviewContent = payload.reviewContent;
      state.reviews[i].rating = payload.rating;
      state.reviews[i].reviewedBy = payload.reviewedBy;
      state.reviews[i].userId = payload.userId;
      state.reviews[i].published = payload.published;
      state.reviews[i].id = payload.id;
    }
  }
}

export function storeAll(state, payload) {
  payload.forEach(toSave => {
    let present = state.reviews.find(review => {
      return review.id === toSave.id;
    });

    if (present === null || present === undefined) {
      state.reviews.push(toSave);
    }
  });
}

export function storeData(state, payload) {
  for (let i = 0; i < state.reviews.length; i++) {
    if (state.reviews[i].id === payload.id) {
      if (
        state.reviews[i][payload.field] === null ||
        state.reviews[i][payload.field] === undefined
      ) {
        state.reviews[i][payload.field] = [...payload[payload.field]];
      } else {
        payload[payload.field].forEach(toSave => {
          let present = state.reviews[i][payload.field].find(data => {
            return data.id === toSave.id;
          });
          if (present === null || present === undefined) {
            state.reviews[i][payload.field].push(...payload[payload.field]);
          }
        });
      }
      break;
    }
  }
}

export function remove(state, payload) {
  for (let j = 0; j < state.reviews.length; j++) {
    if (state.reviews[j].id === payload.id) {
      state.reviews.splice(j, 1);
      break;
    }
  }
}

export function deleteData(state, payload) {
  let data = payload.data;
  let field = payload.field;
  for (let i = 0; i < state.reviews.length; i++) {
    if (state.reviews[i].id === data.reviewId) {
      if (
        state.reviews[i][field] !== null ||
        state.reviews[i][field] !== undefined
      ) {
        for (let j = 0; j < state.reviews[i][field].length; j++) {
          if (state.reviews[i][field][j].id === data.id) {
            state.reviews[i][payload.field].splice(j, 1);
            break;
          }
        }
      }
      break;
    }
  }
}
