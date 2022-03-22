export default async function ({ store, params, error }) {
  let image = store.getters["images/byId"](params.id);
  if (image === null || image === undefined) {
    try {
      let image = await store.dispatch("images/get", { id: params.id });
      let user = store.getters["users/user"](image.userId);
      if (user === null || user === undefined) {
        await store.dispatch("users/storeUser", { id: image.userId });
      }
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
