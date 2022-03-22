import firebase from "firebase";
import axios from "axios";
import ApiEndpoints from "@/constants/ApiEndpoints";
import { auth } from "@/services/firebaseInit.js";

export default ({ store, redirect, isServer, app, error }) => {
  return new Promise((resolve) => {
    auth.onAuthStateChanged(async (user) => {
      store.dispatch("login/setServiceWorkerRegistered", false); // Set service worker registered false as browser refresh
      if (user) {
        if (!user.emailVerified) {
          await auth.signOut();
          store.dispatch("login/setLoginError", {
            message: "Email not verified",
          });
          return;
        }
        store.dispatch("shared/setDialogText", "Signing In");
        store.dispatch("shared/setDialog", true);
        const idToken = await firebase.auth().currentUser.getIdToken(true);
        const singInConfigData = {
          url: ApiEndpoints.SIGN_IN,
          method: "post",
          headers: { id_token: idToken },
          withCredentials: true,
        };
        axios
          .request(singInConfigData)
          .then(() => {
            const userConfigData = {
              url: ApiEndpoints.USERS,
              method: "get",
              withCredentials: true,
            };
            axios
              .request(userConfigData)
              .then((userResponse) => {
                if (userResponse) {
                  store.dispatch("login/autoSignIn", userResponse.data.data);
                }
              })
              .catch(function (e) {
                if (e.response) {
                  console.log("fireauth "+e.response.data);
                  error({
                    statusCode: e.response.status,
                  });
                }
              })
              .finally(() => {
                store.dispatch("shared/setDialog", false);
                store.dispatch("shared/setDialogText", "");
              });
          })
          .catch(function (error) {
            store.dispatch("shared/setDialog", false);
            store.dispatch("shared/setDialogText", "");
          });
      }
      resolve();
    });
  });
};
