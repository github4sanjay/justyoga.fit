import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";
import MediaUploader from "src/utils/MediaUploader";

export function store({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.COLLECTIONS,
      data: {
        id: payload.id,
        name: payload.name,
        description: payload.description,
        administrativeAreaLevel1Id: payload.administrativeAreaLevel1Id,
        countryId: payload.countryId,
        formattedAddress: payload.formattedAddress,
        geohash1: payload.geohash1,
        geohash150: payload.geohash150,
        geohash5: payload.geohash5,
        geohash50: payload.geohash50,
        latitude: payload.latitude,
        localityId: payload.localityId,
        longitude: payload.longitude,
        postalCode: payload.postalCode,
        subLocalityLevel1Id: payload.subLocalityLevel1Id,
        subLocalityLevel2Id: payload.subLocalityLevel2Id
      },
      method: "put",
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        if (payload.id) {
          // if already exist update
          commit("update", response.data.data);
        }
        commit("store", response.data.data);
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

export function storeCover({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const url = await MediaUploader.uploadMedia({
      media: payload.image.blob,
      contentType: payload.image.blob.type
    });
    const configData = {
      url: ApiEndpoints.COLLECTIONS + `/${payload.collectionId}/cover`,
      data: { url: url.id },
      method: "put",
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        commit("update", response.data.data);
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

export function getCollectionsPage({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.SEARCH_COLLECTION_INFO,
      params: {
        page: payload.page,
        count: payload.count,
        sort: payload.sort,
        order: payload.order
      },
      method: "get",
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200 && response.data) {
        let collections = response.data.data.content;
        let collectionIds = [];
        collections.forEach(collection => collectionIds.push(collection.id));
        commit("storeCollectionPage", {
          totalElements: response.data.data.totalElements,
          totalPages: response.data.data.totalPages,
          hasPrevious: response.data.data.hasPrevious,
          hasNext: response.data.data.hasNext,
          page: payload.page,
          data: collectionIds
        });
        commit("storeAll", collections);
        resolve(response.data);
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
    const configData = {
      url: ApiEndpoints.COLLECTIONS + `/${payload.id}`,
      method: "delete",
      data: payload,
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        commit("remove", payload);
        commit("deleteFromPage", payload);
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

export function get({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.COLLECTIONS + `/${payload.id}`,
      method: "get",
      withCredentials: true
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
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getImages({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.SEARCH_COLLECTION_IMAGE_INFO,
      method: "get",
      params: {
        collectionId: payload.id
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        commit("storeCollectionImages", {
          id: payload.id,
          images: response.data.data.content
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

export function getVideos({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.SEARCH_COLLECTION_VIDEO_INFO,
      method: "get",
      params: {
        collectionId: payload.id
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        commit("storeCollectionVideos", {
          id: payload.id,
          videos: response.data.data.content
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

export function getBlogs({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.SEARCH_COLLECTION_BLOG_INFO,
      method: "get",
      params: {
        collectionId: payload.id
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        commit("storeCollectionBlogs", {
          id: payload.id,
          blogs: response.data.data.content
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

export function storeImage({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.COLLECTION_IMAGES,
      method: "put",
      data: {
        collectionId: payload.collectionId,
        imageId: payload.imageId
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
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

export function storeVideo({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.COLLECTION_VIDEOS,
      method: "put",
      data: {
        collectionId: payload.collectionId,
        videoId: payload.videoId
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
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

export function storeBlog({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.COLLECTION_BLOGS,
      method: "put",
      data: {
        collectionId: payload.collectionId,
        blogId: payload.blogId
      },
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
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
