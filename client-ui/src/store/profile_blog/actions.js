import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";

export function deleteBlog({ commit }, payload) {
  commit("deleteBlog", payload);
}

export function get({ commit, dispatch }, payload) {
  return new Promise(async (resolve, reject) => {
    const blogConfigData = {
      url: ApiEndpoints.BLOGS,
      method: "get",
      params: {
        page: payload.page,
        count: 3,
        userId: payload.userId
      },
      withCredentials: true
    };
    try {
      let blogResponse = await AxiosUtil.getAxiosRequest(
        blogConfigData,
        dispatch
      );
      if (
        blogResponse &&
        blogResponse.status === 200 &&
        blogResponse.data.data
      ) {
        let blogs = blogResponse.data.data.content;
        let blogIds = [];
        blogs.forEach(blog => blogIds.push(blog.id));
        commit("store", {
          totalElements: blogResponse.data.data.totalElements,
          totalPages: blogResponse.data.data.totalPages,
          hasPrevious: blogResponse.data.data.hasPrevious,
          hasNext: blogResponse.data.data.hasNext,
          page: payload.page,
          userId: payload.userId,
          data: blogIds
        });
        dispatch("blogs/storeAll", blogs, {
          root: true
        });
        resolve(blogs);
      } else {
        reject();
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
      reject();
    }
  });
}
