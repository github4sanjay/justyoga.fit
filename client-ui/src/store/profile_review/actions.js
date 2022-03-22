import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";

export function deleteReview({ commit }, payload) {
  commit("deleteReview", payload);
}

export function get({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const reviewConfigData = {
      url: ApiEndpoints.REVIEWS,
      method: "get",
      params: {
        page: payload.page,
        count: 3,
        userId: payload.userId
      },
      withCredentials: true
    };
    try {
      let reviewResponse = await AxiosUtil.getAxiosRequest(
        reviewConfigData,
        dispatch
      );
      if (
        reviewResponse &&
        reviewResponse.status === 200 &&
        reviewResponse.data.data
      ) {
        let reviews = reviewResponse.data.data.content;
        let reviewIds = [];
        reviews.forEach(review => reviewIds.push(review.id));
        commit("store", {
          totalElements: reviewResponse.data.data.totalElements,
          totalPages: reviewResponse.data.data.totalPages,
          hasPrevious: reviewResponse.data.data.hasPrevious,
          hasNext: reviewResponse.data.data.hasNext,
          page: payload.page,
          userId: payload.userId,
          data: reviewIds
        });
        dispatch("reviews/storeAll", reviews, {
          root: true
        });
        resolve(reviews);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}
