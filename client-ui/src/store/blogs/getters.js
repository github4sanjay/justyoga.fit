export function findById(state) {
  return id => state.blogs.find(blog => blog.id == id);
}
