import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";

export default {
  state: () => ({
    images: [],
    profile_images: [] // [{totalElements, totalPages, page, hasNext, hasPrevious, userId, data: [imageId]}]
  }),
  mutations: {
    store(state, payload) {
      let present = state.images.find((image) => {
        return image.id === payload.id;
      });
      if (present === null || present === undefined) {
        state.images.push(payload);
      }
    },

    storeAll(state, payload) {
      payload.forEach((imageToSave) => {
        let present = state.images.find((image) => {
          return image.id === imageToSave.id;
        });
        if (present === null || present === undefined) {
          state.images.push(imageToSave);
        }
      });
    },

    update(state, payload) {
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
    },

    delete(state, payload) {
      for (let i = 0; i < state.images.length; i++) {
        if (state.images[i].id === payload) {
          state.images.splice(i, 1);
          break;
        }
      }
    },

    storeProfileImages(state, payload) {
      let present = state.profile_images.find((data) => {
        return data.page === payload.page && data.userId === payload.userId;
      });
      if (present === null || present === undefined) {
        state.profile_images.push(payload);
      }
    },

  },
  actions: {
    async store({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let formData = new FormData();
        formData.append("image", payload.coverPic.blob);
        formData.append(
          "request",
          JSON.stringify({
            userId:payload.userId,
            title: payload.title,
            description: payload.description,
          })
        );
        const configData = {
          url: ApiEndpoints.IMAGES,
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
                images: [response.data.data],
                field: "images",
              },
              {
                root: true,
              }
            );
            commit("store", response.data.data);
            resolve(response.data.data)
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

    async getProfileImages({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.IMAGES,
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
            let images = response.data.data.content;
            let imageIds = [];
            images.forEach(image => imageIds.push(image.id));
            commit("storeProfileImages", {
              totalElements: response.data.data.totalElements,
              totalPages: response.data.data.totalPages,
              hasPrevious: response.data.data.hasPrevious,
              hasNext: response.data.data.hasNext,
              page: payload.page,
              userId: payload.userId,
              data: imageIds,
            });
            commit("storeAll", images);
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

    async get({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.IMAGES + `/${payload.id}`,
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

    async update({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.IMAGES,
          method: "put",
          data: payload,
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            commit("update", response.data.data);
            dispatch("users/updateImage", response.data.data, {
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
          url: ApiEndpoints.IMAGES + `/${payload.id}`,
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
                field: "images",
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
      return (id) => state.images.find((image) => image.id == id);
    },

    byPageAndUserId(state) {
      return (userId, page) =>
        state.profile_images.find(
          profile_image =>
            profile_image.page == page && profile_image.userId == userId
        );
    },
  },
};
