<template>
  <v-card class="mt-3">
    <v-card-actions>
      <h4>Videos ({{ videos.totalElements }})</h4>
      <v-spacer></v-spacer>
      <v-btn
        router
        :to="`/profile/${userId}/images/${previousPage}`"
        icon
        :disabled="!videos.hasPrevious"
      >
        <v-icon color="primary">fa fa-arrow-alt-circle-left</v-icon>
      </v-btn>
      <v-divider vertical />
      <v-btn
        router
        :to="`/profile/${userId}/images/${nextPage}`"
        icon
        :disabled="!videos.hasNext"
      >
        <v-icon color="primary">fa fa-arrow-alt-circle-right</v-icon>
      </v-btn>
    </v-card-actions>
    <v-divider />
    <v-row class="pa-3">
      <v-col cols="4" v-for="(video, i) in videos.data" :key="i">
        <video-card
          :video="$store.getters['videos/byId'](video)"
          :editable="editable"
        />
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import { mapGetters } from "vuex";
import VideoCard from "@/components/base/VideoCard";
export default {
  name: "VideoPageIndex",
  middleware: "profile_video",
  components: {VideoCard},
  data: function () {
    return {
      page: this.$route.params.page,
      videos: this.$store.getters["videos/byPageAndUserId"](
        this.$route.params.id,
        this.$route.params.page
      ),
      userId: this.$route.params.id,
      nextPage: +this.$route.params.page + 1,
      previousPage: +this.$route.params.page - 1,
    };
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user",
    }),
    user() {
      return this.$store.getters["users/user"](this.$route.params.id);
    },
    editable() {
      return (
        this.currentUser && this.user && this.currentUser.id === this.user.id
      );
    },
  },
};
</script>

<style scoped></style>
