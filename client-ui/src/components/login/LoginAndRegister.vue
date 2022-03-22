<template>
  <div>
    <q-card square flat>
      <q-tabs
        v-model="tab"
        inline-label
        :class="{
          'bg-grey-3': !this.$q.dark.isActive,
          'text-grey': !this.$q.dark.isActive,
          'bg-blue-grey-10': this.$q.dark.isActive
        }"
        active-color="primary"
        indicator-color="primary"
        :breakpoint="0"
        align="justify"
      >
        <q-tab name="login" label="Login" />
        <q-tab name="register" label="Register" />
      </q-tabs>

      <q-tab-panels v-model="tab" animated>
        <q-tab-panel name="login">
          <div class="row text-grey">
            <div class="col-12">
              <div>
                <div class="text-h6 text-weight-bold">
                  Welcome back!
                </div>
                <div class="text-overline q-mb-md">
                  We are glad to see you again, enjoy your only digital yoga
                  networking.
                </div>
                <div>
                  <Login />
                </div>
              </div>
            </div>
          </div>
        </q-tab-panel>
        <q-tab-panel name="register">
          <div class="row text-grey">
            <div class="col-12 full-height">
              <div>
                <div class="text-h6 text-weight-bold">Register Now!</div>
                <div class="text-overline q-mb-md">
                  Yoga Networking - Find And Advertise Yoga Instructor Here
                  Location Wise
                </div>
                <div>
                  <Register />
                </div>
              </div>
            </div>
          </div>
        </q-tab-panel>
      </q-tab-panels>
    </q-card>
  </div>
</template>

<script>
import Login from "./Login";
import Register from "./Register";
export default {
  name: "LoginAndRegister",
  components: { Login, Register },
  data() {
    return {
      tab: "login"
    };
  },
  computed: {
    user() {
      return this.$store.getters["login/user"];
    }
  },
  watch: {
    user(val) {
      if (val != null) {
        this.$router.push("/");
      }
    }
  },
  created() {},
  methods: {}
};
</script>

<style scoped></style>
