export function store(state, payload) {
  let present = state[payload.state].find(data => {
    return data.page === payload.page && data.id === payload.id;
  });
  if (present === null || present === undefined) {
    state[payload.state].push(payload);
  }
}
