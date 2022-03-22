export function someMutation(/* state */) {}

export function store(state, payload) {
  let present = state.collections.find(collection => {
    return collection.id === payload.id;
  });
  if (present === null || present === undefined) {
    state.collections.push(payload);
  }
}

export function storeAll(state, payload) {
  payload.forEach(imageToSave => {
    let present = state.collections.find(image => {
      return image.id === imageToSave.id;
    });
    if (present === null || present === undefined) {
      state.collections.push(imageToSave);
    }
  });
}

export function update(state, payload) {
  for (let i = 0; i < state.collections.length; i++) {
    if (state.collections[i].id === payload.id) {
      state.collections[i].id = payload.id;
      state.collections[i].createdAt = payload.createdAt;
      state.collections[i].createdBy = payload.createdBy;
      state.collections[i].modifiedBy = payload.modifiedBy;
      state.collections[i].updatedAt = payload.updatedAt;
      state.collections[i].description = payload.description;
      state.collections[i].name = payload.name;
      state.collections[i].coverUrl = payload.coverUrl;
      state.collections[i].geohash1 = payload.geohash1;
      state.collections[i].geohash150 = payload.geohash150;
      state.collections[i].geohash5 = payload.geohash5;
      state.collections[i].geohash50 = payload.geohash50;
      state.collections[i].latitude = payload.latitude;
      state.collections[i].localityId = payload.localityId;
      state.collections[i].longitude = payload.longitude;
      state.collections[i].postalCode = payload.postalCode;
      state.collections[i].subLocalityLevel1Id = payload.subLocalityLevel1Id;
      state.collections[i].subLocalityLevel2Id = payload.subLocalityLevel2Id;
      state.collections[i].administrativeAreaLevel1Id =
        payload.administrativeAreaLevel1Id;
      state.collections[i].countryId = payload.countryId;
      break;
    }
  }
}

export function remove(state, payload) {
  for (let i = 0; i < state.collections.length; i++) {
    if (state.collections[i].id === payload.id) {
      state.collections.splice(i, 1);
      break;
    }
  }
}

export function deleteFromPage(state, payload) {
  for (let i = 0; i < state.collection_page.length; i++) {
    let data = state.collection_page[i].data;
    let done = false;
    if (data && data.length > 0) {
      for (let j = 0; j < data.length; j++) {
        if (data[j] === payload.id) {
          state.collection_page[i].data.splice(j, 1);
          done = true;
          break;
        }
      }
    }
    if (done) break;
  }
}

export function storeCollectionPage(state, payload) {
  let present = state.collection_page.find(data => {
    return data.page === payload.page;
  });
  if (present === null || present === undefined) {
    state.collection_page.push(payload);
  }
}

export function storeCollectionImages(state, payload) {
  for (let i = 0; i < state.collections.length; i++) {
    if (state.collections[i].id === payload.id) {
      state.collections[i].images = payload.images;
      break;
    }
  }
}

export function storeCollectionVideos(state, payload) {
  for (let i = 0; i < state.collections.length; i++) {
    if (state.collections[i].id === payload.id) {
      state.collections[i].videos = payload.videos;
      break;
    }
  }
}

export function storeCollectionBlogs(state, payload) {
  for (let i = 0; i < state.collections.length; i++) {
    if (state.collections[i].id === payload.id) {
      state.collections[i].blogs = payload.blogs;
      break;
    }
  }
}
