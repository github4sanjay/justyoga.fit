export function byId(state) {
  return id => state.videos.find(video => video.id == id);
}

export function byPageAndUserId(state) {
  return (userId, page) =>
    state.profile_videos.find(
      profile_video =>
        profile_video.page == page && profile_video.userId == userId
    );
}
