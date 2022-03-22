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
          <q-card-section>
            <div>Browse By Localities in {{ state.name }}</div>
          </q-card-section>
          <q-card-section>
            <div>
              <span
                class="q-pa-sm"
                v-for="locality in localities"
                :key="locality.id"
              >
                <q-btn
                  class="q-mt-sm"
                  size="md"
                  :to="
                    `/directory/${country.id}/${state.id}/${locality.id}/trainers/0`
                  "
                >
                  {{ locality.name }}
                </q-btn>
              </span>
            </div>
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
  name: "States",
  components: { DirectoryBreadcrumbs, DirectoryTab },
  data: function() {
    return {
      localities: this.$store.getters[
        "place/getLocalityByAdministrativeAreaLevel1Id"
      ](this.$route.params.state),
      state: this.$store.getters["place/administrativeAreaLevel1"](
        this.$route.params.state
      ),
      country: this.$store.getters["place/country"](this.$route.params.country)
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
          disabled: true,
          href: `/directory/${this.country.id}/${this.state.id}/trainers/0`
        }
      ];
    },
    tabItems() {
      let page = this.$route.params.page ? this.$route.params.page : 0;
      return [
        {
          text: "Trainers",
          link: `/directory/${this.country.id}/${this.state.id}/trainers/${page}`
        },
        {
          text: "Blogs",
          link: `/directory/${this.country.id}/${this.state.id}/blogs/${page}`
        },
        {
          text: "Videos",
          link: `/directory/${this.country.id}/${this.state.id}/videos/${page}`
        },
        {
          text: "Photos",
          link: `/directory/${this.country.id}/${this.state.id}/images/${page}`
        }
      ];
    }
  }
};
</script>

<style scoped></style>
