<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Change Cover</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="uploading" @click="upload()">
          Save
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
      <single-image-upload
        @image-data="onChangeInImages"
        :ratio="2"
        :clear="clearCover"
      />
    </v-container>
  </v-container>
</template>

<script>
import SingleImageUpload from "../utils/SingleImageUpload";
export default {
  name: "CollectionCoverForm",
  components: { SingleImageUpload },
  props: {
    collectionData: {
      type: Object,
      required: true,
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
    },
  }
};
</script>

<style scoped></style>
