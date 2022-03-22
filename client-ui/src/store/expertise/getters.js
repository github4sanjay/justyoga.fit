export function medicalExpertiseById(state) {
  return id =>
    state.medicalExpertise.find(medicalExpertise => medicalExpertise.id == id);
}

export function yogaExpertiseById(state) {
  return id =>
    state.yogaExpertise.find(yogaExpertise => yogaExpertise.id == id);
}

export function yogaCertificateById(state) {
  return id =>
    state.yogaCertificates.find(yogaCertificate => yogaCertificate.id == id);
}

export function medicalExpertise(state) {
  return state.medicalExpertise;
}

export function yogaExpertise(state) {
  return state.yogaExpertise;
}

export function yogaCertificates(state) {
  return state.yogaCertificates;
}
