export function byPageAndUserId(state) {
  return (userId, page) =>
    state.profile_blogs.find(
      profile_blog => profile_blog.page == page && profile_blog.userId == userId
    );
}
