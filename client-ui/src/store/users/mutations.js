export function setLoadedUser(state, payload) {
  let presentUser = state.users.find(user => {
    return user.id === payload.id;
  });
  if (presentUser === null || presentUser === undefined) {
    state.users.push(payload);
  }
}
export function storeAll(state, payload) {
  payload.forEach(userToSave => {
    let presentUser = state.users.find(user => {
      return user.id === userToSave.id;
    });

    if (presentUser === null || presentUser === undefined) {
      state.users.push(userToSave);
    }
  });
}

export function updateUser(state, payload) {
  for (let i = 0; i < state.users.length; i++) {
    if (state.users[i].id === payload.id) {
      state.users[i].name = payload.name;
      state.users[i].description = payload.description;
      state.users[i].phoneNumber = payload.phoneNumber;
      state.users[i].firebaseUid = payload.firebaseUid;
      state.users[i].email = payload.email;
      state.users[i].emailVerified = payload.emailVerified;
      state.users[i].profilePic = payload.profilePic;
      state.users[i].coverPic = payload.coverPic;
      state.users[i].providerId = payload.providerId;
      state.users[i].password = payload.password;
      state.users[i].authorities = payload.authorities;
      state.users[i].id = payload.id;
      state.users[i].createdAt = payload.createdAt;
      state.users[i].updatedAt = payload.updatedAt;
      state.users[i].createdBy = payload.createdBy;
      state.users[i].modifiedBy = payload.modifiedBy;
      break;
    }
  }
}

export function clearUsers(state) {
  state.users = [];
}

export function storeData(state, payload) {
  for (let i = 0; i < state.users.length; i++) {
    if (state.users[i].id === payload.id) {
      if (
        state.users[i][payload.field] === null ||
        state.users[i][payload.field] === undefined
      ) {
        state.users[i][payload.field] = [...payload[payload.field]];
      } else {
        state.users[i][payload.field].push(...payload[payload.field]);
      }
      break;
    }
  }
}

export function updateVideo(state, payload) {
  for (let i = 0; i < state.users.length; i++) {
    if (state.users[i].id === payload.userId && state.users[i].videos) {
      let videos = state.users[i].videos;
      for (let j = 0; j < videos.length; j++) {
        if (videos[j].id === payload.id) {
          state.users[i].videos[j].coverPic = payload.coverPic;
          state.users[i].videos[j].createdAt = payload.createdAt;
          state.users[i].videos[j].createdAt = payload.createdAt;
          state.users[i].videos[j].description = payload.description;
          state.users[i].videos[j].duration = payload.duration;
          state.users[i].videos[j].id = payload.id;
          state.users[i].videos[j].modifiedBy = payload.modifiedBy;
          state.users[i].videos[j].title = payload.title;
          state.users[i].videos[j].userId = payload.userId;
          state.users[i].videos[j].updatedAt = payload.updatedAt;
          state.users[i].videos[j].url = payload.url;
          break;
        }
      }
      break;
    }
  }
}

export function deleteData(state, payload) {
  let userId = payload.userId;
  let data = payload.data;
  let field = payload.field;
  for (let i = 0; i < state.users.length; i++) {
    if (state.users[i].id === userId && state.users[i][field]) {
      let existingData = state.users[i][field];
      for (let j = 0; j < existingData.length; j++) {
        for (let k = 0; k < data.length; k++) {
          if (existingData[j].id === data[i].id) {
            state.users[i][field].splice(j, 1);
            break;
          }
        }
      }
      break;
    }
  }
}

export function updateImage(state, payload) {
  for (let i = 0; i < state.users.length; i++) {
    if (state.users[i].id === payload.userId && state.users[i].images) {
      let images = state.users[i].images;
      for (let j = 0; j < images.length; j++) {
        if (images[j].id === payload.id) {
          state.users[i].images[j].createdAt = payload.createdAt;
          state.users[i].images[j].createdAt = payload.createdAt;
          state.users[i].images[j].description = payload.description;
          state.users[i].images[j].id = payload.id;
          state.users[i].images[j].modifiedBy = payload.modifiedBy;
          state.users[i].images[j].title = payload.title;
          state.users[i].images[j].userId = payload.userId;
          state.users[i].images[j].updatedAt = payload.updatedAt;
          state.users[i].images[j].url = payload.url;
          break;
        }
      }
      break;
    }
  }
}

export function updateBasicInfo(state, payload) {
  for (let i = 0; i < state.users.length; i++) {
    if (state.users[i].id === payload.userId) {
      state.users[i].basicInfo = {};
      state.users[i].basicInfo.administrativeAreaLevel1Id =
        payload.administrativeAreaLevel1Id;
      state.users[i].basicInfo.age = payload.age;
      state.users[i].basicInfo.countryId = payload.countryId;
      state.users[i].basicInfo.createdAt = payload.createdAt;
      state.users[i].basicInfo.createdBy = payload.createdBy;
      state.users[i].basicInfo.experienceMonths = payload.experienceMonths;
      state.users[i].basicInfo.experienceYears = payload.experienceYears;
      state.users[i].basicInfo.formattedAddress = payload.formattedAddress;
      state.users[i].basicInfo.geohash1 = payload.geohash1;
      state.users[i].basicInfo.geohash150 = payload.geohash150;
      state.users[i].basicInfo.geohash5 = payload.geohash5;
      state.users[i].basicInfo.geohash50 = payload.geohash50;
      state.users[i].basicInfo.id = payload.id;
      state.users[i].basicInfo.latitude = payload.latitude;
      state.users[i].basicInfo.localityId = payload.localityId;
      state.users[i].basicInfo.longitude = payload.longitude;
      state.users[i].basicInfo.modifiedBy = payload.modifiedBy;
      state.users[i].basicInfo.postalCode = payload.postalCode;
      state.users[i].basicInfo.subLocalityLevel1Id =
        payload.subLocalityLevel1Id;
      state.users[i].basicInfo.subLocalityLevel2Id =
        payload.subLocalityLevel2Id;
      state.users[i].basicInfo.updatedAt = payload.updatedAt;
      state.users[i].basicInfo.userId = payload.userId;
      break;
    }
  }
}

export function updateInterest(state, payload) {
  for (let i = 0; i < state.users.length; i++) {
    if (state.users[i].id === payload.userId) {
      state.users[i].interest = {};
      state.users[i].interest.createdAt = payload.createdAt;
      state.users[i].interest.createdBy = payload.createdBy;
      state.users[i].interest.id = payload.id;
      state.users[i].interest.modifiedBy = payload.modifiedBy;
      state.users[i].interest.userId = payload.userId;
      state.users[i].interest.trainer = payload.trainer;
      state.users[i].interest.lookingForTrainer = payload.lookingForTrainer;
      state.users[i].interest.blogger = payload.blogger;
      break;
    }
  }
}
