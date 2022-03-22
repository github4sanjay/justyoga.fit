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
            <div>
              Browse By Countries
            </div>
          </q-card-section>
          <q-card-section>
            <div>
              <span
                class="q-pa-sm"
                v-for="country in countries"
                :key="country.id"
              >
                <q-btn
                  class="q-mt-sm"
                  size="md"
                  :to="`/directory/${country.id}/trainers/0`"
                >
                  {{ country.name }}
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
export default {
  name: "DirectoryIndex",
  components: {
    DirectoryTab
  },
  data: function() {
    return {
      countries: this.$store.getters["place/getAllCountry"]
    };
  },
  computed: {
    tabItems() {
      let page = this.$route.params.page ? this.$route.params.page : 0;
      return [
        {
          text: "Trainers",
          link: `/directory/trainers/${page}`
        },
        {
          text: "Blogs",
          link: `/directory/blogs/${page}`
        },
        {
          text: "Videos",
          link: `/directory/videos/${page}`
        },
        {
          text: "Photos",
          link: `/directory/images/${page}`
        }
      ];
    }
  }
};
</script>

<style scoped></style>
