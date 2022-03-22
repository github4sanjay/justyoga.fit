<template>
  <q-layout view="hHh lpR fFf">
    <q-header :class="{ transparent: isTransparent }" v-scroll="scrolled">
      <q-toolbar>
        <q-btn flat dense round aria-label="logo" to="/">
          <q-avatar size="30px">
            <img
              :src="
                require(`src/assets/${
                  isTransparent
                    ? 'white_logo_only_blue.png'
                    : 'white_logo_only.png'
                }`)
              "
            />
          </q-avatar>
        </q-btn>
        <q-toolbar-title
          :class="{
            'text-bold': true,
            'q-pl-none': true,
            'text-secondary': isTransparent,
            'text-overline': true,
          }"
        >
          JustYoga
        </q-toolbar-title>
        <q-space />
        <q-btn
          v-show="$q.screen.gt.sm"
          v-for="(menuItem, index) in items"
          :key="index"
          :stretch="menuItem.label !== 'Login'"
          :flat="menuItem.label !== 'Login'"
          :color="menuItem.label !== 'Login' ? toolbarTextColor : 'accent'"
          router
          :label="menuItem.label"
          :to="menuItem.to"
        />
        <profile-menu v-show="$q.screen.gt.sm" />
        <q-btn
          v-if="$q.screen.lt.md"
          flat
          dense
          round
          icon="menu"
          :color="toolbarTextColor"
          aria-label="Menu"
          @click="drawer = !drawer"
        />
      </q-toolbar>
    </q-header>

    <q-card square elevated>
      <q-drawer v-model="drawer" side="right" :width="250" bordered>
        <q-scroll-area class="fit">
          <q-list v-if="user">
            <q-item clickable exact :to="`/users/${user.id}/detail`" v-ripple>
              <q-item-section avatar>
                <q-avatar size="40px" class="bg-secondary text-white">
                  <user-image :user="user" />
                </q-avatar>
              </q-item-section>
              <q-item-section>
                <q-item-label
                  :class="{
                    'text-h6': $q.screen.gt.sm,
                    'text-subtitle2': $q.screen.lt.md,
                  }"
                >
                  <v-clamp autoresize hyphens :max-lines="1">
                    {{ user.name }}
                  </v-clamp>
                </q-item-label>
                <q-item-label caption> </q-item-label>
              </q-item-section>
            </q-item>
            <q-separator />
          </q-list>
          <q-list v-for="(menuItem, index) in items" :key="index">
            <q-item
              clickable
              exact
              :to="menuItem.to"
              v-ripple
              v-if="menuItem.label !== 'Logout'"
            >
              <q-item-section avatar>
                <q-icon :name="menuItem.icon" />
              </q-item-section>
              <q-item-section>
                {{ menuItem.label }}
              </q-item-section>
            </q-item>
            <q-item clickable exact v-ripple v-else @click="logout()">
              <q-item-section avatar>
                <q-icon :name="menuItem.icon" />
              </q-item-section>
              <q-item-section>
                {{ menuItem.label }}
              </q-item-section>
            </q-item>
            <q-separator v-if="menuItem.separator" />
          </q-list>
          <!-- <q-page-sticky position="bottom-right" :offset="[18, 0]">
            <div>
              <core-footer />
            </div>
          </q-page-sticky> -->
        </q-scroll-area>
      </q-drawer>
    </q-card>

    <!-- <q-footer>
      <div>
        <core-footer />
      </div>
    </q-footer> -->

    <div class="column items-center">
      <div class="col" style="width: 100%">
        <div class="row justify-center">
          <q-page-container style="width: 100%">
            <router-view />
          </q-page-container>
        </div>
        <div class="bg-dark text-white" v-if="!hideFooter">
          <core-footer />
        </div>
      </div>
    </div>
  </q-layout>
</template>

<script>
import EssentialLink from "components/EssentialLink";
import { mapGetters, mapMutations } from "vuex";
import ProfileMenu from "components/toolbar/ProfileMenu";
import CoreFooter from "layouts/CoreFooter";
import UserImage from "components/base/UserImage";
import VClamp from "vue-clamp";
import NotifyUtil from "src/utils/NotifyUtil";
import { Cookies } from "quasar";

