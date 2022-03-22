<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Change Cover</q-toolbar-title>
      <q-btn flat label="Save" :loading="uploading" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup />
    </q-toolbar>
    <div>
      <single-image-upload
        @image-data="onChangeInImages"
        :ratio="2"
        :clear="clearCover"
        :errors="imageErrors"
      />
    </div>
  </div>
</template>

<script>
import SingleImageUpload from "components/util/SelectImage";
export default {
  name: "CollectionCoverForm",
  components: { SingleImageUpload },
  props: {
    collectionData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      clearCover: false,
      coverPicData: null,
      uploading: false
    };
  },
  methods: {
    upload() {
      if (!this.coverPicData || !this.coverPicData.blob) {
        return;
      }
      this.uploading = true;
      this.$store
        .dispatch("collection/storeCover", {
          collectionId: this.collectionData.id,
          image: this.coverPicData
        })
        .then(() => {
          this.uploading = false;
          this.$emit("close", {});
          this.clearCover = true; // clear single-image-upload
        })
        .catch(() => {
          this.uploading = false;
        });
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
    onChangeInImages(val) {
      this.coverPicData = val;
      this.clearCover = false; // set false so that can be cleared in future
    }
  },
  computed: {
    imageErrors() {
      const errors = [];
      if (!this.imageData || !this.imageData.blob) {
        errors.push("Image is required");
      }
      return errors;
    }
  }
};
</script>

<style scoped></style>
