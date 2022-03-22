<template>
  <v-tabs
    v-if="!user"
    centered
    show-arrows
    background-color="primaryLight"
    slider-size="8">
    <v-tabs-slider color="primary" />
    <v-tab>Login</v-tab>
    <v-tab>Register</v-tab>
      <v-tab-item>
        <v-card color="colorPrimaryText">
          <v-container>
            <v-row v-if="loginError" justify="center">
                <app-alert-error
                  :text="loginError.message"
                  @dismissed="onDismissedLoginError"/>
            </v-row>
            <v-row>
              <v-col>
                <v-card class="colorPrimaryText colorSecondaryText--text" flat>
                  <v-card
                    class="pa-2 colorSecondaryText--text"
                    flat
                    tile>
                    <div class="display-1">Welcome back!</div>
                  </v-card>
                  <v-card
                    class="pa-2 colorSecondaryText--text"
                    flat
                    tile>
                    <div>We are glad to see you again, enjoy your only digital yoga networking.</div>
                  </v-card>
                  <v-card-text>
                    <Login />
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card class="colorPrimaryText">
          <v-container>
            <v-row v-if="error" row>
                <app-alert-error :text="error.message" @dismissed="onDismissed" />
            </v-row>
            <v-row>
              <v-col>
                <v-card class="colorPrimaryText colorSecondaryText--text" flat>
                  <v-card
                    class="pa-2 colorSecondaryText--text"
                    flat
                    tile>
                    <div class="display-1">Register Now!</div>
                  </v-card>
                  <v-card
                    class="pa-2 colorSecondaryText--text"
                    flat
                    tile>
                    <div>Yoga Networking - Find And Advertise Yoga Instructor Here Location Wise</div>
                  </v-card>
                  <v-card-text>
                      <Register />
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-tab-item>
    <!--<v-spacer></v-spacer>-->
    <!--<v-btn flat @click.stop="closeDialogue">Close</v-btn>-->
  </v-tabs>
</template>

<script>
import Login from './Login'
import Register from './Register'
export default {
  name: 'LoginAndRegister',
  components: { Register, Login },
  computed: {
    error () {
      return this.$store.getters['shared/error']
    },
    loginError () {
      return this.$store.getters['login/loginError']
    },
    user () {
      const user = this.$store.getters['login/user']
      if (user != null) {
        this.$router.push('/')
      }
      return user
    }
  },
  created () {},
  methods: {
    onDismissed () {
      this.$store.dispatch('shared/clearError')
    },
    onDismissedLoginError () {
      this.$store.dispatch('login/clearLoginError')
    },
    closeDialogue () {
      this.$emit('closeDialogue')
    }
  }
}
</script>

<style scoped>
</style>
