export default async function ({ store, params, error }) {
  let data = store.getters["directory_images/byPage"](params.page);
  if (!data) {
    try {
      await store.dispatch("directory_images/get", {page: params.page});
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
