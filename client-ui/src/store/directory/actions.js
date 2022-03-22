import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";

export function get({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const trainerConfigData = {
      url: ApiEndpoints.SEARCH_USER_INFO,
      method: "get",
      params: {
        page: payload.page,
        count: 10,
        order: "desc",
        sort: "updatedAt",
        subLocalityLevel1Id: payload.sub,
        countryId: payload.countryId,
        administrativeAreaLevel1Id: payload.administrativeAreaLevel1Id,
        localityId: payload.localityId,
        trainer: true
      },
      withCredentials: true
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
            data: trainerResponse.data.data
          });
        } else if (payload.localityId) {
          commit("store", {
            state: "locality",
            page: payload.page,
            id: payload.localityId,
            data: trainerResponse.data.data
          });
        } else if (payload.administrativeAreaLevel1Id) {
          commit("store", {
            state: "administrativeArea",
            page: payload.page,
            id: payload.administrativeAreaLevel1Id,
            data: trainerResponse.data.data
          });
        } else if (payload.countryId) {
          commit("store", {
            state: "country",
            page: payload.page,
            id: payload.countryId,
            data: trainerResponse.data.data
          });
        } else {
          commit("store", {
            state: "directory",
            page: payload.page,
            id: payload.sub,
            data: trainerResponse.data.data
          });
        }
        resolve(trainerResponse.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}
