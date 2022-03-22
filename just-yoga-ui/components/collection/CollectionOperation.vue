<template>
  <v-card flat>
    <collection-form
      :data="data"
      :uploading="saving"
      @close="closeBtnClick"
      :clear="clearForm"
      @save="save"/>
  </v-card>
</template>

<script>
import CollectionForm from "./CollectionForm";
export default {
  name: "CollectionOperation",
  components: { CollectionForm },
  props: {
    data: {
      type: Object,
      required: false,
    },
  },
  data(){
    return {
      saving: false,
      clearForm: false
    }
  },
  methods: {
    closeBtnClick() {
      this.$emit("close", {});
    },
    async save(val){
      this.saving = false;
      try {
        let collectionData = await this.$store.dispatch("place/storePlace", val);
        collectionData.name = val.name;
        collectionData.description = val.description;
        if (this.data) {
          collectionData.id = this.data.id;
        }
        this.$store
          .dispatch("collection/store", collectionData)
          .then(() => {
            this.saving = false;
            this.$emit("close", {});
          })
          .catch(() => {
            this.saving = false;
          });
      } catch (e) {
        this.$nuxt.error({ statusCode: 500 });
      }
      this.clearForm = false; // set again false so that it cab be cleared again
    }
  },
};
</script>

<style scoped></style>
