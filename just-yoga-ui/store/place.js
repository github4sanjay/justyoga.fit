import axios from "axios";
import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";

export default {
  state: () => ({
    country: [],
    administrative_area_level_1: [],
    locality: [],
    sub_localities_level_1: [],
    sub_localities_level_2: [],
  }),
  mutations: {
    storeAllPlace(state, payload) {
      state.country.push(...payload.country);
      state.administrative_area_level_1.push(
        ...payload.administrative_area_level_1
      );
      state.locality.push(...payload.locality);
      state.sub_localities_level_1.push(...payload.sub_localities_level_1);
      state.sub_localities_level_2.push(...payload.sub_localities_level_2);
    },

    storeCountry(state, payload) {
      let presentCountry = state.country.find((country) => {
        return country.id === payload.id;
      });
      if (presentCountry === null || presentCountry === undefined) {
        state.country.push(payload);
      }
    },

    storeAdministrativeAreaLevel1(state, payload) {
      let presentCountry = state.administrative_area_level_1.find((country) => {
        return country.id === payload.id;
      });
      if (presentCountry === null || presentCountry === undefined) {
        state.administrative_area_level_1.push(payload);
      }
    },

    storeLocality(state, payload) {
      let presentCountry = state.locality.find((country) => {
        return country.id === payload.id;
      });
      if (presentCountry === null || presentCountry === undefined) {
        state.locality.push(payload);
      }
    },

    storeSubLocalitiesLevel1(state, payload) {
      let presentCountry = state.sub_localities_level_1.find((country) => {
        return country.id === payload.id;
      });
      if (presentCountry === null || presentCountry === undefined) {
        state.sub_localities_level_1.push(payload);
      }
    },

    storeSubLocalitiesLevel2(state, payload) {
      let presentCountry = state.sub_localities_level_2.find((country) => {
        return country.id === payload.id;
      });
      if (presentCountry === null || presentCountry === undefined) {
        state.sub_localities_level_2.push(payload);
      }
    },
  },
  actions: {
    async storePlace({ commit, dispatch, params }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.PLACES,
          method: "post",
          data: payload.location,
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            const location = response.data.data;
            const trainerLocationData = {
              administrativeAreaLevel1Id: location.administrativeAreaLevel1.id,
              countryId: location.country.id,
              formattedAddress: payload.location.formattedAddress,
              geohash1: payload.location.geohash1,
              geohash150: payload.location.geohash150,
              geohash5: payload.location.geohash5,
              geohash50: payload.location.geohash50,
              latitude: payload.location.latitude,
              localityId: location.locality.id,
              longitude: payload.location.longitude,
              postalCode: payload.location.postalCode,
              subLocalityLevel1Id: location.subLocalityLevel1.id,
              subLocalityLevel2Id: location.subLocalityLevel2.id,
            };

            commit("storeCountry", location.country);
            commit(
              "storeAdministrativeAreaLevel1",
              location.administrativeAreaLevel1
            );
            commit("storeLocality", location.locality);
            commit("storeSubLocalitiesLevel1", location.subLocalityLevel1);
            commit("storeSubLocalitiesLevel2", location.subLocalityLevel2);

            resolve(trainerLocationData);
          }
        } catch (e) {
          dispatch("shared/setErrorText", "Something went wrong", {
            root: true,
          });
          dispatch("shared/setErrorSnackbar", true, { root: true });
          resolve(null);
        }
      });
    },

    async getAll({ commit, state, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let arr = [];
        if (state.country.length === 0) {
          const configData = {
            url: ApiEndpoints.COUNTRIES,
            method: "get",
            withCredentials: true,
          };
          arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        }
        if (state.administrative_area_level_1.length === 0) {
          const configData = {
            url: ApiEndpoints.ADMINISTRATIVE_AREAS,
            method: "get",
            withCredentials: true,
          };
          arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        }
        if (state.locality.length === 0) {
          const configData = {
            url: ApiEndpoints.LOCALITIES,
            method: "get",
            withCredentials: true,
          };
          arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        }
        if (state.sub_localities_level_1.length === 0) {
          const configData = {
            url: ApiEndpoints.SUB_LOCALITIES1,
            method: "get",
            withCredentials: true,
          };
          arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        }
        if (state.sub_localities_level_2.length === 0) {
          const configData = {
            url: ApiEndpoints.SUB_LOCALITIES2,
            method: "get",
            withCredentials: true,
          };
          arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
        }

        if (arr.length > 0) {
          let responseArr = await axios.all(arr);
          if (responseArr) {
            commit("storeAllPlace", {
              country: responseArr[0] ? responseArr[0].data.data : [],
              administrative_area_level_1: responseArr[1] ? responseArr[1].data.data : [],
              locality: responseArr[2] ? responseArr[2].data.data : [],
              sub_localities_level_1: responseArr[3] ? responseArr[3].data.data : [],
              sub_localities_level_2: responseArr[4] ? responseArr[4].data.data : [],
            });
          }
        }
        resolve();
      });
    },
  },
  getters: {
    getAllCountry(state) {
      return state.country;
    },
    country(state) {
      return (id) => state.country.find((country) => country.id == id);
    },
    getAdministrativeAreaLevel1ByCountryId(state) {
      return (id) => {
        let result = [];
        state.administrative_area_level_1.forEach(value => {
          if (value.countryId === id){
            result.push(value);
          }
        });
        return result;
      }
    },
    administrativeAreaLevel1(state) {
      return (id) =>
        state.administrative_area_level_1.find(
          (administrative_area_level_1) => administrative_area_level_1.id === id
        );
    },
    getLocalityByAdministrativeAreaLevel1Id(state) {
      return (id) => {
        let result = [];
        state.locality.forEach(value => {
          if (value.administrativeAreaLevel1Id === id){
            result.push(value);
          }
        });
        return result;
      }
    },
    locality(state) {
      return (id) => state.locality.find((locality) => locality.id == id);
    },
    getSubLocalityLevel1ByLocalityId(state) {
      return (id) => {
        let result = [];
        state.sub_localities_level_1.forEach(value => {
          if (value.localityId === id){
            result.push(value);
          }
        });
        return result;
      }
    },
    subLocalityLevel1(state) {
      return (id) =>
        state.sub_localities_level_1.find(
          (sub_localities_level_1) => sub_localities_level_1.id == id
        );
    },
    subLocalityLevel2(state) {
      return (id) =>
        state.sub_localities_level_2.find(
          (sub_localities_level_2) => sub_localities_level_2.id == id
        );
    },
  },
};
