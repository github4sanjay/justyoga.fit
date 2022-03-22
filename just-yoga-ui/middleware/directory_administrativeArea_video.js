export default async function ({ store, params, error }) {
  let data = store.getters["directory_videos/administrativeAreaByPage"](params.state, params.page);
  if (!data) {
    try {
      await store.dispatch("directory_videos/get", {page: params.page, administrativeAreaLevel1Id: params.state});
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
