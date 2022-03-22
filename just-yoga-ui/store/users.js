import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";
import axios from "axios";

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
    dispatch("shared/setErrorText", "Something went wrong", {
      root: true,
    });
    dispatch("shared/setErrorSnackbar", true, { root: true });
    reject();
  }
}

export default {
  state: () => ({
    // [{id, basicInfo, interest, images, videos, medicalExpertise, yogaExpertise, yogaCertificates}]
    users: [],
  }),
  mutations: {
    setLoadedUser(state, payload) {
      let presentUser = state.users.find((user) => {
        return user.id === payload.id;
      });
      if (presentUser === null || presentUser === undefined) {
        state.users.push(payload);
      }
    },
    storeAll(state, payload) {
      payload.forEach((userToSave) => {
        let presentUser = state.users.find((user) => {
          return user.id === userToSave.id;
        });

        if (presentUser === null || presentUser === undefined) {
          state.users.push(userToSave);
        }
      });
    },

    updateUser(state, payload) {
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
    },

    clearUsers(state) {
      state.users = [];
    },

    storeData(state, payload) {
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
    },

    updateVideo(state, payload) {
      for (let i = 0; i < state.users.length; i++) {
        if (state.users[i].id === payload.trainerId && state.users[i].videos) {
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
    },

    deleteData(state, payload) {
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
    },

    updateImage(state, payload) {
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
    },

    updateBasicInfo(state, payload) {
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
    },

    updateInterest(state, payload) {
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
    },
  },
  actions: {
    storeAll({ commit, getters, dispatch, error }, payload) {
      commit("storeAll", payload);
    },

    async storeUser({ commit, getters, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const userConfigData = {
          url: ApiEndpoints.USERS + `/${payload.id}`,
          method: "get",
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(
            userConfigData,
            dispatch
          );
          if (response.status === 200) {
            commit("setLoadedUser", response.data.data);
            resolve(response.data.data);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    clearUsers({ commit }) {
      commit("clearUsers");
    },

    updateCoverPic({ commit }, payload) {
      commit("updateCoverPic", payload);
    },

    updateProfilePic({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let formData = new FormData();
        formData.append("profilePic", payload.image.blob);
        const configData = {
          url:
            ApiEndpoints.USERS +
            `/${payload.userId}` +
            ApiEndpoints.USER_PROFILE,
          data: formData,
          method: "post",
          withCredentials: true,
        };
        await updateUserUtil(configData, dispatch, commit, resolve, reject);
      });
    },

    updateUser({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const userConfigData = {
          url: ApiEndpoints.USERS,
          method: "put",
          data: payload,
          withCredentials: true,
        };
        await updateUserUtil(userConfigData, dispatch, commit, resolve, reject);
      });
    },

    updateVideo({ commit }, payload) {
      commit("updateVideo", payload);
    },

    deleteData({ commit }, payload) {
      commit("deleteData", payload);
    },

    updateImage({ commit }, payload) {
      commit("updateImage", payload);
    },

    storeData({ commit }, payload) {
      commit("storeData", payload);
    },

    updateBasicInfo({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const userConfigData = {
          url: ApiEndpoints.BASIC_INFO,
          method: "put",
          data: payload,
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(
            userConfigData,
            dispatch
          );
          if (response && response.status === 200) {
            commit("updateBasicInfo", response.data.data);
            resolve(response.data.data);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    updateInterest({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const userConfigData = {
          url: ApiEndpoints.INTERESTS,
          method: "put",
          data: payload,
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(
            userConfigData,
            dispatch
          );
          if (response && response.status === 200) {
            commit("updateInterest", response.data.data);
            resolve(response.data.data);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    getMedicalExpertise({ commit, state, dispatch }, payload){
      return new Promise(async (resolve, reject) => {
        let presentTrainer = state.users.find((user) => {
          return user.id === payload.id;
        });
        if (!presentTrainer) resolve();

        const configData1 = {
          url: ApiEndpoints.USER_MEDICAL_EXPERTISE,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };

        try {
          let response = await AxiosUtil.getAxiosRequest(configData1, dispatch);
          if (
            response &&
            response.status === 200 &&
            response.data
          ) {
            commit("storeData", {
              id: payload.id,
              medicalExpertise: response.data.data,
              field: "medicalExpertise",
            });
            resolve(response.data.data);
          } else {
            reject();
          }
        }catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    getYogaExpertise({ commit, state, dispatch }, payload){
      return new Promise(async (resolve, reject) => {
        let presentTrainer = state.users.find((user) => {
          return user.id === payload.id;
        });
        if (!presentTrainer) resolve();

        const configData = {
          url: ApiEndpoints.USER_YOGA_EXPERTISE,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };

        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (
            response &&
            response.status === 200 &&
            response.data
          ) {
            commit("storeData", {
              id: payload.id,
              yogaExpertise: response.data.data,
              field: "yogaExpertise",
            });
            resolve(response.data.data);
          } else {
            reject();
          }
        }catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    getYogaCertificates({ commit, state, dispatch }, payload){
      return new Promise(async (resolve, reject) => {
        let presentTrainer = state.users.find((user) => {
          return user.id === payload.id;
        });
        if (!presentTrainer) resolve();

        const configData = {
          url: ApiEndpoints.USER_YOGA_CERTIFICATE,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };

        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (
            response &&
            response.status === 200 &&
            response.data
          ) {
            commit("storeData", {
              id: payload.id,
              yogaCertificates: response.data.data,
              field: "yogaCertificates",
            });
            resolve(response.data.data);
          } else {
            reject();
          }
        }catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    getVideos({ commit, state, dispatch }, payload){
      return new Promise(async (resolve, reject) => {
        let presentTrainer = state.users.find((user) => {
          return user.id === payload.id;
        });
        if (!presentTrainer) resolve();

        const configData = {
          url: ApiEndpoints.VIDEOS,
          params: {
            userId: payload.id,
            page: payload.page,
            count: payload.count,
          },
          method: "get",
          withCredentials: true,
        };

        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (
            response &&
            response.status === 200 &&
            response.data
          ) {
            commit("storeData", {
              id: payload.id,
              videos: response.data.data.content,
              field: "videos",
            });
            resolve(response.data.data);
          } else {
            reject();
          }
        }catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    getImages({ commit, state, dispatch }, payload){
      return new Promise(async (resolve, reject) => {
        let presentTrainer = state.users.find((user) => {
          return user.id === payload.id;
        });
        if (!presentTrainer) resolve();

        const configData = {
          url: ApiEndpoints.IMAGES,
          params: {
            userId: payload.id,
            page: payload.page,
            count: payload.count,
          },
          method: "get",
          withCredentials: true,
        };

        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (
            response &&
            response.status === 200 &&
            response.data
          ) {
            commit("storeData", {
              id: payload.id,
              images: response.data.data.content,
              field: "images",
            });
            resolve(response.data.data);
          } else {
            reject();
          }
        }catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    getBasicInfo({ commit, state, dispatch }, payload){
      return new Promise(async (resolve, reject) => {
        let presentTrainer = state.users.find((user) => {
          return user.id === payload.id;
        });
        if (!presentTrainer) resolve();


        const configData = {
          url: ApiEndpoints.BASIC_INFO,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };

        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (
            response &&
            response.status === 200 &&
            response.data
          ) {
            if (response.data.data){
              commit("updateBasicInfo", response.data.data);
            }
            resolve(response.data.data);
          } else {
            reject();
          }
        }catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    getInterests({ commit, state, dispatch }, payload){
      return new Promise(async (resolve, reject) => {
        let presentTrainer = state.users.find((user) => {
          return user.id === payload.id;
        });
        if (!presentTrainer) resolve();


        const configData = {
          url: ApiEndpoints.INTERESTS,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };

        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (
            response &&
            response.status === 200 &&
            response.data
          ) {
            if (response.data.data){
              commit("updateInterest", response.data.data);
            }
            resolve(response.data.data);
          } else {
            reject();
          }
        }catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    async getData({ commit, state, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let presentTrainer = state.users.find((user) => {
          return user.id === payload.id;
        });
        if (!presentTrainer) resolve();

        let arr = [];

        const configData1 = {
          url: ApiEndpoints.USER_MEDICAL_EXPERTISE,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData1, dispatch));

        const configData2 = {
          url: ApiEndpoints.USER_YOGA_EXPERTISE,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData2, dispatch));

        const configData3 = {
          url: ApiEndpoints.USER_YOGA_CERTIFICATE,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData3, dispatch));

        const configData4 = {
          url: ApiEndpoints.VIDEOS,
          params: {
            userId: payload.id,
            page: 0,
            count: 3,
          },
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData4, dispatch));

        const configData5 = {
          url: ApiEndpoints.IMAGES,
          params: {
            userId: payload.id,
            page: 0,
            count: 3,
          },
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData5, dispatch));

        const configData6 = {
          url: ApiEndpoints.BASIC_INFO,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData6, dispatch));

        const configData7 = {
          url: ApiEndpoints.INTERESTS,
          params: {
            userId: payload.id,
          },
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData7, dispatch));

        try {
          let responseArr = await axios.all(arr);
          if (responseArr) {
            if (responseArr[0]) {
              commit("storeData", {
                id: payload.id,
                medicalExpertise: responseArr[0].data.data,
                field: "medicalExpertise",
              });
            }
            if (responseArr[1]) {
              commit("storeData", {
                id: payload.id,
                yogaExpertise: responseArr[1].data.data,
                field: "yogaExpertise",
              });
            }
            if (responseArr[2]) {
              commit("storeData", {
                id: payload.id,
                yogaCertificates: responseArr[2].data.data,
                field: "yogaCertificates",
              });
            }
            if (responseArr[3]) {
              commit("storeData", {
                id: payload.id,
                videos: responseArr[3].data.data.content,
                field: "videos",
              });
            }
            if (responseArr[4]) {
              commit("storeData", {
                id: payload.id,
                images: responseArr[4].data.data.content,
                field: "images",
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
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    storeMedicalExpertise({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let arr = [];
        for (let item of payload) {
          const configData = {
            url: ApiEndpoints.USER_MEDICAL_EXPERTISE,
            method: "post",
            data: {
              userId: payload.userId,
              medicalExpertiseId: item.id,
            },
            withCredentials: true,
          };
          arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        }
        try {
          let responseArr = await axios.all(arr);
          if (responseArr) {
            let medicalExpertise = [];
            responseArr.forEach((response) => {
              if (response && response.status === 200) {
                medicalExpertise.push(response.data.data);
              }
            });
            commit("storeData", {
              id: payload.userId,
              medicalExpertise: medicalExpertise,
              field: "medicalExpertise",
            });
            resolve(medicalExpertise);
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    storeYogaExpertise({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let arr = [];
        for (let item of payload) {
          const configData = {
            url: ApiEndpoints.USER_YOGA_EXPERTISE,
            method: "post",
            data: {
              userId: payload.userId,
              yogaExpertiseId: item.id,
            },
            withCredentials: true,
          };
          arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        }
        try {
          let responseArr = await axios.all(arr);
          if (responseArr) {
            let yogaExpertise = [];
            responseArr.forEach((response) => {
              if (response && response.status === 200) {
                yogaExpertise.push(response.data.data);
              }
            });
            commit("storeData", {
              id: payload.userId,
              yogaExpertise: yogaExpertise,
              field: "yogaExpertise",
            });
            resolve(yogaExpertise);
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    storeYogaCertificate({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let arr = [];
        for (let item of payload) {
          const configData = {
            url: ApiEndpoints.USER_YOGA_CERTIFICATE,
            method: "post",
            data: {
              userId: payload.userId,
              yogaCertificateId: item.id,
            },
            withCredentials: true,
          };
          arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        }
        try {
          let responseArr = await axios.all(arr);
          if (responseArr) {
            let yogaCertificates = [];
            responseArr.forEach((response) => {
              if (response && response.status === 200) {
                yogaCertificates.push(response.data.data);
              }
            });
            commit("storeData", {
              id: payload.userId,
              yogaCertificates: yogaCertificates,
              field: "yogaCertificates",
            });
            resolve(yogaCertificates);
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    deleteMedicalExpertise({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let arr = [];
        let ids = [];
        for (let item of payload) {
          const configData = {
            url: ApiEndpoints.USER_MEDICAL_EXPERTISE + `/${item}`,
            method: "delete",
            withCredentials: true,
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
              field: "medicalExpertise",
            });
            resolve();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    deleteYogaExpertise({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let arr = [];
        let ids = [];
        for (let item of payload) {
          const configData = {
            url: ApiEndpoints.USER_YOGA_EXPERTISE + `/${item}`,
            method: "delete",
            withCredentials: true,
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
              field: "yogaExpertise",
            });
            resolve();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },

    deleteYogaCertificate({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let arr = [];
        let ids = [];
        for (let item of payload) {
          const configData = {
            url: ApiEndpoints.USER_YOGA_CERTIFICATE + `/${item}`,
            method: "delete",
            withCredentials: true,
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
              field: "yogaCertificates",
            });
            resolve();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },
  },
  getters: {
    user(state) {
      return (id) => state.users.find((user) => user.id == id);
    },
  },
};
