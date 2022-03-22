<template>
  <q-page>
    <expertise-section :user="user" :editable="editable" />
  </q-page>
</template>

<script>
import ExpertiseSection from "components/user/ExpertiseSection";
import { Loading } from "quasar";
import { mapGetters } from "vuex";

export default {
  name: "expertise",
  components: {
    ExpertiseSection
  },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let medicalExpertise = store.getters["expertise/medicalExpertise"];
    if (!(Array.isArray(medicalExpertise) && medicalExpertise.length)) {
      await store.dispatch("expertise/getData", {});
    }
    Loading.hide();
  },
  data: function() {
    return {};
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    user() {
      return this.$store.getters["users/user"](this.$route.params.id);
    },
    editable() {
      return (
        this.currentUser && this.user && this.currentUser.id === this.user.id
      );
    }
  },
  methods: {}
};
</script>

<style scoped></style>
