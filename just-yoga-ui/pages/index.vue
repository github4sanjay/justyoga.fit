<template>
  <v-container>
    <v-img
      gradient="rgba(0, 0, 0, .42), rgba(0, 0, 0, .42)"
      :src="require('@/assets/articles/yoga-costa-rica.jpg')"
      :aspect-ratio="12 / 4">
      <v-container fill-height class="align-center">
        <v-row row wrap fill-height align="center">
          <v-col
            sm="6"
            offset-sm="3"
            xs="10"
            offset-xs="1"
            class="justify-center text-center">
            <v-container class="pa-2 colorPrimaryText--text">
              <div
                :class="{
                  'justify-center': true,
                  'display-1 font-weight-bold': $vuetify.breakpoint.mdAndUp,
                  'headline font-weight-medium': $vuetify.breakpoint.smAndDown
                }">
                justyoga.fit
              </div>
            </v-container>
            <v-container class="pa-0 colorPrimaryText--text">
              <div
                :class="{
                  'justify-center': true,
                  'title font-weight-medium': $vuetify.breakpoint.mdAndUp,
                  'headline font-weight-medium': $vuetify.breakpoint.smAndDown
                }">
                Finding Peace, Serenity And Happiness Here
              </div>
            </v-container>
            <!--<v-autocomplete
              small-chips
              clearable
              hide-details
              hide-selected
              item-text="name"
              item-value="symbol"
              label="Search"
              solo/>-->
            <v-btn
              :large="$vuetify.breakpoint.mdAndUp"
              dark
              class="mt-4 red darken-4"
              router
              :to="`/directory/trainer/0`">
              Explore Yoga Trainers, Blogs, Videos and Images
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-img>
    <v-row align="center" justify="center">
      <v-col xs="12">
        <v-card class="text-center align-center justify-center" color="primary">
          <v-card-text class="headline white--text">
            Are you a yoga instructor?
            <v-btn dark small text>
              Register here so that people can find you
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

   <!-- <v-row align="center" justify="center">
      <v-col xs="10">
        <p class="headline">
          Top Yoga Collections
        </p>
      </v-col>
      <v-col xs="2">
        <v-row justify="end" align="center">
          <v-btn small color="primary" class="mr-3">
            Explore more
          </v-btn>
        </v-row>
      </v-col>
    </v-row>-->

    <collections/>

    <about />

    <subscribe />

    <social />
  </v-container>
</template>

<script>
export default {
  name: "Home",
  middleware: "content",
  components: {
    About: () => import("@/components/home/About"),
    Collections: () => import("@/components/home/Collections"),
    Banner: () => import('@/components/home/Banner'),
    Social: () => import("@/components/home/Social"),
    Subscribe: () => import("@/components/home/Subscribe")
  }
};
</script>
