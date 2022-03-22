export function loginError(state) {
  return state.loginError;
}
export function user(state) {
  return state.user;
}
export function loading(state) {
  return state.loading;
}
export function isUserAdmin(state) {
  let isAdmin = false;
  if (!state.user) return isAdmin;
  for (let i = 0; i < state.user.authorities.length; i++) {
    if (state.user.authorities[i].authority === "ROLE_ADMIN") {
      isAdmin = true;
      break;
    }
  }
  return isAdmin;
}
