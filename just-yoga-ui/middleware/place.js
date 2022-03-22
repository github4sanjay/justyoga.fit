export default async function ({ store }) {
  await store.dispatch("place/getAll", {});
}
