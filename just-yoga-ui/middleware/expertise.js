export default async function ({ store, params }) {
  let medicalExpertise = store.getters["expertise/medicalExpertise"];
  if (!(Array.isArray(medicalExpertise) && medicalExpertise.length)) {
    await store.dispatch("expertise/getData", {});
  }
}
