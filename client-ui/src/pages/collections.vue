<template>
  <q-page padding>
    <q-card
      :class="{ 'q-pa-none': true, 'q-mx-md': $q.screen.gt.xs }"
      square
      flat
      bordered
    >
      <q-card-actions class="q-pa-none">
        <q-card-section>
          <div>
            Explore Collections Here
            <q-badge
              color="accent"
              text-color="white"
              :label="items.totalElements"
            />
          </div>
        </q-card-section>
        <q-space />
        <q-btn
          :disable="!items.hasPrevious"
          flat
          round
          color="primary"
          icon="fas fa-arrow-circle-left"
          :to="`${+items.page - 1}`"
        />
        <q-separator vertical />
        <q-btn
          :disable="!items.hasNext"
          flat
          round
          color="primary"
          icon="fas fa-arrow-circle-right"
          :to="`${+items.page + 1}`"
        />
      </q-card-actions>
    </q-card>
    <div class="row justify-center">
      <collection-section :collections="items.data" />
    </div>
  </q-page>
</template>

<script>
import CollectionSection from "components/collection/CollectionSection";
import { Loading } from "quasar";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";

export default {
  name: "Collections",
  components: { CollectionSection },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let page = currentRoute.params.page ? currentRoute.params.page : 0;
    let collectionPage = store.getters["collection/byPage"](page);
    if (collectionPage === null || collectionPage === undefined) {
      try {
        await store.dispatch("collection/getCollectionsPage", {
          page: page,
          count: 10,
          sort: "updatedAt",
          order: "desc"
        });
      } catch (e) {
        redirect("/error404");
      }
    }
    Loading.hide();
  },
  computed: {
    items() {
      return this.$store.getters["collection/byPage"](this.$route.params.page);
    }
  },
  methods: {
    getCollectionById(id) {
      return this.$store.getters[`collection/byId`](id);
    },
    getTitle() {
      return "Yoga Collections";
    },
    getDesc() {
      return "Browse best yoga collections on justyoga.fit. You can find yoga videos, photos and blogs here.";
    },
    getUrl() {
      return `${config.uiUrl}/collections/${this.$route.params.page}`;
    },
    getImage() {
      return Cms.DEFAULT_IMAGE_URL;
    },
    getKeyWords() {
      return `yoga, video, trainer, blogs, photos`;
    },
    getPrevLink() {
      if (this.items.hasPrevious)
        return `${config.uiUrl}/collections/${+this.items.page - 1}`;
      else null;
    },
    getNextLink() {
      if (this.items.hasNext)
        return `${config.uiUrl}/collections/${+this.items.page + 1}`;
      else null;
    }
  },
  meta() {
    let title = this.getTitle();
    let description = this.getDesc();
    let url = this.getUrl();
    let image = this.getImage();
    let keywords = this.getKeyWords();
    return MetaUtil.getMeta(
      title,
      description,
      url,
      image,
      keywords,
      null,
      "website",
      this.getPrevLink(),
      this.getNextLink()
    );
  }
};
</script>
