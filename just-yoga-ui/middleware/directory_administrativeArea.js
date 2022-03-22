export default async function ({ store, params, error }) {
  let data = store.getters["directory/administrativeAreaByPage"](params.state, params.page);
  if (!data) {
    try {
      await store.dispatch("directory/get", {page: params.page, administrativeAreaLevel1Id: params.state});
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
