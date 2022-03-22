import { instance } from "boot/axios";

export default {
  async getAxiosRequest(configData, dispatch) {
    try {
      return instance.request(configData);
    } catch (e) {
      if (e.response && e.response.data && e.response.data.data) {
        NotifyUtil.showError(e.response.data.data.errMsg);
      } else {
        NotifyUtil.showError("Something went wrong");
      }
    }
  }
};
