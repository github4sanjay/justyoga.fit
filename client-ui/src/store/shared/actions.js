export function clearError({ commit }) {
  commit("clearError");
}
export function areaChange({ commit, getters }, payload) {
  commit("setAreaSelected", payload);
}
export function toggleDrawerState({ commit }) {
  commit("toggleDrawerState");
}
export function setDrawerState({ commit }, payload) {
  commit("setDrawerState", payload);
}
export function isServiceAvailable({ commit }, payload) {
  commit("setServiceAvailable", payload);
}
export function setErrorText({ commit }, payload) {
  commit("setErrorText", payload);
}
export function setErrorSnackbar({ commit }, payload) {
  commit("setErrorSnackbar", payload);
}
export function setSuccessText({ commit }, payload) {
  commit("setSuccessText", payload);
}
export function setSuccessSnackbar({ commit }, payload) {
  commit("setSuccessSnackbar", payload);
}
export function setDialog({ commit }, payload) {
  commit("setDialog", payload);
}
export function setDialogText({ commit }, payload) {
  commit("setDialogText", payload);
}
export function setLoginDialog({ commit }, payload) {
  commit("setLoginDialog", payload);
}
