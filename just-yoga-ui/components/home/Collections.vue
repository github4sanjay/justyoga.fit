<template>
  <v-row>
    <v-col cols="12" sm="6" md="4" v-for="(id, i) in collections.data" :key="i">
      <collection-card
        :collection="$store.getters[`collection/byId`](id)"
        @on-menu-click="onMenuClick"
      />
    </v-col>
    <v-dialog width="800" v-model="collectionDialogue">
      <collection-operation
        @close="collectionDialogue = false"
        :data="dialogueData"
      />
    </v-dialog>
    <v-dialog width="800" v-model="collectionCoverDialogue">
      <v-card flat>
        <collection-cover-form
          @close="collectionCoverDialogue = false"
          :collection-data="dialogueData"
        />
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="deleteCollectionDialogue">
      <v-card flat>
        <delete-collection-form
          @close="deleteCollectionDialogue = false"
          :data="dialogueData"
        />
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import CollectionCard from "../collection/CollectionCard";
import CollectionOperation from "../collection/CollectionOperation";
import CollectionCoverForm from "../collection/CollectionCoverForm";
import Confirm from "../base/Confirm";
import DeleteCollectionForm from "../collection/DeleteCollectionForm";
export default {
  name: "Collections",
  props: {
    page: {
      type: Number,
      required: false,
    },
  },
  components: {
    DeleteCollectionForm,
    Confirm,
    CollectionCoverForm,
    CollectionOperation,
    CollectionCard,
  },
  data() {
    return {
      collections: this.$store.getters["collection/byPage"](
        this.page ? this.page : 0
      ),
      collectionDialogue: false,
      dialogueData: null,
      collectionCoverDialogue: false,
      deleteCollectionDialogue: false
    };
  },
  methods: {
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
    },
  },
};
</script>
