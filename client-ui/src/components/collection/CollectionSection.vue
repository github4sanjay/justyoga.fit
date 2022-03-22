<template>
  <div class="row justify-center">
    <div
      class="col-12 col-sm-6 col-md-4 col-lg-3"
      v-for="id in collections"
      :key="id"
    >
      <div
        :class="{
          row: true,
          'justify-center': true,
          'q-pa-md': $q.screen.gt.xs,
          'q-py-sm': $q.screen.xs
        }"
      >
        <collection-card
          :collection="getCollectionById(id)"
          @on-menu-click="onMenuClick($event)"
        />
      </div>
    </div>
    <q-dialog v-model="collectionDialogue" :maximized="$q.screen.xs">
      <q-card
        :style="{
          'min-width': $q.screen.gt.sm ? '850px' : ''
        }"
      >
        <collection-detail-form
          :data="dialogueData"
          @close="collectionDialogue = false"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="collectionCoverDialogue" :maximized="$q.screen.xs">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.sm ? '850px' : ''
        }"
      >
        <collection-cover-form
          @close="collectionCoverDialogue = false"
          :collection-data="dialogueData"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="deleteCollectionDialogue">
      <q-card flat>
        <delete-collection-form
          @close="deleteCollectionDialogue = false"
          :data="dialogueData"
        />
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import CollectionCard from "components/collection/CollectionCard";
import CollectionDetailForm from "components/collection/CollectionDetailForm";
import CollectionCoverForm from "components/collection/CollectionCoverForm";
import DeleteCollectionForm from "components/collection/DeleteCollectionForm";

export default {
  name: "CollectionSection",
  components: {
    CollectionCard,
    CollectionDetailForm,
    CollectionCoverForm,
    DeleteCollectionForm
  },
  props: {
    collections: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      collectionDialogue: false,
      dialogueData: null,
      collectionCoverDialogue: false,
      deleteCollectionDialogue: false
    };
  },
  methods: {
    getCollectionById(id) {
      return this.$store.getters[`collection/byId`](id);
    },
    onMenuClick(val) {
      switch (val.item) {
        case "EDIT_DETAILS":
          this.collectionDialogue = true;
          this.dialogueData = val.data;
          break;
        case "EDIT_COVER":
          this.collectionCoverDialogue = true;
          this.dialogueData = val.data;
          break;
        case "DELETE":
          this.deleteCollectionDialogue = true;
          this.dialogueData = val.data;
          break;
      }
    }
  }
};
</script>
