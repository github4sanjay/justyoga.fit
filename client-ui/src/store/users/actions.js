import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import axios from "axios";
import NotifyUtil from "src/utils/NotifyUtil";

async function updateUserUtil(configData, dispatch, commit, resolve, reject) {
  try {
    let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
    if (response && response.status === 200) {
      commit("updateUser", response.data.data);
      dispatch("login/setUser", response.data, { root: true });
      resolve(response.data.data);
    } else {
      reject();
    }
  } catch (e) {
    NotifyUtil.showError("Something went wrong");
    reject();
  }
}

export function storeAll({ commit, getters, dispatch, error }, payload) {
  commit("storeAll", payload);
}

export function storeUser({ commit, getters, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const userConfigData = {
      url: ApiEndpoints.USERS + `/${payload.id}`,
      method: "get",
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(userConfigData, dispatch);
      if (response.status === 200) {
        commit("setLoadedUser", response.data.data);
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function clearUsers({ commit }) {
  commit("clearUsers");
}

export function updateCoverPic({ commit }, payload) {
  commit("updateCoverPic", payload);
}

export function updateProfilePic({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url:
        ApiEndpoints.USERS + `/${payload.userId}` + ApiEndpoints.USER_PROFILE,
      data: { url: payload.url },
      method: "post",
      withCredentials: true
    };
    await updateUserUtil(configData, dispatch, commit, resolve, reject);
  });
}

export function updateUser({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const userConfigData = {
      url: ApiEndpoints.USERS,
      method: "put",
      data: payload,
      withCredentials: true
    };
    await updateUserUtil(userConfigData, dispatch, commit, resolve, reject);
  });
}

export function updateVideo({ commit }, payload) {
  commit("updateVideo", payload);
}

export function deleteData({ commit }, payload) {
  commit("deleteData", payload);
}

export function updateImage({ commit }, payload) {
  commit("updateImage", payload);
}

export function storeData({ commit }, payload) {
  commit("storeData", payload);
}

export function updateBasicInfo({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const userConfigData = {
      url: ApiEndpoints.BASIC_INFO,
      method: "put",
      data: payload,
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(userConfigData, dispatch);
      if (response && response.status === 200) {
        commit("updateBasicInfo", response.data.data);
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function updateInterest({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const userConfigData = {
      url: ApiEndpoints.INTERESTS,
      method: "put",
      data: payload,
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(userConfigData, dispatch);
      if (response && response.status === 200) {
        commit("updateInterest", response.data.data);
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getMedicalExpertise({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let presentTrainer = state.users.find(user => {
      return user.id === payload.id;
    });
    if (!presentTrainer) resolve();

    const configData1 = {
      url: ApiEndpoints.USER_MEDICAL_EXPERTISE,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };

    try {
      let response = await AxiosUtil.getAxiosRequest(configData1, dispatch);
      if (response && response.status === 200 && response.data) {
        commit("storeData", {
          id: payload.id,
          medicalExpertise: response.data.data,
          field: "medicalExpertise"
        });
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getYogaExpertise({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let presentTrainer = state.users.find(user => {
      return user.id === payload.id;
    });
    if (!presentTrainer) resolve();

    const configData = {
      url: ApiEndpoints.USER_YOGA_EXPERTISE,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };

    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        commit("storeData", {
          id: payload.id,
          yogaExpertise: response.data.data,
          field: "yogaExpertise"
        });
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getYogaCertificates({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let presentTrainer = state.users.find(user => {
      return user.id === payload.id;
    });
    if (!presentTrainer) resolve();

    const configData = {
      url: ApiEndpoints.USER_YOGA_CERTIFICATE,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };

    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        commit("storeData", {
          id: payload.id,
          yogaCertificates: response.data.data,
          field: "yogaCertificates"
        });
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getVideos({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let presentTrainer = state.users.find(user => {
      return user.id === payload.id;
    });
    if (!presentTrainer) resolve();

    const configData = {
      url: ApiEndpoints.VIDEOS,
      params: {
        userId: payload.id,
        page: payload.page,
        count: payload.count
      },
      method: "get",
      withCredentials: true
    };

    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        commit("storeData", {
          id: payload.id,
          videos: response.data.data.content,
          field: "videos"
        });
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getImages({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let presentTrainer = state.users.find(user => {
      return user.id === payload.id;
    });
    if (!presentTrainer) resolve();

    const configData = {
      url: ApiEndpoints.IMAGES,
      params: {
        userId: payload.id,
        page: payload.page,
        count: payload.count
      },
      method: "get",
      withCredentials: true
    };

    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        commit("storeData", {
          id: payload.id,
          images: response.data.data.content,
          field: "images"
        });
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getBasicInfo({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let presentTrainer = state.users.find(user => {
      return user.id === payload.id;
    });
    if (!presentTrainer) resolve();

    const configData = {
      url: ApiEndpoints.BASIC_INFO,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };

    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        if (response.data.data) {
          commit("updateBasicInfo", response.data.data);
        }
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getInterests({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let presentTrainer = state.users.find(user => {
      return user.id === payload.id;
    });
    if (!presentTrainer) resolve();

    const configData = {
      url: ApiEndpoints.INTERESTS,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };

    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        if (response.data.data) {
          commit("updateInterest", response.data.data);
        }
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getData({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let presentTrainer = state.users.find(user => {
      return user.id === payload.id;
    });
    if (!presentTrainer) resolve();

    let arr = [];

    const configData1 = {
      url: ApiEndpoints.USER_MEDICAL_EXPERTISE,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData1, dispatch));

    const configData2 = {
      url: ApiEndpoints.USER_YOGA_EXPERTISE,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData2, dispatch));

    const configData3 = {
      url: ApiEndpoints.USER_YOGA_CERTIFICATE,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData3, dispatch));

    const configData4 = {
      url: ApiEndpoints.VIDEOS,
      params: {
        userId: payload.id,
        page: 0,
        count: 3
      },
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData4, dispatch));

    const configData5 = {
      url: ApiEndpoints.IMAGES,
      params: {
        userId: payload.id,
        page: 0,
        count: 3
      },
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData5, dispatch));

    const configData6 = {
      url: ApiEndpoints.BASIC_INFO,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData6, dispatch));

    const configData7 = {
      url: ApiEndpoints.INTERESTS,
      params: {
        userId: payload.id
      },
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData7, dispatch));

    try {
      let responseArr = await axios.all(arr);
      if (responseArr) {
        if (responseArr[0]) {
          commit("storeData", {
            id: payload.id,
            medicalExpertise: responseArr[0].data.data,
            field: "medicalExpertise"
          });
        }
        if (responseArr[1]) {
          commit("storeData", {
            id: payload.id,
            yogaExpertise: responseArr[1].data.data,
            field: "yogaExpertise"
          });
        }
        if (responseArr[2]) {
          commit("storeData", {
            id: payload.id,
            yogaCertificates: responseArr[2].data.data,
            field: "yogaCertificates"
          });
        }
        if (responseArr[3]) {
          commit("storeData", {
            id: payload.id,
            videos: responseArr[3].data.data.content,
            field: "videos"
          });
        }
        if (responseArr[4]) {
          commit("storeData", {
            id: payload.id,
            images: responseArr[4].data.data.content,
            field: "images"
          });
        }
        if (responseArr[5] && responseArr[5].data.data) {
          commit("updateBasicInfo", responseArr[5].data.data);
        }
        if (responseArr[6] && responseArr[6].data.data) {
          commit("updateInterest", responseArr[6].data.data);
        }
        resolve();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function storeMedicalExpertise({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let arr = [];
    for (let item of payload) {
      const configData = {
        url: ApiEndpoints.USER_MEDICAL_EXPERTISE,
        method: "post",
        data: {
          userId: payload.userId,
          medicalExpertiseId: item.id
        },
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
    }
    try {
      let responseArr = await axios.all(arr);
      if (responseArr) {
        let medicalExpertise = [];
        responseArr.forEach(response => {
          if (response && response.status === 200) {
            medicalExpertise.push(response.data.data);
          }
        });
        commit("storeData", {
          id: payload.userId,
          medicalExpertise: medicalExpertise,
          field: "medicalExpertise"
        });
        resolve(medicalExpertise);
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function storeYogaExpertise({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let arr = [];
    for (let item of payload) {
      const configData = {
        url: ApiEndpoints.USER_YOGA_EXPERTISE,
        method: "post",
        data: {
          userId: payload.userId,
          yogaExpertiseId: item.id
        },
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
    }
    try {
      let responseArr = await axios.all(arr);
      if (responseArr) {
        let yogaExpertise = [];
        responseArr.forEach(response => {
          if (response && response.status === 200) {
            yogaExpertise.push(response.data.data);
          }
        });
        commit("storeData", {
          id: payload.userId,
          yogaExpertise: yogaExpertise,
          field: "yogaExpertise"
        });
        resolve(yogaExpertise);
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function storeYogaCertificate({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let arr = [];
    for (let item of payload) {
      const configData = {
        url: ApiEndpoints.USER_YOGA_CERTIFICATE,
        method: "post",
        data: {
          userId: payload.userId,
          yogaCertificateId: item.id
        },
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
    }
    try {
      let responseArr = await axios.all(arr);
      if (responseArr) {
        let yogaCertificates = [];
        responseArr.forEach(response => {
          if (response && response.status === 200) {
            yogaCertificates.push(response.data.data);
          }
        });
        commit("storeData", {
          id: payload.userId,
          yogaCertificates: yogaCertificates,
          field: "yogaCertificates"
        });
        resolve(yogaCertificates);
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function deleteMedicalExpertise({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let arr = [];
    let ids = [];
    for (let item of payload) {
      const configData = {
        url: ApiEndpoints.USER_MEDICAL_EXPERTISE + `/${item}`,
        method: "delete",
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
      ids.push({ id: item });
    }
    try {
      let responseArr = await axios.all(arr);
      if (responseArr) {
        let data = [];
        for (let i = 0; i < responseArr.length; i++) {
          let response = responseArr[i];
          if (response && response.status === 200) {
            data.push(ids[i]);
          }
        }
        commit("deleteData", {
          userId: payload.userId,
          data: data,
          field: "medicalExpertise"
        });
        resolve();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function deleteYogaExpertise({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let arr = [];
    let ids = [];
    for (let item of payload) {
      const configData = {
        url: ApiEndpoints.USER_YOGA_EXPERTISE + `/${item}`,
        method: "delete",
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
      ids.push({ id: item });
    }
    try {
      let responseArr = await axios.all(arr);
      if (responseArr) {
        let data = [];
        for (let i = 0; i < responseArr.length; i++) {
          let response = responseArr[i];
          if (response && response.status === 200) {
            data.push(ids[i]);
          }
        }
        commit("deleteData", {
          userId: payload.userId,
          data: data,
          field: "yogaExpertise"
        });
        resolve();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function deleteYogaCertificate({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let arr = [];
    let ids = [];
    for (let item of payload) {
      const configData = {
        url: ApiEndpoints.USER_YOGA_CERTIFICATE + `/${item}`,
        method: "delete",
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
      ids.push({ id: item });
    }
    try {
      let responseArr = await axios.all(arr);
      if (responseArr) {
        let data = [];
        for (let i = 0; i < responseArr.length; i++) {
          let response = responseArr[i];
          if (response && response.status === 200) {
            data.push(ids[i]);
          }
        }
        commit("deleteData", {
          userId: payload.userId,
          data: data,
          field: "yogaCertificates"
        });
        resolve();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}
