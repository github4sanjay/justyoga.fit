export function store(state, payload) {
  let present = state.images.find(image => {
    return image.id === payload.id;
  });
  if (present === null || present === undefined) {
    state.images.push(payload);
  }
}

export function storeAll(state, payload) {
  payload.forEach(imageToSave => {
    let present = state.images.find(image => {
      return image.id === imageToSave.id;
    });
    if (present === null || present === undefined) {
      state.images.push(imageToSave);
    }
  });
}

export function update(state, payload) {
  for (let i = 0; i < state.images.length; i++) {
    if (state.images[i].id === payload.id) {
      state.images[i].createdAt = payload.createdAt;
      state.images[i].createdAt = payload.createdAt;
      state.images[i].description = payload.description;
      state.images[i].id = payload.id;
      state.images[i].modifiedBy = payload.modifiedBy;
      state.images[i].title = payload.title;
      state.images[i].trainerId = payload.trainerId;
      state.images[i].updatedAt = payload.updatedAt;
      state.images[i].url = payload.url;
      break;
    }
  }
}

export function remove(state, payload) {
  for (let i = 0; i < state.images.length; i++) {
    if (state.images[i].id === payload) {
      state.images.splice(i, 1);
      break;
    }
  }
}

export function storeProfileImages(state, payload) {
  let present = state.profile_images.find(data => {
    return data.page === payload.page && data.userId === payload.userId;
  });
  if (present === null || present === undefined) {
    state.profile_images.push(payload);
  }
}
