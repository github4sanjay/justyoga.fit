export function findById(state) {
  return id => state.reviews.find(review => review.id == id);
}
