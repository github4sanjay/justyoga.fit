<template>
  <v-container>
    <v-card>
      <v-row>
        <v-col class="pa-0">
          <directory-breadcrumbs :items="items"/>
        </v-col>
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
              Browse By Administrative Areas in {{country.name}}
            </v-card-text>
          </v-card>
        </v-col>
        <v-col cols="12" class="py-0">
          <v-card flat>
            <v-card-text class="py-0">
              <v-chip-group column>
                <v-chip
                  v-for="model in states"
                  :key="model.id"
                  :small="$vuetify.breakpoint.xsOnly"
                  label
                  router
                  :to="`/directory/${country.id}/${model.id}/trainer/0`"
                >
                  {{ model.name }}
                </v-chip>
              </v-chip-group>
            </v-card-text>
          </v-card>
        </v-col>
        <v-col cols="12" class="py-0 mt-5">
          <v-card>
            <directory-tab :tab-items="tabItems"/>
          </v-card>
        </v-col>
      </v-row>
    </v-card>
    <nuxt-child :key="$route.fullPath"></nuxt-child>
  </v-container>
</template>

<script>
import DirectoryTab from "@/components/directory/DirectoryTab";
import DirectoryBreadcrumbs from "@/components/directory/DirectoryBreadcrumbs";
export default {
  name: "States",
  components: {DirectoryBreadcrumbs, DirectoryTab},
  data: function () {
    return {
      states: this.$store.getters[
        "place/getAdministrativeAreaLevel1ByCountryId"
      ](this.$route.params.country),
      country: this.$store.getters["place/country"](this.$route.params.country),
    };
  },
  computed: {
    items() {
      return [
        {
          text: "Countries",
          disabled: false,
          href: "/directory/trainer/0",
        },
        {
          text: this.country.name,
          disabled: true,
          href: `/directory/${this.country.id}/trainer/0`,
        },
      ];
    },
    tabItems() {
      let page = this.$route.params.page ? this.$route.params.page : 0;
      return [
        {
          text: "Instructors",
          link: `/directory/${this.country.id}/trainer/${page}`,
        },
        {
          text: "Blogs",
          link: `/directory/${this.country.id}/blog`,
        },
        {
          text: "Videos",
          link: `/directory/${this.country.id}/video/${page}`,
        },
        {
          text: "Images",
          link: `/directory/${this.country.id}/image/${page}`,
        },
      ];
    },
  },
};
</script>

<style scoped></style>
