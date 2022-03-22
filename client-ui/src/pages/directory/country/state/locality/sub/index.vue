<template>
  <q-page padding>
    <div class="row">
      <div class="col-12">
        <q-card
          square
          flat
          bordered
          :class="{ 'q-pa-none': true, 'q-mx-md': $q.screen.gt.xs }"
        >
          <q-card-section>
            <directory-breadcrumbs :items="items" />
          </q-card-section>
          <q-card-section class="q-pa-none">
            <directory-tab :tab-items="tabItems" />
          </q-card-section>
        </q-card>
      </div>
      <div class="col-12 q-mt-sm">
        <q-card square flat class="shadow-1">
          <router-view />
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script>
import DirectoryTab from "components/directory/DirectoryTab";
import DirectoryBreadcrumbs from "components/directory/DirectoryBreadcrumbs";
export default {
  name: "SubLocality",
  components: { DirectoryBreadcrumbs, DirectoryTab },
  data: function() {
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
      )
    };
  },
  computed: {
    items() {
      return [
        {
          text: "Countries",
          disabled: false,
          href: "/directory/trainers/0"
        },
        {
          text: this.country.name,
          disabled: false,
          href: `/directory/${this.country.id}/trainers/0`
        },
        {
          text: this.state.name,
          disabled: false,
          href: `/directory/${this.country.id}/${this.state.id}/trainers/0`
        },
        {
          text: this.locality.name,
          disabled: false,
          href: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/trainers/0`
        },
        {
          text: this.subLocality.name,
          disabled: true,
          href: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/trainers/0`
        }
      ];
    },
    tabItems() {
      let page = this.$route.params.page ? this.$route.params.page : 0;
      return [
        {
          text: "Trainers",
          link: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/trainers/${page}`
        },
        {
          text: "Blogs",
          link: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/blogs/${page}`
        },
        {
          text: "Videos",
          link: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/videos/${page}`
        },
        {
          text: "Photos",
          link: `/directory/${this.country.id}/${this.state.id}/${this.locality.id}/${this.subLocality.id}/images/${page}`
        }
      ];
    }
  }
};
</script>

<style scoped></style>
