import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";

export default {
  state: () => ({
    // totalElements, totalPages, page, hasNext, hasPrevious, userId, data: [reviewId]
    profile_reviews: [],
  }),
  mutations: {
    store(state, payload) {
      let present = state.profile_reviews.find((data) => {
        return data.page === payload.page && data.userId === payload.userId;
      });
      if (present === null || present === undefined) {
        state.profile_reviews.push(payload);
      }
    },
    deleteReview(state, payload) {
      for (let i = 0; i < state.profile_reviews.length; i++) {
        let data = state.profile_reviews[i].data;
        let done = false;
        if (data && data.length > 0) {
          for (let j = 0; j < data.length; j++) {
            if (data[j] === payload.id) {
              state.profile_reviews[i].data.splice(j, 1);
              done = true;
              break;
            }
          }
        }
        if (done) break;
      }
    },
  },
  actions: {
    deleteReview({ commit }, payload) {
      commit("deleteReview", payload);
    },
    async get({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const reviewConfigData = {
          url: ApiEndpoints.REVIEWS,
          method: "get",
          params: {
            page: payload.page,
            count: 3,
            userId: payload.userId,
          },
          withCredentials: true,
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
            reviews.forEach((review) => reviewIds.push(review.id));
            commit("store", {
              totalElements: reviewResponse.data.data.totalElements,
              totalPages: reviewResponse.data.data.totalPages,
              hasPrevious: reviewResponse.data.data.hasPrevious,
              hasNext: reviewResponse.data.data.hasNext,
              page: payload.page,
              userId: payload.userId,
              data: reviewIds,
            });
            dispatch("reviews/storeAll", reviews, {
              root: true,
            });
            resolve(reviews);
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
    byPageAndUserId(state) {
      return (userId, page) =>
        state.profile_reviews.find(
          (profile_review) =>
            profile_review.page == page && profile_review.userId == userId
        );
    },
  },
};
