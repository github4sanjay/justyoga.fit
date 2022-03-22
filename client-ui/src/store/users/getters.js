export function user(state) {
  return id => state.users.find(user => user.id == id);
}
