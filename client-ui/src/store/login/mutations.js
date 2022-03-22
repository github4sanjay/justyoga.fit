export function clearUser(state) {
  state.user = null;
}
export function setUpdatedUser(state, payload) {
  state.user = payload.data;
}
export function setUser(state, payload) {
  state.user = payload;
}
export function setLoginError(state, payload) {
  state.loginError = payload;
}
export function clearLoginError(state) {
  state.loginError = null;
}
export function setServiceWorkerRegistered(state, payload) {
  state.serviceWorkerRegistered = payload;
}
export function setLoading(state, payload) {
  state.loading = payload;
}
export function updateCoverPic(state, payload) {
  state.user.coverPic = payload;
}
export function updateProfilePic(state, payload) {
  state.user.profilePic = payload;
}
