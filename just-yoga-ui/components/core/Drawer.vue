<template>
  <v-navigation-drawer v-model="drawer" app dark temporary>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title class="title">
          justyoga.fit
        </v-list-item-title>
       <!-- <v-list-item-subtitle>
          Important Links
        </v-list-item-subtitle>-->
      </v-list-item-content>
    </v-list-item>
    <v-divider></v-divider>
    <v-list dense nav>
      <v-list-item
        v-for="item in items"
        :key="item.text"
        link
        :to="item.link"
        :href="item.href"
        @click="onClick($event, item)"
      >
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>{{ item.text }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
// Utilities
import { mapGetters, mapMutations } from "vuex";

export default {
  name: "CoreDrawer",
  computed: {
    ...mapGetters({
      links: "store/links",
    }),
    drawer: {
      get() {
        return this.$store.getters["store/drawer"];
      },
      set(val) {
        this.setDrawer(val);
      },
    },
    userIsAuthenticated() {
      return (
        this.$store.getters["login/user"] !== null &&
        this.$store.getters["login/user"] !== undefined
      );
    },
    items() {
      let items = [
        {
          icon: "home",
          text: "Home",
          link: "/",
        },
        {
          icon: "fa fa-folder",
          text: "Directory",
          link: "/directory/trainer/0",
        },
        {
          icon: "fa fa-compact-disc",
          text: "Collections",
          link: `/contents/0`,
        },
      ];
      if (!this.userIsAuthenticated) {
        items.unshift({
          icon: "account_box",
          text: "Login",
          link: "/login",
        });
      } else {
        const user = this.$store.getters["login/user"];
        items.push({
          icon: "person",
          text: "Profile",
          link: "/profile/" + user.id + "/details",
        });
        user.authorities.forEach((element) => {
          if (element.authority === "ROLE_ADMIN") {
            items.push({
              icon: "dashboard",
              text: "Admin",
              link: "/admin",
            });
          }
        });
      }
      return items;
    },
  },
  methods: {
    ...mapMutations({
      setDrawer: "store/setDrawer",
    }),
    onClick(e, item) {
      e.stopPropagation();
      if (item.to === "/") {
        this.$vuetify.goTo(0);
        this.setDrawer(false);
        return;
      }
      if (item.to || !item.href) {
        return;
      }
      this.$vuetify.goTo(item.href);
      this.setDrawer(false);
    },
  },
};
</script>
