export function storeAllPlace(state, payload) {
  state.country.push(...payload.country);
  state.administrative_area_level_1.push(
    ...payload.administrative_area_level_1
  );
  state.locality.push(...payload.locality);
  state.sub_localities_level_1.push(...payload.sub_localities_level_1);
  state.sub_localities_level_2.push(...payload.sub_localities_level_2);
}

export function storeCountry(state, payload) {
  let presentCountry = state.country.find(country => {
    return country.id === payload.id;
  });
  if (presentCountry === null || presentCountry === undefined) {
    state.country.push(payload);
  }
}

export function storeAdministrativeAreaLevel1(state, payload) {
  let presentCountry = state.administrative_area_level_1.find(country => {
    return country.id === payload.id;
  });
  if (presentCountry === null || presentCountry === undefined) {
    state.administrative_area_level_1.push(payload);
  }
}

export function storeLocality(state, payload) {
  let presentCountry = state.locality.find(country => {
    return country.id === payload.id;
  });
  if (presentCountry === null || presentCountry === undefined) {
    state.locality.push(payload);
  }
}

export function storeSubLocalitiesLevel1(state, payload) {
  let presentCountry = state.sub_localities_level_1.find(country => {
    return country.id === payload.id;
  });
  if (presentCountry === null || presentCountry === undefined) {
    state.sub_localities_level_1.push(payload);
  }
}

export function storeSubLocalitiesLevel2(state, payload) {
  let presentCountry = state.sub_localities_level_2.find(country => {
    return country.id === payload.id;
  });
  if (presentCountry === null || presentCountry === undefined) {
    state.sub_localities_level_2.push(payload);
  }
}