export default {
  name: "MainLayout",

  components: { ProfileMenu, CoreFooter, UserImage, VClamp },

  data() {
    return {
      hideFooter: false,
      drawer: false,
      darkTheme: false,
      cookieBanner: true,
      isTransparent: true,
      toolbarTextColor: "secondary",
      cookiePolicyAccept: "x-cookie-policy",
    };
  },
  methods: {
    logout() {
      this.$store.dispatch("login/logout").then(() => {
        this.$router.push("/login");
      });
    },
    scrolled(position) {
      if (this.$router.currentRoute.path === "/") {
        if (position > 10) {
          this.isTransparent = false;
          this.toolbarTextColor = "";
        } else {
          this.isTransparent = true;
          this.toolbarTextColor = "secondary";
        }
      }
    },
  },
  computed: {
    ...mapGetters({
      commonLoadingDialog: "shared/dialog",
      dialogText: "shared/dialogText",
      successText: "shared/successText",
      successSnackbar: "shared/successText",
      errorText: "shared/errorText",
      errorSnackbar: "shared/errorSnackbar",
      user: "login/user",
      isUserAdmin: "login/isUserAdmin",
    }),
    items() {
      let items = [
        {
          icon: "home",
          label: "Home",
          to: "/",
          separator: true,
        },
        {
          icon: "fa fa-compact-disc",
          label: "Yoga Contents",
          to: `/collections/0`,
        },
        {
          icon: "fa fa-male",
          label: "Trainers",
          to: "/directory/trainers/0",
        },
        {
          icon: "fa fa-rss-square",
          label: "Blogs",
          to: "/directory/blogs/0",
        },
        {
          icon: "fa fa-video",
          label: "Videos",
          to: "/directory/videos/0",
        },
        {
          icon: "fa fa-images",
          label: "Photos",
          to: "/directory/images/0",
          separator: true,
        },
      ];
      if (!this.user) {
        items.push({
          icon: "account_box",
          label: "Login",
          to: "/login",
        });
      } else {
        if (this.isUserAdmin) {
          items.push({
            icon: "dashboard",
            label: "Admin",
            to: "/admin",
          });
        }

        items.push({
          icon: "person",
          label: "Profile",
          to: `/users/${this.user.id}/detail`,
        });

        if (this.$q.screen.lt.md) {
          items.push({
            icon: "fas fa-sign-out-alt",
            label: "Logout",
            separator: true,
          });
        }
      }
      // items.push({
      //   icon: "fa fa-id-card-alt",
      //   label: "Contact us",
      //   to: "/contact-us",
      //   separator: true
      // });
      return items;
    },
  },
  watch: {
    $route(to, from) {
      if (to.path === "/") {
        this.isTransparent = true;
        this.toolbarTextColor = "secondary";
      } else {
        this.isTransparent = false;
        this.toolbarTextColor = "";
      }
      if (to.path === "/tech") {
        this.hideFooter = true;
      } else {
        this.hideFooter = false;
      }
    },
    errorSnackbar(val) {
      if (val) {
        this.$q.notify({
          color: "negative",
          message: this.errorText,
        });
      }
    },
    successSnackbar(val) {
      if (val) {
        this.$q.notify({
          color: "positive",
          message: this.successText,
        });
      }
    },
    commonLoadingDialog(val) {
      if (val) {
        this.$q.loading.show({
          message: this.dialogText,
        });
      } else {
        this.$q.loading.hide();
      }
    },
  },
  mounted() {
    if (this.$router.currentRoute.path === "/") {
      this.isTransparent = true;
      this.toolbarTextColor = "secondary";
    } else {
      this.isTransparent = false;
      this.toolbarTextColor = "";
    }

    if (this.$router.currentRoute.path === "/tech") {
      this.hideFooter = true;
    } else {
      this.hideFooter = false;
    }

    var acceptValue = Cookies.get(this.cookiePolicyAccept);

    if (!acceptValue) {
      this.$q.notify({
        textColor: "white",
        message:
          "Cookies help us deliver our services. By using our services you agree to our use of cookies",
        position: "bottom-right",
        multiLine: true,
        actions: [
          {
            label: "Agree",
            color: "yellow",
            handler: () => {
              Cookies.set(this.cookiePolicyAccept, "true");
            },
          },
        ],
        timeout: 0,
      });
    }
  },
};
</script>
