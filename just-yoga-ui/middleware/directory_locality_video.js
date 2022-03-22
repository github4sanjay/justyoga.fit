export default async function ({ store, params, error }) {
  let data = store.getters["directory_videos/localityByPage"](params.locality, params.page);
  if (!data) {
    try {
      await store.dispatch("directory_videos/get", {page: params.page, localityId: params.locality});
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
