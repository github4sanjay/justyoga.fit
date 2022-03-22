import axios from "axios";

export default {
  getAxiosRequest(configData, dispatch) {
    return axios.request(configData).catch((e) => {
      if (e.response && e.response.data && e.response.data.data) {
        dispatch("shared/setErrorText", e.response.data.data.errMsg, {
          root: true,
        });
        dispatch("shared/setErrorSnackbar", true, {
          root: true,
        });
      } else {
        dispatch("shared/setErrorText", "Something went wrong", {
          root: true,
        });
        dispatch("shared/setErrorSnackbar", true, {
          root: true,
        });
      }
    });
  }
}
