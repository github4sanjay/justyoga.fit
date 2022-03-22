<template>
  <v-container>
    <v-card>
      <v-row>
        <v-col cols="12" class="pa-0">
          <directory-breadcrumbs :items="items"/>
        </v-col>
        <v-col class="py-0 mt-5">
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
import DirectoryBreadcrumbs from "@/components/directory/DirectoryBreadcrumbs";
export default {
  name: "SubLocality",
  components: {DirectoryBreadcrumbs, DirectoryTab },
  data: function () {
    return {
      subLocalities: this.$store.getters[
        "place/getSubLocalityLevel1ByLocalityId"
      ](this.$route.params.locality),
      locality: this.$store.getters["place/locality"](
        this.$route.params.locality
      ),
      state: this.$store.getters["place/administrativeAreaLevel1"](
        this.$route.params.state
      ),
      country: this.$store.getters["place/country"](this.$route.params.country),
      subLocality: this.$store.getters["place/subLocalityLevel1"](
        this.$route.params.sub
      ),
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
          disabled: false,
          href: `/directory/${this.country.id}/trainer/0`,
        },
        {
          text: this.state.name,
          disabled: false,
          href: `/directory/${this.country.id}/${this.state.id}/trainer/0`,
        },
        {
          text: this.locality.name,
          disabled: false,
          href: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/trainer/0`,
        },
        {
          text: this.subLocality.name,
          disabled: true,
          href: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/trainer/0`,
        },
      ];
    },
    tabItems() {
      let page = this.$route.params.page ? this.$route.params.page : 0;
      return [
        {
          text: "Instructors",
          link: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/trainer/${page}`,
        },
        {
          text: "Blogs",
          link: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/blog/${page}`,
        },
        {
          text: "Videos",
          link: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/video/${page}`,
        },
        {
          text: "Images",
          link: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/image/${page}`,
        },
      ];
    },
  },
};
</script>

<style scoped></style>
