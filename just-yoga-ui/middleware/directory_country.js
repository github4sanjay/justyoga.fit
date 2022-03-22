export default async function ({ store, params, error}) {
  let data = store.getters["directory/countryByPage"](params.country, params.page);
  if (!data) {
    try {
      await store.dispatch("directory/get", {page: params.page, countryId: params.country});
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
