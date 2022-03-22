import Firebase from "firebase/app";
import "firebase/auth";
import { instance } from "boot/axios";
import ApiEndpoints from "../constants/ApiEndpoints";
import { Loading } from "quasar";
import { Cookies } from "quasar";

export default ({ store, redirect }) => {
  return new Promise(resolve => {
    Firebase.auth().onAuthStateChanged(async user => {
      store.dispatch("login/setServiceWorkerRegistered", false); // Set service worker registered false as browser refresh
      if (user) {
        if (!user.emailVerified) {
          await Firebase.auth().signOut();
          store.dispatch("login/setLoginError", {
            message: "Email not verified"
          });
          return;
        }
        Loading.show({
          message:
            'Singing <b>process</b> is in progress.<br/><span class="text-primary">Hang on...</span>'
        });
        const idToken = await Firebase.auth().currentUser.getIdToken(true);
        const singInConfigData = {
          url: ApiEndpoints.SIGN_IN,
          method: "post",
          headers: { "X-ID-TOKEN": idToken },
          withCredentials: true
        };
        instance
          .request(singInConfigData)
          .then(signInResponse => {
            Cookies.set("X_AUTHORIZATION_FIREBASE", signInResponse.data.data);
            const userConfigData = {
              url: ApiEndpoints.USERS,
              method: "get",
              withCredentials: true
            };
            instance
              .request(userConfigData)
              .then(userResponse => {
                if (userResponse) {
                  store.dispatch("login/autoSignIn", userResponse.data.data);
                }
              })
              .catch(function(e) {
                if (e.response) {
                  redirect("/error404");
                }
              })
              .finally(() => {
                Loading.hide();
              });
          })
          .catch(function(error) {
            Loading.hide();
          });
      }
      resolve();
    });
  });
};
