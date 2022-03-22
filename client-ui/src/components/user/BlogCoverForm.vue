<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Update Blog Cover</q-toolbar-title>
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
import MediaUploader from "src/utils/MediaUploader";

export default {
  name: "BlogCoverForm",
  components: { SingleImageUpload },
  props: {
    blogData: {
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
    async upload() {
      if (!this.coverPicData || !this.coverPicData.blob) {
        return;
      }
      this.uploading = true;
      const url = await MediaUploader.uploadMedia({
        media: this.coverPicData.blob,
        contentType: this.coverPicData.blob.type
      });
      this.$store
        .dispatch("blogs/storeCover", {
          blogId: this.blogData.id,
          image: url.id
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
      if (!this.coverPicData || !this.coverPicData.blob) {
        errors.push("Image is required");
      }
      return errors;
    }
  }
};
</script>

<style scoped></style>
