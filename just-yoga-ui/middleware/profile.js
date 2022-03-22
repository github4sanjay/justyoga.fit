export default async function ({ store, params, error }) {
  try {
    let user = store.getters["users/user"](params.id);
    if (user === null || user === undefined) {
      user = await store.dispatch("users/storeUser", { id: params.id });
    }

    let requests = [];
    if (user.basicInfo === null || user.basicInfo === undefined) {
      requests.push(store.dispatch("users/getBasicInfo", { id: user.id }));
      //await store.dispatch("users/getData", { id: user.id });
    }

    if (user.images === null || user.images === undefined) {
      requests.push(store.dispatch("users/getImages", { id: user.id }));
    }

    if (user.videos === null || user.videos === undefined) {
      requests.push(
        store.dispatch("users/getVideos", { id: user.id, page: 0, count: 3 })
      );
    }

    if (user.medicalExpertise === null || user.medicalExpertise === undefined) {
      requests.push(
        store.dispatch("users/getMedicalExpertise", {
          id: user.id,
          page: 0,
          count: 3,
        })
      );
    }

    if (user.yogaExpertise === null || user.yogaExpertise === undefined) {
      requests.push(store.dispatch("users/getYogaExpertise", { id: user.id }));
    }

    if (user.yogaCertificates === null || user.yogaCertificates === undefined) {
      requests.push(
        store.dispatch("users/getYogaCertificates", { id: user.id })
      );
    }

    if (user.interest === null || user.interest === undefined) {
      requests.push(store.dispatch("users/getInterests", { id: user.id }));
    }

    await Promise.all(requests);
  } catch (e) {
    console.log(e);
    error({ statusCode: 500 });
  }
}
