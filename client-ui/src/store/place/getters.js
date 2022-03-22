export function getAllCountry(state) {
  return state.country;
}

export function country(state) {
  return id => state.country.find(country => country.id == id);
}

export function getAdministrativeAreaLevel1ByCountryId(state) {
  return id => {
    let result = [];
    state.administrative_area_level_1.forEach(value => {
      if (value.countryId === id) {
        result.push(value);
      }
    });
    return result;
  };
}

export function administrativeAreaLevel1(state) {
  return id =>
    state.administrative_area_level_1.find(
      administrative_area_level_1 => administrative_area_level_1.id === id
    );
}

export function getLocalityByAdministrativeAreaLevel1Id(state) {
  return id => {
    let result = [];
    state.locality.forEach(value => {
      if (value.administrativeAreaLevel1Id === id) {
        result.push(value);
      }
    });
    return result;
  };
}

export function locality(state) {
  return id => state.locality.find(locality => locality.id == id);
}

export function getSubLocalityLevel1ByLocalityId(state) {
  return id => {
    let result = [];
    state.sub_localities_level_1.forEach(value => {
      if (value.localityId === id) {
        result.push(value);
      }
    });
    return result;
  };
}

export function subLocalityLevel1(state) {
  return id =>
    state.sub_localities_level_1.find(
      sub_localities_level_1 => sub_localities_level_1.id == id
    );
}

export function subLocalityLevel2(state) {
  return id =>
    state.sub_localities_level_2.find(
      sub_localities_level_2 => sub_localities_level_2.id == id
    );
}
