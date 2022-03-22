export default async function ({ store, params, error }) {
  let data = store.getters["directory_videos/subLocalityLevel1ByPage"](params.sub, params.page);
  if (!data) {
    try {
      await store.dispatch("directory_videos/get", {page: params.page, sub: params.sub});
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
