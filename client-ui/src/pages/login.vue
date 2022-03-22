<template>
  <q-page
    padding
    style="background-image: linear-gradient(#0e5f8f, #1d1d1d);"
    :style-fn="myTweak"
  >
    <div class="text-white q-mt-lg">
      <div class="row justify-center">
        <div class="col-12 col-sm-6 col-md-6 col-lg-5" v-show="$q.screen.gt.sm">
          <q-img
            src="~assets/3768606.jpg"
            style="min-height:600px"
            spinner-color="white"
          />
        </div>
        <div
          :class="
            `col-12 col-sm-6 col-md-5 col-lg-4 bg-${
              this.$q.dark.isActive ? 'dark' : 'white'
            }`
          "
        >
          <LoginAndRegister />
        </div>
      </div>
    </div>
  </q-page>
</template>

<script>
import LoginAndRegister from "components/login/LoginAndRegister";
import Login from "components/login/Login";
import Register from "components/login/Register";

export default {
  name: "login",
  components: {
    LoginAndRegister
    // Login,
    // Register
  },
  methods: {
    myTweak(offset) {
      // "offset" is a Number (pixels) that refers to the total
      // height of header + footer that occupies on screen,
      // based on the QLayout "view" prop configuration
      // this is actually what the default style-fn does in Quasar
      return { minHeight: offset ? `calc(90vh - ${offset}px)` : "100vh" };
    }
  }
};
</script>
