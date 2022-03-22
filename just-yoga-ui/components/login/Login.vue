<template>
  <v-container>
    <v-form @submit.prevent="onSignin">
      <v-row>
        <v-col cols="12">
          <div id="firebaseui-auth-container" />
          <div id="loader">
            <v-progress-circular indeterminate color="green" />
          </div>
        </v-col>
        <v-col>
          <v-text-field
            id="loginEmail"
            v-model="loginEmail"
            flat
            class="ma-1"
            solo-inverted
            name="loginEmail"
            label="Email"
            type="email"
            required
          />
          <v-text-field
            id="loginPassword"
            v-model="loginPassword"
            class="ma-1"
            flat
            solo-inverted
            name="loginPassword"
            label="Password"
            type="password"
            required
          />
          <v-container pa-0>
            <p class="colorSecondaryText--text">
              Forgot Password?
            </p>
          </v-container>
          <v-btn
            type="submit"
            color="secondary"
            :loading="loading"
            :disabled="loading"
          >
            Login
          </v-btn>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import firebase from "firebase";
import "firebaseui/dist/firebaseui.css";
import { mapGetters } from "vuex";
export default {
  name: "Login",
  data: () => ({
    loginEmail: "",
    loginPassword: "",
  }),
  computed: {
    ...mapGetters({
      loading: "login/loading",
    }),
  },
  mounted() {
    const firebaseui = require("firebaseui");
    const uiConfig = {
      callbacks: {
        uiShown() {
          document.getElementById("loader").style.display = "none";
        },
      },
      signInSuccessUrl: "/",
      credentialHelper: firebaseui.auth.CredentialHelper.ACCOUNT_CHOOSER_COM,
      signInFlow: "popup",
      signInOptions: [firebase.auth.GoogleAuthProvider.PROVIDER_ID],
    };
    const ui = new firebaseui.auth.AuthUI(firebase.auth());
    ui.start("#firebaseui-auth-container", uiConfig);
  },
  /**
   * Called before destroying this component
   * un-subscribing from database listeners
   */
  beforeDestroy() {
    const firebaseui = require("firebaseui");
    const ui = firebaseui.auth.AuthUI.getInstance();
    if (ui) {
      ui.delete();
    }
  },
  methods: {
    onSignin() {
      this.$store
        .dispatch("login/signUserIn", {
          email: this.loginEmail,
          password: this.loginPassword,
        })
        .then((user) => {
          if (user.emailVerified) this.$router.push("/");
        });
    },
  },
};
</script>
