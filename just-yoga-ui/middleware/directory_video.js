export default async function ({ store, params, error }) {
  let data = store.getters["directory_videos/byPage"](params.page);
  if (!data) {
    try {
      await store.dispatch("directory_videos/get", {page: params.page});
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
