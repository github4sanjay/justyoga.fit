import Vue from "vue";
import axios from "axios";
import { Cookies } from "quasar";
import NotifyUtil from "src/utils/NotifyUtil";

Vue.mixin({
  beforeCreate() {
    const options = this.$options;
    if (options.axios) {
      this.$axios = options.axios;
    } else if (options.parent) {
      this.$axios = options.parent.$axios;
    }
  }
});

let instance = axios.create({
  //withCredentials: true
});

export default function({ app, ssrContext, router, store }) {
  let cookies = null;
  if (!process.env.SERVER) {
    cookies = Cookies;
  }
  instance.interceptors.request.use(
    config => {
      let token = cookies ? cookies.get("X_AUTHORIZATION_FIREBASE") : null;
      if (token) {
        config.headers["X-Authorization-Firebase"] = token;
      }
      return config;
    },
    error => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    response => {
      return response;
    },
    error => {
      const { status } = error.response || {};
      // switch (status) {
      //   case 400: break
      //   case 401: message = store.$t('http.unauthorized'); break
      //   case 403: message = store.$t('http.forbidden'); break
      //   case 500: message = store.$t('http.serverError'); break
      //   case 503: message = store.$t('http.serviceUnavailable'); break
      // }

      if (status === 401) {
        NotifyUtil.showError("Your session ended. Please login again.");
        store.dispatch("login/clearUser");
        cookies.remove("X_AUTHORIZATION_FIREBASE");
        router.push("/login");
      }

      return Promise.reject(error);
    }
  );

  Vue.prototype.$axios = instance;
}

export { instance };
