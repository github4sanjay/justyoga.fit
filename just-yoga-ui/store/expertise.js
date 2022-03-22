import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";
import axios from "axios";

export default {
  state: () => ({
    medicalExpertise: [],
    yogaExpertise: [],
    yogaCertificates: []
  }),
  mutations: {

    storeAllMedicalExpertise(state, payload) {
      payload.forEach((toSave) => {
        let present = state.medicalExpertise.find((data) => {
          return data.id === toSave.id;
        });
        if (present === null || present === undefined) {
          state.medicalExpertise.push(toSave);
        }
      });
    },

    storeAllYogaExpertise(state, payload) {
      payload.forEach((toSave) => {
        let present = state.yogaExpertise.find((data) => {
          return data.id === toSave.id;
        });
        if (present === null || present === undefined) {
          state.yogaExpertise.push(toSave);
        }
      });
    },

    storeAllYogaCertificates(state, payload) {
      payload.forEach((toSave) => {
        let present = state.yogaCertificates.find((data) => {
          return data.id === toSave.id;
        });
        if (present === null || present === undefined) {
          state.yogaCertificates.push(toSave);
        }
      });
    },

  },
  actions: {
    async getData({ commit, state, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let arr = [];

        const configData1 = {
          url: ApiEndpoints.MEDICAL_EXPERTISE,
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData1, dispatch));

        const configData2 = {
          url: ApiEndpoints.YOGA_EXPERTISE,
          method: "get",
          withCredentials: true,
        };
        arr.push(AxiosUtil.getAxiosRequest(configData2, dispatch));

        const configData3 = {
          url: ApiEndpoints.YOGA_CERTIFICATE,
          method: "get",
          withCredentials: true,
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
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          reject();
        }
      });
    },
  },
  getters: {
    medicalExpertiseById(state) {
      return (id) => state.medicalExpertise.find((medicalExpertise) => medicalExpertise.id == id);
    },

    yogaExpertiseById(state) {
      return (id) => state.yogaExpertise.find((yogaExpertise) => yogaExpertise.id == id);
    },

    yogaCertificateById(state) {
      return (id) => state.yogaCertificates.find((yogaCertificate) => yogaCertificate.id == id);
    },

    medicalExpertise(state) {
      return state.medicalExpertise;
    },

    yogaExpertise(state) {
      return state.yogaExpertise;
    },

    yogaCertificates(state) {
      return state.yogaCertificates;
    },
  },
};
