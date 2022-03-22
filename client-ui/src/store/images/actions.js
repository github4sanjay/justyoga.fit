import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";
import MediaUploader from "src/utils/MediaUploader";

export function store({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const url = await MediaUploader.uploadMedia({
      media: payload.coverPic.blob,
      contentType: payload.coverPic.blob.type
    });

    const configData = {
      url: ApiEndpoints.IMAGES,
      data: {
        url: url.id,
        userId: payload.userId,
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
            images: [response.data.data],
            field: "images"
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

export function getProfileImages({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.IMAGES,
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
          data: imageIds
        });
        commit("storeAll", images);
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

export function get({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.IMAGES + `/${payload.id}`,
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

export function update({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.IMAGES,
      method: "put",
      data: payload,
      withCredentials: true
    };
    try {
      let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
      if (response && response.status === 200) {
        commit("update", response.data.data);
        dispatch("users/updateImage", response.data.data, {
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
      url: ApiEndpoints.IMAGES + `/${payload.id}`,
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
            field: "images"
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
    }
  });
}
