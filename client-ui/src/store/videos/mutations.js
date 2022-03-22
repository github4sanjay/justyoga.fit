export function store(state, payload) {
  let present = state.videos.find(video => {
    return video.id === payload.id;
  });
  if (present === null || present === undefined) {
    state.videos.push(payload);
  }
}

export function storeAll(state, payload) {
  payload.forEach(videoToSave => {
    let present = state.videos.find(video => {
      return video.id === videoToSave.id;
    });
    if (present === null || present === undefined) {
      state.videos.push(videoToSave);
    }
  });
}

export function update(state, payload) {
  for (let i = 0; i < state.videos.length; i++) {
    if (state.videos[i].id === payload.id) {
      state.videos[i].coverPic = payload.coverPic;
      state.videos[i].createdAt = payload.createdAt;
      state.videos[i].createdAt = payload.createdAt;
      state.videos[i].description = payload.description;
      state.videos[i].duration = payload.duration;
      state.videos[i].id = payload.id;
      state.videos[i].modifiedBy = payload.modifiedBy;
      state.videos[i].title = payload.title;
      state.videos[i].trainerId = payload.trainerId;
      state.videos[i].updatedAt = payload.updatedAt;
      state.videos[i].url = payload.url;
      break;
    }
  }
}

export function remove(state, payload) {
  for (let i = 0; i < state.videos.length; i++) {
    if (state.videos[i].id === payload) {
      state.videos.splice(i, 1);
      break;
    }
  }
}

export function storeProfileVideos(state, payload) {
  let present = state.profile_videos.find(data => {
    return data.page === payload.page && data.userId === payload.userId;
  });
  if (present === null || present === undefined) {
    state.profile_videos.push(payload);
  }
}
