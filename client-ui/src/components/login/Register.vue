<template>
  <div>
    <div class="row">
      <div class="col-12">
        <div class="col-12 q-mb-md" v-if="error">
          <q-banner inline-actions class="text-white bg-red">
            {{ error }}
            <template v-slot:action>
              <q-btn flat label="Dismiss" @click="onDismissed" />
            </template>
          </q-banner>
        </div>
      </div>
      <div class="col-12">
        <q-form @submit="onRegister" class="q-gutter-md">
          <q-input
            filled
            v-model="email"
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
            v-model="password"
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

          <q-input
            filled
            :type="isPwd ? 'password' : 'text'"
            v-model="confirmPassword"
            label="Confirm password *"
            lazy-rules
            :rules="[
              val => (val !== null && val !== '') || 'Password is required',
              val =>
                (val && val.length > 5) ||
                'Password should be greater than 6 character',
              val =>
                (val !== null && val === password) || 'Password not matched'
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
              label="Register"
              type="submit"
              color="primary"
              :loading="loading"
            />
          </div>
        </q-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data: () => ({
    email: "",
    password: "",
    confirmPassword: "",
    loading: false,
    accept: false,
    isPwd: true
  }),
  computed: {
    error() {
      return this.$store.getters["shared/errorText"];
    }
  },
  methods: {
    onRegister() {
      this.loading = true;
      this.$store
        .dispatch("login/registerUser", {
          email: this.email,
          password: this.password
        })
        .then(() => {
          this.email = "";
          this.password = "";
          this.confirmPassword = "";
        })
        .finally(() => {
          this.loading = false;
        });
    },
    onDismissed() {
      this.$store.dispatch("shared/setErrorText", null);
    }
  }
};
</script>

<style scoped></style>
