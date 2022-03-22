export function storeAllMedicalExpertise(state, payload) {
  payload.forEach(toSave => {
    let present = state.medicalExpertise.find(data => {
      return data.id === toSave.id;
    });
    if (present === null || present === undefined) {
      state.medicalExpertise.push(toSave);
    }
  });
}

export function storeAllYogaExpertise(state, payload) {
  payload.forEach(toSave => {
    let present = state.yogaExpertise.find(data => {
      return data.id === toSave.id;
    });
    if (present === null || present === undefined) {
      state.yogaExpertise.push(toSave);
    }
  });
}

export function storeAllYogaCertificates(state, payload) {
  payload.forEach(toSave => {
    let present = state.yogaCertificates.find(data => {
      return data.id === toSave.id;
    });
    if (present === null || present === undefined) {
      state.yogaCertificates.push(toSave);
    }
  });
}
