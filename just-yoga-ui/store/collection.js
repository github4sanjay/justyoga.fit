import ApiEndpoints from "@/constants/ApiEndpoints";
import AxiosUtil from "../utils/AxiosUtil";

export default {
  state: () => ({
    collections: [],
    collection_page: [],
  }),
  mutations: {
    store(state, payload) {
      let present = state.collections.find((image) => {
        return image.id === payload.id;
      });
      if (present === null || present === undefined) {
        state.collections.push(payload);
      }
    },

    storeAll(state, payload) {
      payload.forEach((imageToSave) => {
        let present = state.collections.find((image) => {
          return image.id === imageToSave.id;
        });
        if (present === null || present === undefined) {
          state.collections.push(imageToSave);
        }
      });
    },

    update(state, payload) {
      for (let i = 0; i < state.collections.length; i++) {
        if (state.collections[i].id === payload.id) {
          state.collections[i].id = payload.id;
          state.collections[i].createdAt = payload.createdAt;
          state.collections[i].createdBy = payload.createdBy;
          state.collections[i].modifiedBy = payload.modifiedBy;
          state.collections[i].updatedAt = payload.updatedAt;
          state.collections[i].description = payload.description;
          state.collections[i].name = payload.name;
          state.collections[i].coverUrl = payload.coverUrl;
          state.collections[i].geohash1 = payload.geohash1;
          state.collections[i].geohash150 = payload.geohash150;
          state.collections[i].geohash5 = payload.geohash5;
          state.collections[i].geohash50 = payload.geohash50;
          state.collections[i].latitude = payload.latitude;
          state.collections[i].localityId = payload.localityId;
          state.collections[i].longitude = payload.longitude;
          state.collections[i].postalCode = payload.postalCode;
          state.collections[i].subLocalityLevel1Id =
            payload.subLocalityLevel1Id;
          state.collections[i].subLocalityLevel2Id =
            payload.subLocalityLevel2Id;
          state.collections[i].administrativeAreaLevel1Id =
            payload.administrativeAreaLevel1Id;
          state.collections[i].countryId = payload.countryId;
          break;
        }
      }
    },

    delete(state, payload) {
      for (let i = 0; i < state.collections.length; i++) {
        if (state.collections[i].id === payload.id) {
          state.collections.splice(i, 1);
          break;
        }
      }
    },

    deleteFromPage(state, payload) {
      for (let i = 0; i < state.collection_page.length; i++) {
        let data = state.collection_page[i].data;
        let done = false;
        if (data && data.length > 0) {
          for (let j = 0; j < data.length; j++) {
            if (data[j] === payload.id) {
              state.collection_page[i].data.splice(j, 1);
              done = true;
              break;
            }
          }
        }
        if (done) break;
      }
    },

    storeCollectionPage(state, payload) {
      let present = state.collection_page.find((data) => {
        return data.page === payload.page;
      });
      if (present === null || present === undefined) {
        state.collection_page.push(payload);
      }
    },
  },
  actions: {
    async store({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.COLLECTIONS,
          data: {
            id: payload.id,
            name: payload.name,
            description: payload.description,
            administrativeAreaLevel1Id: payload.administrativeAreaLevel1Id,
            countryId: payload.countryId,
            formattedAddress: payload.formattedAddress,
            geohash1: payload.geohash1,
            geohash150: payload.geohash150,
            geohash5: payload.geohash5,
            geohash50: payload.geohash50,
            latitude: payload.latitude,
            localityId: payload.localityId,
            longitude: payload.longitude,
            postalCode: payload.postalCode,
            subLocalityLevel1Id: payload.subLocalityLevel1Id,
            subLocalityLevel2Id: payload.subLocalityLevel2Id,
          },
          method: "put",
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            if (payload.id){ // if already exist update
              commit("update", response.data.data);
            }
            commit("store", response.data.data);
            resolve(response.data.data);
          } else {
            reject();
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

    storeCover({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        let formData = new FormData();
        formData.append("image", payload.image.blob);
        const configData = {
          url: ApiEndpoints.COLLECTIONS + `/${payload.collectionId}/cover`,
          data: formData,
          method: "put",
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200 && response.data) {
            commit("update", response.data.data);
            resolve(response.data.data);
          } else {
            reject();
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

    async getCollectionsPage({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.COLLECTIONS,
          params: {
            page: payload.page,
            count: payload.count,
            sort: payload.sort,
            order: payload.order,
          },
          method: "get",
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200 && response.data) {
            let collections = response.data.data.content;
            let collectionIds = [];
            collections.forEach((collection) =>
              collectionIds.push(collection.id)
            );
            commit("storeCollectionPage", {
              totalElements: response.data.data.totalElements,
              totalPages: response.data.data.totalPages,
              hasPrevious: response.data.data.hasPrevious,
              hasNext: response.data.data.hasNext,
              page: payload.page,
              data: collectionIds,
            });
            commit("storeAll", collections);
            resolve(response.data);
          } else {
            reject();
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

    async delete({ commit, dispatch }, payload) {
      return new Promise(async (resolve, reject) => {
        const configData = {
          url: ApiEndpoints.COLLECTIONS + `/${payload.id}`,
          method: "delete",
          data: payload,
          withCredentials: true,
        };
        try {
          let response = await AxiosUtil.getAxiosRequest(configData, dispatch);
          if (response && response.status === 200) {
            commit("delete", payload);
            commit("deleteFromPage", payload);
            resolve(response.data.data);
          } else {
            reject();
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
    byId(state) {
      return (id) =>
        state.collections.find((collection) => collection.id == id);
    },

    byPage(state) {
      return (page) =>
        state.collection_page.find(
          (collection_page) => collection_page.page == page
        );
    },
  },
};
