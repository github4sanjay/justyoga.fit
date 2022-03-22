<template>
  <v-container>
    <v-card class="mt-3">
      <v-card-actions>
        <h4>Collections ({{ collections.totalElements }})</h4>
        <v-spacer></v-spacer>
        <v-btn
          router
          :to="`/contents/${previousPage}`"
          icon
          :disabled="!collections.hasPrevious"
        >
          <v-icon color="primary">fa fa-arrow-alt-circle-left</v-icon>
        </v-btn>
        <v-divider vertical />
        <v-btn
          router
          :to="`/contents/${nextPage}`"
          icon
          :disabled="!collections.hasNext"
        >
          <v-icon color="primary">fa fa-arrow-alt-circle-right</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
    <collections :page="+$route.params.page"/>
  </v-container>
</template>

<script>
import Collections from "@/components/home/Collections";
export default {
  name: "ContentsIndex",
  components: {Collections},
  middleware: "content",
  data() {
    return {
      collections: this.$store.getters["collection/byPage"](
        this.$route.params.page ? this.$route.params.page : 0
      ),
      nextPage: +this.$route.params.page + 1,
      previousPage: +this.$route.params.page - 1,
    };
  },
};
</script>

<style scoped></style>
