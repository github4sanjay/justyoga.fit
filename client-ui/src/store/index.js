import Vue from "vue";
import Vuex from "vuex";

import collection from "./collection";
import directory from "./directory";
import directory_images from "./directory_images";
import directory_videos from "./directory_videos";
import directory_blogs from "./directory_blogs";
import expertise from "./expertise";
import images from "./images";
import login from "./login";
import place from "./place";
import profile_review from "./profile_review";
import profile_blog from "./profile_blog";
import reviews from "./reviews";
import shared from "./shared";
import users from "./users";
import videos from "./videos";
import blogs from "./blogs";

Vue.use(Vuex);

/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Store instance.
 */

export default function(/* { ssrContext } */) {
  const Store = new Vuex.Store({
    modules: {
      collection,
      directory,
      directory_images,
      directory_videos,
      directory_blogs,
      expertise,
      images,
      login,
      place,
      profile_review,
      profile_blog,
      reviews,
      shared,
      users,
      videos,
      blogs
    },

    // enable strict mode (adds overhead!)
    // for dev mode only
    strict: process.env.DEV
  });

  return Store;
}
