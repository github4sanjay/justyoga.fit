import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import axios from "axios";
import NotifyUtil from "src/utils/NotifyUtil";

export function storePlace({ commit, dispatch, params }, payload) {
  return new Promise(async (resolve, reject) => {
    const configData = {
      url: ApiEndpoints.PLACES,
      method: "post",
      data: payload.location,
      withCredentials: true
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
          subLocalityLevel2Id: location.subLocalityLevel2.id
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
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}

export function getAll({ commit, state, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    let arr = [];
    if (state.country.length === 0) {
      const configData = {
        url: ApiEndpoints.COUNTRIES,
        method: "get",
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
    }
    if (state.administrative_area_level_1.length === 0) {
      const configData = {
        url: ApiEndpoints.ADMINISTRATIVE_AREAS,
        method: "get",
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
    }
    if (state.locality.length === 0) {
      const configData = {
        url: ApiEndpoints.LOCALITIES,
        method: "get",
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
    }
    if (state.sub_localities_level_1.length === 0) {
      const configData = {
        url: ApiEndpoints.SUB_LOCALITIES1,
        method: "get",
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
    }
    if (state.sub_localities_level_2.length === 0) {
      const configData = {
        url: ApiEndpoints.SUB_LOCALITIES2,
        method: "get",
        withCredentials: true
      };
      arr.push(AxiosUtil.getAxiosRequest(configData, dispatch));
    }

    if (arr.length > 0) {
      try {
        let responseArr = await axios.all(arr);
        if (responseArr) {
          commit("storeAllPlace", {
            country: responseArr[0] ? responseArr[0].data.data : [],
            administrative_area_level_1: responseArr[1]
              ? responseArr[1].data.data
              : [],
            locality: responseArr[2] ? responseArr[2].data.data : [],
            sub_localities_level_1: responseArr[3]
              ? responseArr[3].data.data
              : [],
            sub_localities_level_2: responseArr[4]
              ? responseArr[4].data.data
              : []
          });
          resolve();
        }
      } catch (error) {
        reject();
      }
    } else {
      resolve();
    }
  });
}
