<template>
  <v-container class="primary pa-0 mt-10">
    <v-row
      color="primary"
      no-gutters>
      <v-col sm="12" md="6">
        <v-card
          color="primary"
          flat
          class="pa-5 text-center justify-center align-center">
          <v-row class="mt-3">
            <h2 class="section-title">
              JustYoga is the <span>next generation</span> digital yoga platform
            </h2>
          </v-row>
          <v-row class="mt-10">
            <p class="section-intro colorPrimaryText--text">
              We are transforming the way end users and yoga
              trainers used to interact with each other by creating a
              digital ecosystem where they can interact with each other directly.
            </p>
          </v-row>
          <v-row class="mt-10" justify="center">
            <h2 class="section-title">
              Join Our Community Now
            </h2>
          </v-row>
          <v-row class="mt-10" justify="center">
              <img
                height="200"
                style="cursor : pointer"
                :src="require('@/assets/white_logo.png')"
                class="text-xs-center"
              >
            </v-row>
        </v-card>
      </v-col>
      <v-col xs="12" md="6">
        <LoginAndRegister />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import images from '@/constants/images'
import LoginAndRegister from '@/components/login/LoginAndRegister'

export default {
  name: 'Login',
  components: { LoginAndRegister },
  data: () => ({
    LOGIN_BANNER_2: images.LOGIN_BANNER_2,
    LAZY_LOADING_IMAGE: images.LAZY_LOADING_IMAGE
  })
}
</script>

<style scoped>
.section-title {
  letter-spacing: 0.2vh;
  font-weight: 400;
  font-size: 3vh;
  color: white;
}
.section-title span {
  font-weight: 400;
}
.section-intro {
  font-size: 2vh;
  line-height: 2.1vh;
  font-weight: 400;
  letter-spacing: 0.1vh;
}
</style>
