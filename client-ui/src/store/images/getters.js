export function byId(state) {
  return id => state.images.find(image => image.id == id);
}

export function byPageAndUserId(state) {
  return (userId, page) =>
    state.profile_images.find(
      profile_image =>
        profile_image.page == page && profile_image.userId == userId
    );
}
