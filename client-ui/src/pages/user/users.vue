<template>
  <q-page
    :class="{
      'q-pa-xs': $q.screen.lt.md,
      'q-pa-md': $q.screen.gt.sm
    }"
  >
    <div class="row justify-center">
      <div
        :class="{
          'col-12': true,
          'col-md-4': true,
          'col-lg-3': true,
          'q-pr-sm': $q.screen.gt.xs,
          'q-mb-md': $q.screen.xs
        }"
      >
        <user :user="model" :editable="!!editable" />
      </div>
      <div class="col-12 col-md-8 col-lg-7">
        <q-card square class="shadow-1" flat bordered>
          <div class="row">
            <div class="col-12" v-if="editable">
              <user-action-section :user="model" />
            </div>
            <div class="col-12">
              <q-tabs
                :dense="$q.screen.xs"
                inline-label
                outside-arrows
                mobile-arrows
                shrink
                stretch
                :class="{
                  'bg-grey-3': !this.$q.dark.isActive,
                  'text-grey': !this.$q.dark.isActive,
                  'bg-blue-grey-10': this.$q.dark.isActive
                }"
                active-color="primary"
                indicator-color="primary"
                align="left"
                v-model="tab"
              >
                <q-route-tab
                  name="Home"
                  label="Home"
                  :to="`/users/${id}/detail`"
                />

                <q-route-tab
                  name="Blogs"
                  label="Blogs"
                  :to="`/users/${id}/blogs/${page}`"
                />

                <q-route-tab
                  name="Videos"
                  label="Videos"
                  :to="`/users/${id}/videos/${page}`"
                />

                <q-route-tab
                  name="Images"
                  label="Images"
                  :to="`/users/${id}/images/${page}`"
                />

                <q-route-tab
                  name="Reviews"
                  label="Reviews"
                  :to="`/users/${id}/reviews/${page}`"
                />

                <!-- <q-btn-dropdown
                  v-if="$q.screen.lt.md"
                  auto-close
                  stretch
                  flat
                  label="More..."
                >
                  <q-list>
                    <q-item
                      clickable
                      @click="tab = 'Images'"
                      :to="`/users/${id}/images/${page}`"
                    >
                      <q-item-section>Images</q-item-section>
                    </q-item>

                    <q-item
                      clickable
                      @click="tab = 'Reviews'"
                      :to="`/users/${id}/reviews/${page}`"
                    >
                      <q-item-section>Reviews</q-item-section>
                    </q-item>
                  </q-list>
                </q-btn-dropdown> -->
              </q-tabs>
            </div>
          </div>
        </q-card>
        <div class="row">
          <div
            class="col-12 q-mt-sm"
            v-if="editable && alertText && !hideAlert"
          >
            <q-banner inline-actions class="text-white bg-primary">
              {{ alertText }}
              <template v-slot:action>
                <q-btn flat label="Dismiss" @click="hideAlert = true" />
              </template>
            </q-banner>
          </div>
          <div class="col-12 q-mt-sm">
            <router-view />
          </div>
        </div>
      </div>
    </div>
  </q-page>
</template>

<script>
import { mapGetters } from "vuex";
import User from "components/user/User";
import UserActionSection from "components/user/UserActionSection";
import { Loading } from "quasar";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";

export default {
  name: "UsersPage",
  components: {
    UserActionSection,
    User
  },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    try {
      const params = currentRoute.params;
      let user = store.getters["users/user"](params.id);
      if (user === null || user === undefined) {
        user = await store.dispatch("users/storeUser", { id: params.id });
      }

      let requests = [];
      if (user.basicInfo === null || user.basicInfo === undefined) {
        requests.push(store.dispatch("users/getBasicInfo", { id: user.id }));
        //await store.dispatch("users/getData", { id: user.id });
      }

      if (
        user.medicalExpertise === null ||
        user.medicalExpertise === undefined
      ) {
        requests.push(
          store.dispatch("users/getMedicalExpertise", {
            id: user.id,
            page: 0,
            count: 3
          })
        );
      }

      if (user.yogaExpertise === null || user.yogaExpertise === undefined) {
        requests.push(
          store.dispatch("users/getYogaExpertise", { id: user.id })
        );
      }

      if (
        user.yogaCertificates === null ||
        user.yogaCertificates === undefined
      ) {
        requests.push(
          store.dispatch("users/getYogaCertificates", { id: user.id })
        );
      }

      if (user.interest === null || user.interest === undefined) {
        requests.push(store.dispatch("users/getInterests", { id: user.id }));
      }

      let medicalExpertise = store.getters["expertise/medicalExpertise"];
      if (!(Array.isArray(medicalExpertise) && medicalExpertise.length)) {
        requests.push(store.dispatch("expertise/getData", {}));
      }

      await Promise.all(requests);
    } catch (e) {
      redirect("/error500");
    }
    Loading.hide();
  },
  data: function() {
    return {
      profilePicDialogue: false,
      profileDialogue: false,
      coverPicWidth: 755,
      coverPicHeight: 300,
      altProfilePic: "/images/util/profile_picture_blank_portrait.png",
      isTrainerCheckBox: false,
      alert: true,
      hideAlert: false,
      tab: "Details"
    };
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    model() {
      return this.$store.getters["users/user"](this.$route.params.id);
    },
    editable() {
      return (
        this.currentUser && this.model && this.currentUser.id === this.model.id
      );
    },
    id() {
      return this.$route.params.id;
    },
    page() {
      return this.$route.params.page ? this.$route.params.page : 0;
    },
    tabItems() {
      let id = this.$route.params.id;
      let page = this.$route.params.page ? this.$route.params.page : 0;
      let tabItems = [
        { text: "Details", link: "/users/" + id + "/detail" },
        { text: "Expertise", link: "/users/" + id + "/expertise" },
        { text: "Images", link: `/users/${id}/images/${page}` },
        { text: "Videos", link: `/users/${id}/videos/${page}` },
        //{ text: "Bookmarks", link: "/users/" + id + "/bookmarks" },
        { text: "Reviews", link: `/users/${id}/reviews/${page}` },
        { text: "Blogs", link: `/users/${id}/blogs/${page}` }
      ];
      if (this.currentUser !== null && this.currentUser.id === this.model.id) {
        //tabItems.push({ text: "Manage", link: "/users/" + id + "/admin" });
      }
      return tabItems;
    },
    alertText() {
      if (!this.model.interest) {
        return "Please update your interest !";
      } else if (!this.model.basicInfo) {
        return "Please update your basic information !";
      } else if (!this.model.profilePic) {
        return "Please update your profile picture !";
      } else if (this.model.interest && this.model.interest.trainer) {
        if (
          (!this.model.medicalExpertise ||
            this.model.medicalExpertise.length === 0) &&
          (!this.model.yogaExpertise ||
            this.model.yogaExpertise.length === 0) &&
          (!this.model.yogaCertificates ||
            this.model.yogaCertificates.length === 0)
        ) {
          return "Add your expertise so that you stand out to users !";
        }
      }

      return null;
    }
  },
  methods: {}
};
</script>
>
