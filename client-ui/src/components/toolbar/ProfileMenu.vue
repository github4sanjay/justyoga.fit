<template>
  <div v-if="user">
    <q-avatar size="40px" class="bg-accent">
      <user-image :user="user" />
      <q-menu>
        <div class="row no-wrap q-pa-md">
          <div class="column">
            <div class="text-h6 q-mb-md">Settings</div>
            <q-toggle v-model="darkTheme" color="green" label="Dark Theme" />
          </div>

          <q-separator vertical inset class="q-mx-lg" />

          <div class="column items-center">
            <q-avatar size="72px" class="bg-secondary text-white">
              <user-image :user="user" />
            </q-avatar>

            <div class="text-subtitle1 q-mt-md q-mb-xs">{{ user.name }}</div>

            <q-btn
              color="primary"
              label="Logout"
              push
              size="sm"
              v-close-popup
              @click="onLogout()"
            />
          </div>
        </div>
      </q-menu>
    </q-avatar>
  </div>
</template>

<script>
import UserImage from "components/base/UserImage";
export default {
  name: "ProfileMenu",
  components: { UserImage },
  data() {
    return {
      darkTheme: false,
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
    };
  },
  watch: {
    darkTheme(val) {
      if (val) {
        this.$q.dark.set(true);
      } else {
        this.$q.dark.set(false);
      }
    }
  },
  methods: {
    onLogout() {
      this.$store.dispatch("login/logout").then(() => {
        this.$router.push("/login");
      });
    },
    onAreaChange() {
      this.$store.dispatch("shared/areaChange", { area: this.areaSelected });
    }
  },
  computed: {
    user() {
      return this.$store.getters["login/user"];
    },
    isUserAdmin() {
      return this.$store.getters["login/isUserAdmin"];
    }
  },
  created: function() {
    this.areaSelected = this.$store.getters["shared/areaSelected"];
  }
};
</script>
