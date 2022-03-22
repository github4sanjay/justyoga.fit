export function setLoading(state, payload) {
  state.loading = payload;
}
export function setError(state, payload) {
  state.error = payload;
}
export function clearError(state) {
  state.error = null;
}
export function setAreaSelected(state, payload) {
  state.areaSelected = payload.area;
}
export function setDrawerState(state, payload) {
  state.drawerState = payload;
}
export function toggleDrawerState(state) {
  state.drawerState = !state.drawerState;
}
export function setServiceAvailable(state, payload) {
  state.isServiceAvailable = payload;
}
export function setErrorText(state, payload) {
  state.errorText = payload;
}
export function setErrorSnackbar(state, payload) {
  state.errorSnackbar = payload;
}
export function setSuccessText(state, payload) {
  state.successText = payload;
}
export function setSuccessSnackbar(state, payload) {
  state.successSnackbar = payload;
}
export function setDialog(state, payload) {
  state.dialog = payload;
}
export function setDialogText(state, payload) {
  state.dialogText = payload;
}
export function setLoginDialog(state, payload) {
  state.loginDialog = payload;
}
