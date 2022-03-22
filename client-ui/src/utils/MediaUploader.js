import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";

export default {
  getUploadUrlPublic() {
    return new Promise(async (resolve, reject) => {
      const configData = {
        url: ApiEndpoints.MEDIA_UPLOAD_URL_PUBLIC,
        method: "post",
        withCredentials: true
      };
      try {
        let urlResponse = await AxiosUtil.getAxiosRequest(configData, null);
        if (
          urlResponse &&
          urlResponse.status === 200 &&
          urlResponse.data.data
        ) {
          resolve(urlResponse.data.data);
        } else {
          reject();
        }
      } catch (e) {
        NotifyUtil.showError("Something went wrong");
        reject();
      }
    });
  },
  getUploadUrl() {
    return new Promise(async (resolve, reject) => {
      const configData = {
        url: ApiEndpoints.MEDIA_UPLOAD_URL,
        method: "post",
        withCredentials: true
      };
      try {
        let urlResponse = await AxiosUtil.getAxiosRequest(configData, null);
        if (
          urlResponse &&
          urlResponse.status === 200 &&
          urlResponse.data.data
        ) {
          resolve(urlResponse.data.data);
        } else {
          reject();
        }
      } catch (e) {
        NotifyUtil.showError("Something went wrong");
        reject();
      }
    });
  },
  upload(url, data, contentType) {
    return new Promise(async (resolve, reject) => {
      const configData = {
        url: url,
        method: "put",
        data: data,
        headers: {
          "Content-Type": contentType
        }
      };
      try {
        let urlResponse = await AxiosUtil.getAxiosRequest(configData, null);
        if (urlResponse && urlResponse.status === 200) {
          resolve();
        } else {
          reject();
        }
      } catch (e) {
        NotifyUtil.showError("Something went wrong");
        reject();
      }
    });
  },
  async uploadPublicMedia(payload) {
    let url = await this.getUploadUrlPublic();
    await this.upload(url.uploadUrl, payload.media, payload.contentType);
    return url;
  },
  async uploadMedia(payload) {
    let url = await this.getUploadUrl();
    await this.upload(url.uploadUrl, payload.media, payload.contentType);
    return url;
  }
};
