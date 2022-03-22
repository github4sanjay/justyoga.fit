import { instance } from "boot/axios";
import ApiEndpoints from "src/constants/ApiEndpoints";
import Firebase from "firebase/app";
import "firebase/auth";
import NotifyUtil from "src/utils/NotifyUtil";
import { Cookies } from "quasar";

export function clearUser({ commit }) {
  commit("clearUser");
}

export function setUser({ commit }, payload) {
  commit("setUpdatedUser", payload);
}

export function setLoginError({ commit }, payload) {
  commit("setLoginError", payload);
}

export function signUserIn({ commit, state }, payload) {
  return new Promise(async (resolve, reject) => {
    commit("setLoading", true);
    commit("clearLoginError");
    try {
      let user = await Firebase.auth().signInWithEmailAndPassword(
        payload.email,
        payload.password
      );
      resolve(user);
    } catch (error) {
      commit("setLoginError", error);
      reject();
    } finally {
      commit("setLoading", false);
    }
  });
}

export function clearLoginError({ commit }) {
  commit("clearLoginError");
}

export function autoSignIn({ commit, state }, payload) {
  commit("setLoading", false);
  commit("setUser", payload);
  //afterSignInUserSpecificTask(commit, state, payload)
}

export function setServiceWorkerRegistered({ commit }, payload) {
  commit("setServiceWorkerRegistered", payload);
}

export function setIsUserAdmin({ commit }, payload) {
  commit("setIsUserAdmin", payload);
}

export async function logout({ commit }) {
  await Firebase.auth().signOut();
  // const logoutConfigData = {
  //   url: ApiEndpoints.LOGOUT,
  //   method: "post",
  //   withCredentials: true
  // };
  // await instance.request(logoutConfigData).catch(function(error) {
  //   if (error.response) {
  //     console.log(error.response.data);
  //   }
  // });
  Cookies.remove("X_AUTHORIZATION_FIREBASE");
  commit("setUser", null);
}

export function registerUser({ commit, dispatch }, payload) {
  return new Promise((resolve, reject) => {
    Firebase.auth()
      .createUserWithEmailAndPassword(payload.email, payload.password)
      .then(() => {
        Firebase.auth()
          .currentUser.sendEmailVerification()
          .then(function() {
            NotifyUtil.showSuccess(
              "Verification email has been sent to the your email address. " +
                "Please click on the link to login."
            );
            resolve();
          })
          .catch(function(error) {
            NotifyUtil.showError("Something went wrong");
            reject();
          });
      })
      .catch(function(error) {
        if (error && error.code === "auth/email-already-in-use") {
          NotifyUtil.showError(
            "The email address is already in use by another account."
          );
        } else {
          NotifyUtil.showError("Something went wrong");
        }
        reject();
      });
  });
}

export function forgetPassword({ commit, dispatch }, payload) {
  return new Promise((resolve, reject) => {
    Firebase.auth()
      .sendPasswordResetEmail(payload.email)
      .then(() => {
        NotifyUtil.showSuccess("Password reset email sent, check your inbox.");
        resolve();
      })
      .catch(function(error) {
        if (error && error.code === "auth/user-not-found") {
          NotifyUtil.showError(
            "There is no user record corresponding to this email."
          );
        } else if (error && error.code === "auth/invalid-email") {
          NotifyUtil.showError("The email address is badly formatted.");
        } else {
          NotifyUtil.showError("Something went wrong");
        }
        reject();
      });
  });
}

export function updateCoverPic({ commit }, payload) {
  commit("updateCoverPic", payload);
}
export function updateProfilePic({ commit }, payload) {
  commit("updateProfilePic", payload);
}
