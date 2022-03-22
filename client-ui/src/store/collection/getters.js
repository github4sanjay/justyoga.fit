export function someGetter(/* state */) {}
export function byId(state) {
  return id => state.collections.find(collection => collection.id == id);
}

export function byPage(state) {
  return page =>
    state.collection_page.find(collection_page => collection_page.page == page);
}
