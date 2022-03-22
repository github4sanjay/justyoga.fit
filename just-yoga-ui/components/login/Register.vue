<template>
  <v-container>
    <v-form @submit.prevent="onRegister">
      <v-row>
        <v-col cols="12">
          <v-text-field
            id="email"
            v-model="email"
            flat
            class="ma-1"
            solo-inverted
            name="email"
            label="Email"
            type="email"
            required
          />
          <v-text-field
            id="password"
            v-model="password"
            class="ma-1"
            flat
            solo-inverted
            name="password"
            label="Password"
            type="password"
            required
          />
          <v-text-field
            id="confirmPassword"
            v-model="confirmPassword"
            class="ma-1"
            flat
            solo-inverted
            name="confirmPassword"
            label="Confirm Password"
            type="password"
            :rules="[comparePasswords]"
          />
          <v-btn
            color="secondary"
            type="submit"
            :loading="loading"
            :disabled="loading"
          >
            Register
          </v-btn>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
export default {
  name: "Register",
  data: () => ({
    email: "",
    password: "",
    confirmPassword: "",
    loading: false,
  }),
  computed: {
    comparePasswords() {
      return this.password !== this.confirmPassword
        ? "Passwords do not match"
        : true;
    },
  },
  methods: {
    onRegister() {
      this.loading = true;
      this.$store
        .dispatch("login/registerUser", {
          email: this.email,
          password: this.password,
        })
        .finally(() => {
          this.email = "";
          this.password = "";
          this.confirmPassword = "";
          this.loading = false;
        });
    },
  },
};
</script>

<style scoped>
</style>
