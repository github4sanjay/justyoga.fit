import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";
import MediaUploader from "src/utils/MediaUploader";

export function store({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const coverUrl = await MediaUploader.uploadMedia({
      media: payload.coverPic.blob,
      contentType: payload.coverPic.blob.type
    });

    const videoUrl = await MediaUploader.uploadMedia({
      media: payload.video,
      contentType: payload.video.type
    });

    const configData = {
      url: ApiEndpoints.VIDEOS,
      data: {
        url: videoUrl.id,
        coverPic: coverUrl.id,
        userId: payload.userId,
        duration: payload.duration,
        title: payload.title,
        description: payload.description
      },
      method: "post",
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        dispatch(
          "users/storeData",
          {
            id: payload.userId,
            videos: [response.data.data],
            field: "videos"
          },
          {
            root: true
          }
        );
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

export function get({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.VIDEOS + `/${payload.id}`,
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

export function getProfileVideos({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.VIDEOS,
      params: {
        userId: payload.userId,
        page: payload.page,
        count: payload.count
      },
      method: "get",
      withCredentials: true
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
          data: videoIds
        });
        commit("storeAll", videos);
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

export function update({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.VIDEOS,
      method: "put",
      data: payload,
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        commit("update", response.data.data);
        dispatch("users/updateVideo", response.data.data, {
          root: true
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

export function remove({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.VIDEOS + `/${payload.id}`,
      method: "delete",
      data: payload,
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        commit("remove", payload.id);
        dispatch(
          "users/deleteData",
          {
            userId: payload.userId,
            data: [payload],
            field: "videos"
          },
          {
            root: true
          }
        );
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
