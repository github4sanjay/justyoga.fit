<template>
  <div>
    <div class="row">
      <div class="col-12">
        <div id="firebaseui-auth-container" />
        <div id="loader">
          <q-spinner-pie color="primary" size="2em" />
        </div>
      </div>
      <div class="col-12">
        <div class="col-12 q-mb-md" v-if="loginError">
          <q-banner inline-actions class="text-white bg-red">
            {{ loginError.message }}
            <template v-slot:action>
              <q-btn flat label="Dismiss" @click="onDismissedLoginError" />
            </template>
          </q-banner>
        </div>
      </div>
      <div class="col-12">
        <q-form @submit="onSignin" class="q-gutter-md">
          <q-input
            filled
            v-model="loginEmail"
            type="email"
            label="Your email *"
            lazy-rules
            :rules="[
              val => (val && val.length > 0) || 'Please type your email'
            ]"
          />

          <q-input
            filled
            :type="isPwd ? 'password' : 'text'"
            v-model="loginPassword"
            label="Your password *"
            lazy-rules
            :rules="[
              val => (val !== null && val !== '') || 'Password is required',
              val =>
                (val && val.length > 5) ||
                'Password should be greater than 6 character'
            ]"
          >
            <template v-slot:append>
              <q-icon
                :name="isPwd ? 'visibility_off' : 'visibility'"
                class="cursor-pointer"
                @click="isPwd = !isPwd"
              />
            </template>
          </q-input>

          <q-toggle v-model="accept" label="I accept the license and terms" />

          <div>
            <q-btn
              label="Login"
              type="submit"
              color="primary"
              :loading="loading"
            />
            <q-btn
              label="Forgot Password ?"
              color="primary"
              flat
              class="q-ml-sm"
              @click="forgotPasswordDialogue = true"
            />
          </div>
        </q-form>
      </div>
    </div>
    <q-dialog v-model="forgotPasswordDialogue" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">Your email address</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-input
            dense
            v-model="forgotPasswordForEmailAddress"
            autofocus
            @keyup.enter="forgotPasswordDialogue = false"
          />
        </q-card-section>

        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="Cancel" v-close-popup />
          <q-btn
            flat
            label="Reset Password"
            v-close-popup
            @click="forgetPassword()"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import firebase from "firebase";
import "firebaseui/dist/firebaseui.css";
import { mapGetters } from "vuex";
import NotifyUtil from "src/utils/NotifyUtil";
import { Platform } from "quasar";

export default {
  name: "Login",
  data: () => ({
    loginEmail: "",
    loginPassword: "",
    accept: false,
    isPwd: true,
    forgotPasswordDialogue: false,
    forgotPasswordForEmailAddress: null
  }),
  computed: {
    ...mapGetters({
      loading: "login/loading",
      loginError: "login/loginError"
    })
  },
  mounted() {
    const firebaseui = require("firebaseui");
    const uiConfig = {
      callbacks: {
        uiShown() {
          document.getElementById("loader").style.display = "none";
        }
      },
      signInSuccessUrl: "/",
      credentialHelper: firebaseui.auth.CredentialHelper.ACCOUNT_CHOOSER_COM,
      signInFlow: "popup",
      signInOptions: [firebase.auth.GoogleAuthProvider.PROVIDER_ID]
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
          password: this.loginPassword
        })
        .then(user => {
          if (user.emailVerified) this.$router.push("/");
        });
    },
    onDismissedLoginError() {
      this.$store.dispatch("login/clearLoginError");
    },
    forgetPassword() {
      if (this.forgotPasswordForEmailAddress) {
        this.$store.dispatch("login/forgetPassword", {
          email: this.forgotPasswordForEmailAddress
        });
      } else {
        NotifyUtil.showError("Email Address is required");
      }
    }
  }
};
</script>
