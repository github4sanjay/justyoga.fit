export function store(state, payload) {
  let present = state.profile_blogs.find(data => {
    return data.page === payload.page && data.userId === payload.userId;
  });
  if (present === null || present === undefined) {
    state.profile_blogs.push(payload);
  }
}

export function deleteBlog(state, payload) {
  for (let i = 0; i < state.profile_blogs.length; i++) {
    let data = state.profile_blogs[i].data;
    let done = false;
    if (data && data.length > 0) {
      for (let j = 0; j < data.length; j++) {
        if (data[j] === payload.id) {
          state.profile_blogs[i].data.splice(j, 1);
          done = true;
          break;
        }
      }
    }
    if (done) break;
  }
}
