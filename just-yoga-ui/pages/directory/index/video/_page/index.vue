<template>
  <v-card class="mt-3">
    <v-card-actions>
      <h4>Yoga Instructors ({{ sub.data.totalElements }})</h4>
      <v-spacer></v-spacer>
      <v-btn router :to="prevTo" icon :disabled="!sub.hasPrevious">
        <v-icon color="primary">fa fa-arrow-alt-circle-left</v-icon>
      </v-btn>
      <v-divider vertical />
      <v-btn router :to="nextTo" icon :disabled="!sub.hasNext">
        <v-icon color="primary">fa fa-arrow-alt-circle-right</v-icon>
      </v-btn>
    </v-card-actions>
    <v-divider />
    <v-row class="pa-3">
      <v-col
        cols="12"
        sm="6"
        md="4"
        class=""
        v-for="(data, i) in sub.data.content"
        :key="i"
      >
       <directory-video-card :video-info="data"/>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import TrainerCard from "@/components/base/TrainerCard";
import DirectoryVideoCard from "@/components/directory/DirectoryVideoCard";

export default {
  name: "DirectoryBlogIndex",
  components: {DirectoryVideoCard, TrainerCard },
  middleware: "directory_video",
  data: function () {
    return {
      sub: this.$store.getters["directory_videos/byPage"](this.$route.params.page),
      page: this.$route.params.page,
      nextPage: +this.$route.params.page + 1,
      previousPage: +this.$route.params.page - 1,
    };
  },
  computed: {
    prevTo() {
      return `/directory/trainer/${this.previousPage}`;
    },
    nextTo() {
      return `/directory/trainer/${this.nextPage}`;
    },
  },
  head() {
    return {
      title: `Yoga videos in the World - Just Yoga`,
      meta: [
        {
          hid: `description`,
          name: "description",
          content: `Explore yoga videos in the world and also explore yoga videos country wise`,
        },
        {
          hid: `keywords`,
          name: "keywords",
          keywords: `yoga,videos,details,rating,reviews,yoga,feeds,
            blog,contact,yoga classes`,
        },
      ],
    };
  },
};
</script>

<style scoped></style>
