<template>
  <v-card class="mt-3">
    <v-card-actions>
      <h4>Images ({{ sub.data.totalElements }})</h4>
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
      <v-col cols="12" sm="6" v-for="(data, i) in sub.data.content" :key="i">
        <directory-image-card :image-info="data" />
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import DirectoryImageCard from "@/components/directory/DirectoryImageCard";

export default {
  name: "DirectoryBlogIndex",
  components: { DirectoryImageCard },
  middleware: "directory_country_image",
  data: function () {
    return {
      sub: this.$store.getters["directory_images/countryByPage"](
        this.$route.params.country, this.$route.params.page
      ),
      page: this.$route.params.page,
      nextPage: +this.$route.params.page + 1,
      previousPage: +this.$route.params.page - 1,
      country: this.$route.params.country
    };
  },
  computed: {
    prevTo() {
      return `/directory/${this.country}/image/${this.previousPage}`;
    },
    nextTo() {
      return `/directory/${this.country}/image/${this.nextPage}`;
    },
  },
};
</script>

<style scoped></style>
