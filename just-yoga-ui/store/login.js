import { auth } from "@/services/firebaseInit.js";
import db from "../services/firebaseInit";
import axios from "axios";
import ApiEndpoints from "@/constants/ApiEndpoints";

export default {
  namespaced: true,
  state: () => ({
    user: null,
    loginError: null,
    serviceWorkerRegistered: false,
    loading: false,
    isUserAdmin: false,
  }),

  mutations: {
    clearUser(state) {
      state.user = null;
    },
    setUpdatedUser(state, payload) {
      state.user = payload.data;
    },
    setUser(state, payload) {
      state.user = payload;
    },
    setLoginError(state, payload) {
      state.loginError = payload;
    },
    clearLoginError(state) {
      state.loginError = null;
    },
    setServiceWorkerRegistered(state, payload) {
      state.serviceWorkerRegistered = payload;
    },
    setLoading(state, payload) {
      state.loading = payload;
    },
    updateCoverPic(state, payload) {
      state.user.coverPic = payload;
    },
    updateProfilePic(state, payload) {
      state.user.profilePic = payload;
    },
  },
  actions: {
    clearUser({ commit }) {
      commit("clearUser");
    },

    setUser({ commit }, payload) {
      commit("setUpdatedUser", payload);
    },

    setLoginError({ commit }, payload) {
      commit("setLoginError", payload);
    },

    signUserIn({ commit, state }, payload) {
      return new Promise(async (resolve, reject) => {
        commit("setLoading", true);
        commit("clearLoginError");
        try {
          let user = await auth.signInWithEmailAndPassword(
            payload.email,
            payload.password
          );
          resolve(user);
        } catch (error) {
          commit("setLoginError", error);
          reject();
        }finally {
          commit("setLoading", false);
        }
      });
    },

    clearLoginError({ commit }) {
      commit("clearLoginError");
    },

    autoSignIn({ commit, state }, payload) {
      commit("setLoading", false);
      commit("setUser", payload);
      //afterSignInUserSpecificTask(commit, state, payload)
    },

    setServiceWorkerRegistered({ commit }, payload) {
      commit("setServiceWorkerRegistered", payload);
    },

    setIsUserAdmin({ commit }, payload) {
      commit("setIsUserAdmin", payload);
    },

    async logout({ commit }) {
      await auth.signOut();
      const logoutConfigData = {
        url: ApiEndpoints.LOGOUT,
        method: "post",
        withCredentials: true,
      };
      await axios.request(logoutConfigData).catch(function (error) {
        if (error.response) {
          console.log(error.response.data);
        }
      });
      commit("setUser", null);
    },

    registerUser({ commit, dispatch }, payload) {
      return new Promise( (resolve, reject) => {
        auth
          .createUserWithEmailAndPassword(payload.email, payload.password)
          .then(() => {
            auth.currentUser
              .sendEmailVerification()
              .then(function () {
                dispatch(
                  "shared/setSuccessText",
                  "Verification email has been sent to the your email address. " +
                  "Please click on the link to login.",
                  {
                    root: true,
                  }
                );
                dispatch("shared/setSuccessSnackbar", true, { root: true });
                resolve();
              })
              .catch(function (error) {
                dispatch("shared/setErrorText", "Something went wrong", {
                  root: true,
                });
                dispatch("shared/setErrorSnackbar", true, { root: true });
                reject()
              });
          })
          .catch(function (error) {
            dispatch("shared/setErrorText", error.message, {
              root: true,
            });
            dispatch("shared/setErrorSnackbar", true, { root: true });
            reject()
          });
      });
    },

    updateCoverPic({ commit }, payload) {
      commit("updateCoverPic", payload);
    },
    updateProfilePic({ commit }, payload) {
      commit("updateProfilePic", payload);
    },
  },
  getters: {
    loginError(state) {
      return state.loginError;
    },
    user(state) {
      return state.user;
    },
    loading(state) {
      return state.loading;
    },
    isUserAdmin(state) {
      let isAdmin = false;
      if (!state.user) return isAdmin;
      for (let i=0;i<state.user.authorities.length;i++){
        if (state.user.authorities[i].authority === "ROLE_ADMIN") {
          isAdmin = true;
          break;
        }
      }
      return isAdmin;
    },
  },
};

/*
export const getters = {
  loginError(state) {
    return state.loginError;
  },
  user(state) {
    return state.user;
  },
  loading(state) {
    return state.loading;
  },
  isUserAdmin(state) {
    return state.isUserAdmin;
  },
};
*/

function afterSignInUserSpecificTask(commit, state, user) {
  /**
   * Build notification service
   * 1. Request Permission
   * 2. Save token in db
   * @type {firebase.messaging.Messaging | *}
   */
  const FIREBASE_MESSAGING = firebase.messaging();
  console.log(
    "service worker registered",
    "==>",
    state.serviceWorkerRegistered
  );
  if (!state.serviceWorkerRegistered) {
    navigator.serviceWorker
      .register("/js/firebase-messaging-sw.js")
      .then((registration) => {
        FIREBASE_MESSAGING.useServiceWorker(registration);
        commit("setServiceWorkerRegistered", true);
        FIREBASE_MESSAGING.requestPermission().then(() => {
          FIREBASE_MESSAGING.getToken().then((token) => {
            db.collection("notification_tokens")
              .doc(user.uid)
              .set({
                token: token,
                uid: user.uid,
              })
              .then(() => {})
              .catch((err) => {
                console.log(
                  "Shared/index.js error in registering service worker",
                  err
                );
              });
            console.log(`The token is ${token}`);
          });
        });
      })
      .catch((err) => {
        console.log(err);
      });
  } else {
    FIREBASE_MESSAGING.getToken().then((token) => {
      db.collection("notification_tokens")
        .doc(user.uid)
        .set({
          token: token,
          uid: user.uid,
        })
        .then(() => {})
        .catch((err) => {
          console.log(
            "Shared/index.js error in registering service worker",
            err
          );
        });
      console.log(`The token is ${token}`);
    });
  }

  /**
   * Check user is admin or not
   * 1. if admin set in store admin field true
   * 2. otherwise false
   *
   */
  commit("setIsUserAdmin", user);
}
