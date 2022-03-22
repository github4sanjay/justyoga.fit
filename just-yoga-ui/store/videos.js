import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";

export default {
  state: () => ({
    videos: [],
    profile_videos: [] // [{totalElements, totalPages, page, hasNext, hasPrevious, userId, data: [videoId]}]
  }),
  mutations: {
    store(state, payload) {
      let present = state.videos.find((video) => {
        return video.id === payload.id;
      });
      if (present === null || present === undefined) {
        state.videos.push(payload);
      }
    },

    storeAll(state, payload) {
      payload.forEach((videoToSave) => {
        let present = state.videos.find((video) => {
          return video.id === videoToSave.id;
        });
        if (present === null || present === undefined) {
          state.videos.push(videoToSave);
        }
      });
    },

    update(state, payload) {
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
    },

    delete(state, payload) {
      for (let i = 0; i < state.videos.length; i++) {
        if (state.videos[i].id === payload) {
          state.videos.splice(i, 1);
          break;
        }
      }
    },

    storeProfileVideos(state, payload) {
      let present = state.profile_videos.find((data) => {
        return data.page === payload.page && data.userId === payload.userId;
      });
      if (present === null || present === undefined) {
        state.profile_videos.push(payload);
      }
    },
  },
  actions: {
    async store({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let formData = new FormData();
        formData.append("video", payload.video);
        formData.append("coverPic", payload.coverPic.blob);
        formData.append(
          "request",
          JSON.stringify({
            userId:payload.userId,
            duration: payload.duration,
            title: payload.title,
            description: payload.description,
          })
        );
        const configData = {
          url: ApiEndpoints.VIDEOS,
          data: formData,
          method: "post",
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            dispatch(
              "users/storeData",
              {
                id: payload.userId,
                videos: [response.data.data],
                field: "videos",
              },
              {
                root: true,
              }
            );
            commit("store", response.data.data);
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

    async get({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.VIDEOS + `/${payload.id}`,
          method: "get",
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            commit("store", response.data.data);
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

    async getProfileVideos({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.VIDEOS,
          params: {
            userId: payload.userId,
            page: payload.page,
            count: payload.count,
          },
          method: "get",
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            let videos = response.data.data.content;
            let videoIds = [];
            videos.forEach(image => videoIds.push(image.id));
            commit("storeProfileVideos", {
              totalElements: response.data.data.totalElements,
              totalPages: response.data.data.totalPages,
              hasPrevious: response.data.data.hasPrevious,
              hasNext: response.data.data.hasNext,
              page: payload.page,
              userId: payload.userId,
              data: videoIds,
            });
            commit("storeAll", videos);
            resolve(response.data);
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

    async update({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.VIDEOS,
          method: "put",
          data: payload,
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            commit("update", response.data.data);
            dispatch("users/updateVideo", response.data.data, {
              root: true,
            });
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

    async delete({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.VIDEOS + `/${payload.id}`,
          method: "delete",
          data: payload,
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            commit("delete", payload.id);
            dispatch(
              "users/deleteData",
              {
                userId: payload.userId,
                data: [payload],
                field: "videos",
              },
              {
                root: true,
              }
            );
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
  },
  getters: {
    byId(state) {
      return (id) => state.videos.find((video) => video.id == id);
    },

    byPageAndUserId(state) {
      return (userId, page) =>
        state.profile_videos.find(
          profile_video =>
            profile_video.page == page && profile_video.userId == userId
        );
    },
  },
};
