<template>
  <v-toolbar max-height="64" dark flat color="primary">
    <v-app-bar-nav-icon
      class="hidden-md-and-up"
      @click="toggleDrawer"
    ></v-app-bar-nav-icon>
    <v-container py-0>
      <v-row align="center">
        <v-img
          :src="require('@/assets/white_logo_only.png')"
          class="mr-5"
          contain
          height="48"
          width="48"
          max-width="48"
          @click="$vuetify.goTo(0)"
        />
        <v-btn
          v-for="(link, i) in links"
          :key="i"
          :to="link.to"
          class="ml-0 hidden-sm-and-down colorPrimaryText&#45;&#45;text"
          text
          @click="onClick($event, link)"
        >
          {{ link.text }}
        </v-btn>
        <v-spacer />
        <v-btn
          v-if="!userIsAuthenticated"
          type="submit"
          color="secondary"
          router
          to="/login"
        >
          Login
        </v-btn>
        <profile-menu v-if="userIsAuthenticated"></profile-menu>
      </v-row>
    </v-container>
  </v-toolbar>
</template>

<script>
// Utilities
import { mapGetters, mapMutations } from "vuex";

export default {
  components: {
    ProfileMenu: () => import("@/components/toolbar/ProfileMenu"),
  },
  data() {
    return {};
  },
  computed: {
    links() {
      let links = [
        {
          text: "Home",
          to: "/",
        },
        {
          text: "About",
          href: "#about",
        },
        {
          text: "Directory",
          to: `/directory/trainer/0`,
        },
        {
          text: "Collections",
          to: `/contents/0`,
        },
      ];
      if (this.userIsAuthenticated) {
        const user = this.$store.getters["login/user"];
        links.push({
          text: "Profile",
          to: "/profile/" + user.id + "/details",
        });
      }
      return links;
    },
    userIsAuthenticated() {
      return (
        this.$store.getters["login/user"] !== null &&
        this.$store.getters["login/user"] !== undefined
      );
    },
  },

  methods: {
    ...mapMutations({
      toggleDrawer: "store/toggleDrawer",
    }),
    onClick(e, item) {
      e.stopPropagation();
      if (item.to || !item.href) {
        return;
      }
      this.$vuetify.goTo(item.href);
    },
  },
};
</script>
