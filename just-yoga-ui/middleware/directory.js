export default async function ({ store, params, error }) {
  let data = store.getters["directory/byPage"](params.page);
  if (!data) {
    try {
      await store.dispatch("directory/get", {page: params.page});
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
