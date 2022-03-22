<template>
  <confirm
    :loading="deletingCollection"
    :model="data"
    :text="deleteCollectionText"
    @close="close"
    @confirm="deleteCollection"
  />
</template>

<script>
import Confirm from "../base/Confirm";
export default {
  name: "DeleteCollectionForm",
  components: {Confirm},
  props: {
    data: {
      type: Object,
      require: true
    }
  },
  data() {
    return {
      deletingCollection: false,
      deleteCollectionText: "Do you want to delete this collection ?",
    };
  },
  methods: {
    close(){
      this.$emit("close");
    },
    deleteCollection(val){
      this.deletingCollection = true;
      this.$store
        .dispatch("collection/delete", val)
        .then(() => {
          this.deletingCollection = false;
          this.$emit("close");
        })
        .catch(() => {
          this.deletingCollection = false;
        });
    }
  }
};
</script>

<style scoped></style>
