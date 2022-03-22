import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import axios from "axios";
import NotifyUtil from "src/utils/NotifyUtil";
import MediaUploader from "src/utils/MediaUploader";

export function storeData({ commit }, payload) {
  commit("storeData", payload);
}

export function storeAll({ commit }, payload) {
  commit("storeAll", payload);
}

export function get({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const reviewConfigData = {
      url: ApiEndpoints.REVIEWS + `/${payload.id}`,
      method: "get",
      withCredentials: true
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
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function store({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const reviewConfigData = {
      url: ApiEndpoints.REVIEWS,
      method: "put",
      data: payload,
      withCredentials: true
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
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function remove({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const reviewConfigData = {
      url: ApiEndpoints.REVIEWS + `/${payload.id}`,
      method: "delete",
      withCredentials: true
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
        commit("remove", payload);
        dispatch("profile_review/deleteReview", payload, {
          root: true
        });
        resolve(true);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function storeImages({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    if (!payload.images) return resolve();
    let requests = [];
    for (let i = 0; i < payload.images.length; i++) {
      const url = await MediaUploader.uploadMedia({
        media: payload.images[i].blob,
        contentType: payload.images[i].blob.type
      });
      const reviewConfigData = {
        url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/images`,
        method: "post",
        data: { url: url.id },
        withCredentials: true
      };
      requests.push(AxiosUtil.getAxiosRequest(reviewConfigData, dispatch));
    }
    try {
      let responseArr = await axios.all(requests);
      let errorOccurred = false;
      if (responseArr) {
        let images = [];
        responseArr.forEach(response => {
          if (response && response.status === 200 && response.data.data) {
            images.push(response.data.data);
          }
        });
        commit("storeData", {
          id: payload.reviewId,
          images: images,
          field: "images"
        });
      } else {
        errorOccurred = true;
      }
      if (errorOccurred) {
        NotifyUtil.showError("Something went wrong");
        reject();
      } else {
        resolve();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function storeVideos({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    if (!payload.videos) return resolve();
    let requests = [];
    for (let i = 0; i < payload.videos.length; i++) {
      const url = await MediaUploader.uploadMedia({
        media: payload.videos[i].file,
        contentType: payload.videos[i].file.type
      });
      const reviewConfigData = {
        url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/videos`,
        method: "post",
        data: { url: url.id },
        withCredentials: true
      };
      requests.push(AxiosUtil.getAxiosRequest(reviewConfigData, dispatch));
    }
    try {
      let responseArr = await axios.all(requests);
      let errorOccurred = false;
      if (responseArr) {
        let videos = [];
        responseArr.forEach(response => {
          if (response && response.status === 200 && response.data.data) {
            videos.push(response.data.data);
          }
        });
        commit("storeData", {
          id: payload.reviewId,
          videos: videos,
          field: "videos"
        });
      } else {
        errorOccurred = true;
      }
      if (errorOccurred) {
        NotifyUtil.showError("Something went wrong");
        reject();
      } else {
        resolve();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function storeComments({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    if (!payload) return resolve();
    const configData = {
      url: ApiEndpoints.REVIEW_COMMENTS,
      method: "put",
      data: {
        text: payload.text,
        reviewId: payload.reviewId,
        userId: payload.userId
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data.data) {
        let comments = response.data.data;
        commit("storeData", {
          id: payload.reviewId,
          comments: [comments],
          field: "comments"
        });
        resolve(comments);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function storeLikes({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    if (!payload) return resolve();
    const configData = {
      url: ApiEndpoints.REVIEW_LIKES,
      method: "put",
      data: {
        reviewId: payload.reviewId,
        userId: payload.userId
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data.data) {
        let likes = response.data.data;
        commit("storeData", {
          id: payload.reviewId,
          likes: [likes],
          field: "likes"
        });
        resolve(likes);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getImages({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/images`,
      method: "get",
      withCredentials: true
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
          field: "images"
        });
        resolve(images);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getVideos({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/videos`,
      method: "get",
      withCredentials: true
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
          field: "videos"
        });
        resolve(videos);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getComments({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/comments`,
      method: "get",
      withCredentials: true
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
          field: "comments"
        });
        resolve(images);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getLikes({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.REVIEWS + `/${payload.reviewId}/likes`,
      method: "get",
      withCredentials: true
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
          field: "likes"
        });
        resolve(likes);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function deleteLikes({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.REVIEW_LIKES + `/${payload.id}`,
      method: "delete",
      withCredentials: true
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
          field: "likes"
        });
        resolve(likes);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function deleteImages({ commit, dispatch }, payload) {
  // for deleting multi images in parallel
  return new Promise(async (resolve, reject) => {
    let request = [];
    payload.forEach(image => {
      const configData = {
        url: ApiEndpoints.REVIEW_IMAGES + `/${image.id}`,
        method: "delete",
        withCredentials: true
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
              field: "images"
            });
          }
        }
        resolve(true);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function deleteVideos({ commit, dispatch }, payload) {
  // for deleting multi videos in parallel
  return new Promise(async (resolve, reject) => {
    let request = [];
    payload.forEach(video => {
      const configData = {
        url: ApiEndpoints.REVIEW_VIDEOS + `/${video.id}`,
        method: "delete",
        withCredentials: true
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
              field: "videos"
            });
          }
        }
        resolve(true);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}
