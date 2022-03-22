import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";

export function get({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const trainerConfigData = {
      url: ApiEndpoints.SEARCH_BLOG_INFO,
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
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(
        trainerConfigData,
        dispatch
      );
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        if (payload.sub) {
          commit("store", {
            state: "subLocalityLevel1",
            page: payload.page,
            id: payload.sub,
            data: blogResponse.data.data
          });
        } else if (payload.localityId) {
          commit("store", {
            state: "locality",
            page: payload.page,
            id: payload.localityId,
            data: blogResponse.data.data
          });
        } else if (payload.administrativeAreaLevel1Id) {
          commit("store", {
            state: "administrativeArea",
            page: payload.page,
            id: payload.administrativeAreaLevel1Id,
            data: blogResponse.data.data
          });
        } else if (payload.countryId) {
          commit("store", {
            state: "country",
            page: payload.page,
            id: payload.countryId,
            data: blogResponse.data.data
          });
        } else {
          commit("store", {
            state: "directory",
            page: payload.page,
            id: payload.sub,
            data: blogResponse.data.data
          });
        }
        resolve(blogResponse.data.data);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}
