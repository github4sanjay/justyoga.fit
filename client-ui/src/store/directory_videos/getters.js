export function byPage(state) {
  return page => state.directory.find(sub => sub.page == page);
}
export function subLocalityLevel1ByPage(state) {
  return (subLocalityLevel1Id, page) =>
    state.subLocalityLevel1.find(
      sub => sub.page == page && sub.id == subLocalityLevel1Id
    );
}
export function countryByPage(state) {
  return (countryId, page) =>
    state.country.find(
      country => country.page == page && country.id == countryId
    );
}
export function administrativeAreaByPage(state) {
  return (administrativeAreaId, page) =>
    state.administrativeArea.find(
      administrativeArea =>
        administrativeArea.page == page &&
        administrativeArea.id == administrativeAreaId
    );
}
export function localityByPage(state) {
  return (localityId, page) =>
    state.locality.find(
      locality => locality.page == page && locality.id == localityId
    );
}
