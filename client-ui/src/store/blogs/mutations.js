export function store(state, payload) {
  let present = state.blogs.find(data => {
    return data.id === payload.id;
  });
  if (present === null || present === undefined) {
    state.blogs.push(payload);
  }
}

export function updateBlog(state, payload) {
  for (let i = 0; i < state.blogs.length; i++) {
    if (state.blogs[i].id === payload.id) {
      state.blogs[i].createdAt = payload.createdAt;
      state.blogs[i].updatedAt = payload.updatedAt;
      state.blogs[i].createdBy = payload.createdBy;
      state.blogs[i].modifiedBy = payload.modifiedBy;
      state.blogs[i].blogText = payload.blogText;
      state.blogs[i].blogContent = payload.blogContent;
      state.blogs[i].userId = payload.userId;
      state.blogs[i].published = payload.published;
      state.blogs[i].blogTitle = payload.blogTitle;
      state.blogs[i].coverUrl = payload.coverUrl;
      state.blogs[i].id = payload.id;
    }
  }
}

export function storeAll(state, payload) {
  payload.forEach(toSave => {
    let present = state.blogs.find(blog => {
      return blog.id === toSave.id;
    });

    if (present === null || present === undefined) {
      state.blogs.push(toSave);
    }
  });
}

export function storeData(state, payload) {
  for (let i = 0; i < state.blogs.length; i++) {
    if (state.blogs[i].id === payload.id) {
      if (
        state.blogs[i][payload.field] === null ||
        state.blogs[i][payload.field] === undefined
      ) {
        state.blogs[i][payload.field] = [...payload[payload.field]];
      } else {
        payload[payload.field].forEach(toSave => {
          let present = state.blogs[i][payload.field].find(data => {
            return data.id === toSave.id;
          });
          if (present === null || present === undefined) {
            state.blogs[i][payload.field].push(...payload[payload.field]);
          }
        });
      }
      break;
    }
  }
}

export function remove(state, payload) {
  for (let j = 0; j < state.blogs.length; j++) {
    if (state.blogs[j].id === payload.id) {
      state.blogs.splice(j, 1);
      break;
    }
  }
}

export function deleteData(state, payload) {
  let data = payload.data;
  let field = payload.field;
  for (let i = 0; i < state.blogs.length; i++) {
    if (state.blogs[i].id === data.blogId) {
      if (
        state.blogs[i][field] !== null ||
        state.blogs[i][field] !== undefined
      ) {
        for (let j = 0; j < state.blogs[i][field].length; j++) {
          if (state.blogs[i][field][j].id === data.id) {
            state.blogs[i][payload.field].splice(j, 1);
            break;
          }
        }
      }
      break;
    }
  }
}
