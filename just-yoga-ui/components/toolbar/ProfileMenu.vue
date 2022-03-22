<template>
  <div class="text-xs-center" v-if="user">
    <v-menu
      bottom
      origin="center center"
      transition="scale-transition"
      :close-on-content-click="false"
      v-model="menu">
      <template v-slot:activator="{ on }">
        <v-avatar size="35" v-on="on" color="secondary">
          <v-img
            v-if="user.profilePic"
            :src="user.profilePic"
            :alt="user.name"
          />
          <span v-else-if="user.name" class="headline">
            {{ user.name.charAt(0) }}
          </span>
          <span v-else class="white--text headline">JY</span>
        </v-avatar>
      </template>
      <v-card
        max-width="344"
        class="mx-auto">
        <v-list-item>
          <v-list-item-avatar color="grey">
            <img :src="user.profilePic" :alt="user.name">
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="headline">{{user.name}}</v-list-item-title>
           <!-- <v-list-item-subtitle>Software Developer</v-list-item-subtitle>-->
          </v-list-item-content>
        </v-list-item>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            x-small
            text
            color="secondary"
            @click="onLogout">
            Logout
          </v-btn>
        </v-card-actions>
      </v-card>
      <!--<v-card>
        <v-list>
          <v-list-tile avatar>
            <v-list-tile-avatar>
              <img :src="user.profilePic" :alt="user.name">
            </v-list-tile-avatar>
            <v-list-tile-content>
              <v-list-tile-title>{{user.name}}</v-list-tile-title>
              <v-list-tile-sub-title>Software Developer</v-list-tile-sub-title>
            </v-list-tile-content>
            <v-list-tile-action>
              <v-btn
                icon
                :class="fav ? 'red&#45;&#45;text' : ''"
                @click="fav = !fav">
                <v-icon>favorite</v-icon>
              </v-btn>
            </v-list-tile-action>
          </v-list-tile>
        </v-list>
        <v-divider></v-divider>
        <v-layout class="text-xs-center justify-center">
          <v-btn v-if="isUserAdmin"
            router to="/adminDashboard"
            color="blue-grey"
            class="white&#45;&#45;text">
            Visit Admin Dashboard
            <v-icon right dark>dashboard</v-icon>
          </v-btn>
        </v-layout>
        <v-layout ml-3>
          <v-radio-group v-model="areaSelected" @change="onAreaChange">
            <span>Select range in which you want to explore?</span>
            <v-radio
              v-for="(area,index) in areas"
              :key="index"
              :label="area.name"
              :value="area.value"
            ></v-radio>
          </v-radio-group>
        </v-layout>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat @click="menu = false">Cancel</v-btn>
          <v-btn color="primary" flat @click="onLogout">Logout</v-btn>
        </v-card-actions>
      </v-card>-->
    </v-menu>
  </div>
</template>

<script>

export default {
  name: "ProfileMenu",
  data: () => ({
    fav: true,
    menu: false,
    message: false,
    hints: true,
    areaSelected: "geohash50",
    areas: [
      {
        name: "Within 1 kms",
        value: "geohash1"
      },
      {
        name: "Within 5 kms",
        value: "geohash5"
      },
      {
        name: "Within 50 kms",
        value: "geohash50"
      },
      {
        name: "Within 150 kms",
        value: "geohash150"
      }
    ]
  }),
  methods: {
    onLogout() {
      this.$store.dispatch("login/logout").then(() => {
        this.$router.push("/login");
        this.$cookies.set("firebase-user-token", null);
      });
    },
    onAreaChange() {
      this.$store.dispatch("shared/areaChange", { area: this.areaSelected });
    }
  },
  computed: {
    user() {
      return  this.$store.getters["login/user"];
    },
    isUserAdmin() {
      return this.$store.getters["login/isUserAdmin"];
    }
  },
  created: function() {
    this.areaSelected = this.$store.getters["shared/areaSelected"];
    console.log(this.areaSelected);
  }
};
</script>

<style scoped>
</style>
