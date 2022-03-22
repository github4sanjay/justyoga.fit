import AxiosUtil from "../../utils/AxiosUtil";
import ApiEndpoints from "../../constants/ApiEndpoints";
import axios from "axios";
import NotifyUtil from "src/utils/NotifyUtil";

export function getData({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let arr = [];

    const configData1 = {
      url: ApiEndpoints.MEDICAL_EXPERTISE,
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData1, dispatch));

    const configData2 = {
      url: ApiEndpoints.YOGA_EXPERTISE,
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData2, dispatch));

    const configData3 = {
      url: ApiEndpoints.YOGA_CERTIFICATE,
      method: "get",
      withCredentials: true
    };
    arr.push(AxiosUtil.getAxiosRequest(configData3, dispatch));
    try {
      let responseArr = await axios.all(arr);
      if (responseArr) {
        if (responseArr[0]) {
          commit("storeAllMedicalExpertise", responseArr[0].data.data);
        }
        if (responseArr[1]) {
          commit("storeAllYogaExpertise", responseArr[1].data.data);
        }
        if (responseArr[2]) {
          commit("storeAllYogaCertificates", responseArr[2].data.data);
        }
        resolve();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}
