export default async function ({ store, params, error }) {
  let video = store.getters["videos/byId"](params.id);
  if (video === null || video === undefined) {
    try {
      let video = await store.dispatch("videos/get", { id: params.id });
      let user = store.getters["users/user"](video.userId);
      if (user === null || user === undefined) {
        await store.dispatch("users/storeUser", { id: video.userId });
      }
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
