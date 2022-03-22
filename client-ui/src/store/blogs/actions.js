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
    const blogConfigData = {
      url: ApiEndpoints.BLOGS + `/${payload.id}`,
      method: "get",
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(
        blogConfigData,
        dispatch
      );
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        commit("store", blogResponse.data.data);
        resolve(blogResponse.data.data);
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
    const blogConfigData = {
      url: ApiEndpoints.BLOGS,
      method: "put",
      data: payload,
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(
        blogConfigData,
        dispatch
      );
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        let blog = blogResponse.data.data;
        if (payload.id) {
          commit("updateBlog", blog);
        } else {
          commit("store", blog);
        }
        resolve(blog);
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
    const blogConfigData = {
      url: ApiEndpoints.BLOGS + `/${payload.id}`,
      method: "delete",
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(
        blogConfigData,
        dispatch
      );
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        commit("remove", payload);
        dispatch("profile_blog/deleteBlog", payload, {
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
    try {
      if (!payload.images) return resolve();
      let requests = [];
      for (let i = 0; i < payload.images.length; i++) {
        const url = await MediaUploader.uploadMedia({
          media: payload.images[i].blob,
          contentType: payload.images[i].blob.type
        });
        const blogConfigData = {
          url: ApiEndpoints.BLOGS + `/${payload.blogId}/images`,
          method: "post",
          data: { url: url.id },
          withCredentials: true
        };
        requests.push(AxiosUtil.getAxiosRequest(blogConfigData, dispatch));
      }

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
          id: payload.blogId,
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
      const blogConfigData = {
        url: ApiEndpoints.BLOGS + `/${payload.blogId}/videos`,
        method: "post",
        data: { url: url.id },
        withCredentials: true
      };
      requests.push(AxiosUtil.getAxiosRequest(blogConfigData, dispatch));
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
          id: payload.blogId,
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
      url: ApiEndpoints.BLOG_COMMENTS,
      method: "put",
      data: {
        text: payload.text,
        blogId: payload.blogId,
        userId: payload.userId
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data.data) {
        let comments = response.data.data;
        commit("storeData", {
          id: payload.blogId,
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
      url: ApiEndpoints.BLOG_LIKES,
      method: "put",
      data: {
        blogId: payload.blogId,
        userId: payload.userId
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data.data) {
        let likes = response.data.data;
        commit("storeData", {
          id: payload.blogId,
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
      url: ApiEndpoints.BLOGS + `/${payload.blogId}/images`,
      method: "get",
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        let images = blogResponse.data.data;
        commit("storeData", {
          id: payload.blogId,
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
      url: ApiEndpoints.BLOGS + `/${payload.blogId}/videos`,
      method: "get",
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        let videos = blogResponse.data.data;
        commit("storeData", {
          id: payload.blogId,
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
      url: ApiEndpoints.BLOGS + `/${payload.blogId}/comments`,
      method: "get",
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        let images = blogResponse.data.data;
        commit("storeData", {
          id: payload.blogId,
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
      url: ApiEndpoints.BLOGS + `/${payload.blogId}/likes`,
      method: "get",
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        let likes = blogResponse.data.data;
        commit("storeData", {
          id: payload.blogId,
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
      url: ApiEndpoints.BLOG_LIKES + `/${payload.id}`,
      method: "delete",
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        let likes = blogResponse.data.data;
        commit("deleteData", {
          data: { blogId: payload.blogId, id: payload.id },
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
        url: ApiEndpoints.BLOG_IMAGES + `/${image.id}`,
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
              data: { blogId: payload[i].blogId, id: payload[i].id },
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
        url: ApiEndpoints.BLOG_VIDEOS + `/${video.id}`,
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
              data: { blogId: payload[i].blogId, id: payload[i].id },
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

export function storeCover({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    // let formData = new FormData();
    // formData.append("image", payload.image.blob);
    const configData = {
      url: ApiEndpoints.BLOGS + `/${payload.blogId}/cover`,
      data: { url: payload.image },
      method: "put",
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        commit("updateBlog", response.data.data);
        resolve(response.data.data);
      } else {
        reject();
      }
    } catch (e) {
      dispatch("shared/setErrorText", "Something went wrong", {
        root: true
      });
      dispatch("shared/setErrorSnackbar", true, { root: true });
      reject();
    }
  });
}
