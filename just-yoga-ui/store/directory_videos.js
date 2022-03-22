import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";

export default {
  state: () => ({
    // page, sub, data: [{user, trainer}]
    subLocalityLevel1: [],
    country: [],
    directory: [],
    administrativeArea: [],
    locality: []
  }),
  mutations: {
    store(state, payload) {
      let present = state[payload.state].find((data) => {
        return (data.page === payload.page && data.id === payload.id);
      });
      if (present === null || present === undefined) {
        state[payload.state].push(payload);
      }
    },
  },
  actions: {
    async get({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const trainerConfigData = {
          url: ApiEndpoints.SEARCH_VIDEO_INFO,
          method: "get",
          params: {
            page: payload.page,
            count: 10,
            subLocalityLevel1Id: payload.sub,
            order: "desc",
            sort: "updatedAt",
            countryId: payload.countryId,
            administrativeAreaLevel1Id: payload.administrativeAreaLevel1Id,
            localityId: payload.localityId,
            trainer: null
          },
          withCredentials: true,
        };
        try {
          let trainerResponse = await AxiosUtil.getAxiosRequest(
            trainerConfigData,
            dispatch
          );
          if (
            trainerResponse &&
            trainerResponse.status === 200 &&
            trainerResponse.data.data
          ) {
            if (payload.sub) {
              commit("store", {
                state: "subLocalityLevel1",
                page: payload.page,
                id: payload.sub,
                data: trainerResponse.data.data,
              });
            }else if (payload.localityId){
              commit("store", {
                state: "locality",
                page: payload.page,
                id: payload.localityId,
                data: trainerResponse.data.data,
              });
            } else if(payload.administrativeAreaLevel1Id){
              commit("store", {
                state: "administrativeArea",
                page: payload.page,
                id: payload.administrativeAreaLevel1Id,
                data: trainerResponse.data.data,
              });
            } else if(payload.countryId){
              commit("store", {
                state: "country",
                page: payload.page,
                id: payload.countryId,
                data: trainerResponse.data.data,
              });
            }else {
              commit("store", {
                state: "directory",
                page: payload.page,
                id: payload.sub,
                data: trainerResponse.data.data,
              });
            }
            resolve(trainerResponse.data.data)
          } else {
            reject();
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, {
            root: true,
          });
          reject();
        }
      });
    },
  },
  getters: {
    byPage(state) {
      return (page) =>
        state.directory.find(
          (sub) => sub.page == page
        );
    },
    subLocalityLevel1ByPage(state) {
      return (subLocalityLevel1Id, page) =>
        state.subLocalityLevel1.find(
          (sub) => sub.page == page && sub.id == subLocalityLevel1Id
        );
    },
    countryByPage(state) {
      return (countryId, page) =>
        state.country.find(
          (country) => country.page == page && country.id == countryId
        );
    },
    administrativeAreaByPage(state) {
      return (administrativeAreaId, page) =>
        state.administrativeArea.find(
          (administrativeArea) => administrativeArea.page == page && administrativeArea.id == administrativeAreaId
        );
    },
    localityByPage(state) {
      return (localityId, page) =>
        state.locality.find(
          (locality) => locality.page == page && locality.id == localityId
        );
    },
  },
};
