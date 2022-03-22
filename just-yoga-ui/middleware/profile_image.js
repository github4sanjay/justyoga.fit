export default async function ({ store, params, error }) {
  let page = params.page ? params.page : 0;
  let data = store.getters["images/byPageAndUserId"](
    params.id,
    page
  );
  if (!data) {
    try {
      await store.dispatch("images/getProfileImages", {
        page: page,
        userId: params.id,
        count: 4
      });
    } catch (e) {
      error({ statusCode: 500 });
    }
  }
}
