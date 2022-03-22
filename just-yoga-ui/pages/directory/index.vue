<template>
  <v-container>
    <v-card>
      <v-row>
        <v-col cols="12">
          <v-card flat>
            <v-card-text
              :class="{
                'py-0': true,
                'justify-center': $vuetify.breakpoint.smAndDown,
                headline: $vuetify.breakpoint.mdAndUp,
                title: $vuetify.breakpoint.sm,
                'subtitle-2': $vuetify.breakpoint.xsOnly,
              }"
            >
              Browse By Countries
            </v-card-text>
          </v-card>
        </v-col>
        <v-col cols="12" class="py-0">
          <v-card flat>
            <v-card-text class="py-0">
              <v-chip-group column>
                <v-chip
                  v-for="country in countries"
                  :key="country.id"
                  :small="$vuetify.breakpoint.xsOnly"
                  label
                  router
                  :to="`/directory/${country.id}/trainer/0`"
                >
                  {{ country.name }}
                </v-chip>
              </v-chip-group>
            </v-card-text>
          </v-card>
        </v-col>
        <v-col cols="12" class="py-0 mt-5">
          <v-card>
            <directory-tab :tab-items="tabItems" />
          </v-card>
        </v-col>
      </v-row>
    </v-card>
    <nuxt-child :key="$route.fullPath"></nuxt-child>
  </v-container>
</template>

<script>
import DirectoryTab from "@/components/directory/DirectoryTab";
export default {
  name: "index",
  components: { DirectoryTab },
  data: function () {
    return {
      countries: this.$store.getters["place/getAllCountry"],
    };
  },
  computed: {
    tabItems() {
      let page = this.$route.params.page ? this.$route.params.page : 0;
      return [
        {
          text: "Instructors",
          link: `/directory/trainer/${page}`,
        },
        {
          text: "Blogs",
          link: `/directory/blog/${page}`,
        },
        {
          text: "Videos",
          link: `/directory/video/${page}`,
        },
        {
          text: "Images",
          link: `/directory/image/${page}`,
        },
      ];
    },
  },
};
</script>

<style scoped></style>
