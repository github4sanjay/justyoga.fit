export default async function ({ store, params, error }) {
  let page = params.page ? params.page : 0;
  let collectionPage = store.getters["collection/byPage"](page);
  if (collectionPage === null || collectionPage === undefined) {
    try {
      await store.dispatch("collection/getCollectionsPage", {
        page: page,
        count: 10,
        sort: "updatedAt",
        order: "desc",
      });
    }catch (e) {
      error({ statusCode: 500 })
    }
  }
}
