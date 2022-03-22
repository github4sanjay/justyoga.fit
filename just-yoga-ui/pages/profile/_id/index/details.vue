<template>
  <profile-details :user="user"/>
</template>

<script>
import ProfileDetails from "@/components/profile/ProfileDetails";

export default {
  name: "index",

  components: {
    ProfileDetails,
  },

  computed: {
    user() {
      return this.$store.getters["users/user"](this.$route.params.id);
    }
  }
};
</script>

<style scoped></style>
