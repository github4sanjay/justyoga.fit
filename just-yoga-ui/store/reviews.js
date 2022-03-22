import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";
import axios from "axios";

export default {
  state: () => ({
    // [{ id,....., images, videos, images, likes, comments}]
    reviews: [],
  }),
  mutations: {
    store(state, payload) {
      let present = state.reviews.find((data) => {
        return data.id === payload.id;
      });
      if (present === null || present === undefined) {
        state.reviews.push(payload);
      }
    },

    updateReview(state, payload) {
      for (let i = 0; i < state.reviews.length; i++) {
        if (state.reviews[i].id === payload.id) {
          state.reviews[i].createdAt = payload.createdAt;
          state.reviews[i].updatedAt = payload.updatedAt;
          state.reviews[i].createdBy = payload.createdBy;
          state.reviews[i].modifiedBy = payload.modifiedBy;
          state.reviews[i].reviewText = payload.reviewText;
          state.reviews[i].reviewContent = payload.reviewContent;
          state.reviews[i].rating = payload.rating;
          state.reviews[i].reviewedBy = payload.reviewedBy;
          state.reviews[i].userId = payload.userId;
          state.reviews[i].published = payload.published;
          state.reviews[i].id = payload.id;
        }
      }
    },

    storeAll(state, payload) {
      payload.forEach((toSave) => {
        let present = state.reviews.find((review) => {
          return review.id === toSave.id;
        });

        if (present === null || present === undefined) {
          state.reviews.push(toSave);
        }
      });
    },

    storeData(state, payload) {
      for (let i = 0; i < state.reviews.length; i++) {
        if (state.reviews[i].id === payload.id) {
          if (
            state.reviews[i][payload.field] === null ||
            state.reviews[i][payload.field] === undefined
          ) {
            state.reviews[i][payload.field] = [...payload[payload.field]];
          } else {
            payload[payload.field].forEach((toSave) => {
              let present = state.reviews[i][payload.field].find((data) => {
                return data.id === toSave.id;
              });
              if (present === null || present === undefined) {
                state.reviews[i][payload.field].push(...payload[payload.field]);
              }
            });
          }
          break;
        }
      }
    },

    delete(state, payload){
      for (let j = 0; j < state.reviews.length; j++) {
        if (state.reviews[j].id === payload.id) {
          state.reviews.splice(j, 1);
          break;
        }
      }
    },

    deleteData(state, payload) {
      let data = payload.data;
      let field = payload.field;
      for (let i = 0; i < state.reviews.length; i++) {
        if (state.reviews[i].id === data.reviewId) {
          if (
            state.reviews[i][field] !== null ||
            state.reviews[i][field] !== undefined
          ) {
            for (let j = 0; j < state.reviews[i][field].length; j++) {
              if (state.reviews[i][field][j].id === data.id) {
                state.reviews[i][payload.field].splice(j, 1);
                break;
              }
            }
          }
          break;
        }
      }
    },
  },
  actions: {
    storeData({ commit }, payload) {
      commit("storeData", payload);
    },

    storeAll({ commit }, payload) {
      commit("storeAll", payload);
    },

    async get({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const reviewConfigData = {
          url: ApiEndpoints.REVIEWS + `/${payload.id}`,
          method: "get",
          withCredentials: true,
        };
        try {
          let reviewResponse = await AxiosUtil.getAxiosRequest(
            reviewConfigData,
            dispatch
          );
          if (
            reviewResponse &&
            reviewResponse.status === 200 &&
            reviewResponse.data.data
          ) {
            commit("store", reviewResponse.data.data);
            resolve(reviewResponse.data.data);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async store({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const reviewConfigData = {
          url: ApiEndpoints.REVIEWS,
          method: "put",
          data: payload,
          withCredentials: true,
        };
        try {
          let reviewResponse = await AxiosUtil.getAxiosRequest(
            reviewConfigData,
            dispatch
          );
          if (
            reviewResponse &&
            reviewResponse.status === 200 &&
            reviewResponse.data.data
          ) {
            let review = reviewResponse.data.data;
            if (payload.id) {
              commit("updateReview", review);
            } else {
              commit("store", review);
            }
            resolve(review);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async delete({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const reviewConfigData = {
          url: ApiEndpoints.REVIEWS + `/${payload.id}`,
          method: "delete",
          withCredentials: true,
        };
        try {
          let reviewResponse = await AxiosUtil.getAxiosRequest(
            reviewConfigData,
            dispatch
          );
          if (
            reviewResponse &&
            reviewResponse.status === 200 &&
            reviewResponse.data.data
          ) {
            commit("delete", payload);
            dispatch("profile_review/deleteReview", payload, {
              root: true,
            });
            resolve(true);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async storeImages({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        if (!payload.images) return resolve();
        let requests = [];
        payload.images.forEach((image) => {
          let formData = new FormData();
          formData.append("image", image.blob);
          const reviewConfigData = {
            url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/images`,
            method: "post",
            data: formData,
            withCredentials: true,
          };
          requests.push(AxiosUtil.getAxiosRequest(reviewConfigData, dispatch));
        });
        try {
          let responseArr = await axios.all(requests);
          let errorOccurred = false;
          if (responseArr) {
            let images = [];
            responseArr.forEach((response) => {
              if (response && response.status === 200 && response.data.data) {
                images.push(response.data.data);
              }
            });
            commit("storeData", {
              id: payload.reviewId,
              images: images,
              field: "images",
            });
          } else {
            errorOccurred = true;
          }
          if (errorOccurred) {
            dispatch("shared/setErrorText", "Something went wrong", {
              root: true,
            });
            dispatch("shared/setErrorSnackbar", true, {
              root: true,
            });
            reject();
          } else {
            resolve();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async storeVideos({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        if (!payload.videos) return resolve();
        let requests = [];
        payload.videos.forEach((video) => {
          let formData = new FormData();
          formData.append("video", video.file);
          const reviewConfigData = {
            url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/videos`,
            method: "post",
            data: formData,
            withCredentials: true,
          };
          requests.push(AxiosUtil.getAxiosRequest(reviewConfigData, dispatch));
        });
        try {
          let responseArr = await axios.all(requests);
          let errorOccurred = false;
          if (responseArr) {
            let videos = [];
            responseArr.forEach((response) => {
              if (response && response.status === 200 && response.data.data) {
                videos.push(response.data.data);
              }
            });
            commit("storeData", {
              id: payload.reviewId,
              videos: videos,
              field: "videos",
            });
          } else {
            errorOccurred = true;
          }
          if (errorOccurred) {
            dispatch("shared/setErrorText", "Something went wrong", {
              root: true,
            });
            dispatch("shared/setErrorSnackbar", true, {
              root: true,
            });
            reject();
          } else {
            resolve();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async storeComments({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        if (!payload) return resolve();
        const configData = {
          url: ApiEndpoints.REVIEW_COMMENTS,
          method: "put",
          data: {
            text: payload.text,
            reviewId: payload.reviewId,
            userId: payload.userId,
          },
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200 && response.data.data) {
            let comments = response.data.data;
            commit("storeData", {
              id: payload.reviewId,
              comments: [comments],
              field: "comments",
            });
            resolve(comments);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async storeLikes({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        if (!payload) return resolve();
        const configData = {
          url: ApiEndpoints.REVIEW_LIKES,
          method: "put",
          data: {
            reviewId: payload.reviewId,
            userId: payload.userId,
          },
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200 && response.data.data) {
            let likes = response.data.data;
            commit("storeData", {
              id: payload.reviewId,
              likes: [likes],
              field: "likes",
            });
            resolve(likes);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async getImages({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/images`,
          method: "get",
          withCredentials: true,
        };
        try {
          let reviewResponse = await AxiosUtil.getAxiosRequest(
            configData,
            dispatch
          );
          if (
            reviewResponse &&
            reviewResponse.status === 200 &&
            reviewResponse.data.data
          ) {
            let images = reviewResponse.data.data;
            commit("storeData", {
              id: payload.reviewId,
              images: images,
              field: "images",
            });
            resolve(images);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async getVideos({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/videos`,
          method: "get",
          withCredentials: true,
        };
        try {
          let reviewResponse = await AxiosUtil.getAxiosRequest(
            configData,
            dispatch
          );
          if (
            reviewResponse &&
            reviewResponse.status === 200 &&
            reviewResponse.data.data
          ) {
            let videos = reviewResponse.data.data;
            commit("storeData", {
              id: payload.reviewId,
              videos: videos,
              field: "videos",
            });
            resolve(videos);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async getComments({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/comments`,
          method: "get",
          withCredentials: true,
        };
        try {
          let reviewResponse = await AxiosUtil.getAxiosRequest(
            configData,
            dispatch
          );
          if (
            reviewResponse &&
            reviewResponse.status === 200 &&
            reviewResponse.data.data
          ) {
            let images = reviewResponse.data.data;
            commit("storeData", {
              id: payload.reviewId,
              comments: images,
              field: "comments",
            });
            resolve(images);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async getLikes({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/likes`,
          method: "get",
          withCredentials: true,
        };
        try {
          let reviewResponse = await AxiosUtil.getAxiosRequest(
            configData,
            dispatch
          );
          if (
            reviewResponse &&
            reviewResponse.status === 200 &&
            reviewResponse.data.data
          ) {
            let likes = reviewResponse.data.data;
            commit("storeData", {
              id: payload.reviewId,
              likes: likes,
              field: "likes",
            });
            resolve(likes);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async deleteLikes({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.REVIEW_LIKES + `/${payload.id}`,
          method: "delete",
          withCredentials: true,
        };
        try {
          let reviewResponse = await AxiosUtil.getAxiosRequest(
            configData,
            dispatch
          );
          if (
            reviewResponse &&
            reviewResponse.status === 200 &&
            reviewResponse.data.data
          ) {
            let likes = reviewResponse.data.data;
            commit("deleteData", {
              data: { reviewId: payload.reviewId, id: payload.id },
              field: "likes",
            });
            resolve(likes);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async deleteImages({ commit, dispatch }, payload) {
      // for deleting multi images in parallel
      return new Promise(async (resolve, reject) => {
        let request = [];
        payload.forEach((video) => {
          const configData = {
            url: ApiEndpoints.REVIEW_IMAGES + `/${video.id}`,
            method: "delete",
            withCredentials: true,
          };
          request.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        });
        try {
          let responseArray = await axios.all(request);
          if (responseArray) {
            for (let i = 0; i < responseArray.length; i++) {
              if (
                responseArray[i] &&
                responseArray[i].status === 200 &&
                responseArray[i].data.data
              ) {
                commit("deleteData", {
                  data: {reviewId: payload[i].reviewId, id: payload[i].id},
                  field: "images",
                });
              }
            }
            resolve(true);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },

    async deleteVideos({ commit, dispatch }, payload) {
      // for deleting multi videos in parallel
      return new Promise(async (resolve, reject) => {
        let request = [];
        payload.forEach((video) => {
          const configData = {
            url: ApiEndpoints.REVIEW_VIDEOS + `/${video.id}`,
            method: "delete",
            withCredentials: true,
          };
          request.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        });
        try {
          let responseArray = await axios.all(request);
          if (responseArray) {
            for (let i = 0; i < responseArray.length; i++) {
              if (
                responseArray[i] &&
                responseArray[i].status === 200 &&
                responseArray[i].data.data
              ) {
                commit("deleteData", {
                  data: { reviewId: payload[i].reviewId, id: payload[i].id },
                  field: "videos",
                });
              }
            }
            resolve(true);
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },
  },
  getters: {
    findById(state) {
      return (id) => state.reviews.find((review) => review.id == id);
    },
  },
};
