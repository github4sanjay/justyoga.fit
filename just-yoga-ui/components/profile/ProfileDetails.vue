<template>
  <v-card class="pa-2 mt-3">
    <v-row>
      <v-col cols="12" sm="7" class="py-0">
        <basic-info-section
          :user="user"
          :editable="editable"
        />
      </v-col>
      <v-col cols="12" sm="5" v-if="user.basicInfo" class="text-center">
        <profile-map :basicInfo="user.basicInfo" />
      </v-col>
      <v-col cols="12">
        <image-section :user="user" :editable="editable" />
      </v-col>
      <v-col cols="12">
        <video-section :user="user" :editable="editable" />
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import BasicInfoSection from "./BasicInfoSection";
import ProfileMap from "./ProfileMap";
import ImageSection from "./ImageSection";
import VideoSection from "./VideoSection";

export default {
  name: "ProfileDetails",
  components: {
    VideoSection,
    ImageSection,
    ProfileMap,
    BasicInfoSection,
  },
  props: {
    user: {
      type: Object,
      required: true,
    }
  },
  data: function () {
    return {};
  },
  methods: {},
  computed: {
    currentUser() {
      return this.$store.getters["login/user"];
    },
    editable() {
      return !!(this.currentUser && this.currentUser.id === this.user.id);
    },
  },
  created() {},
  mounted() {},
};
</script>

<style scoped></style>
