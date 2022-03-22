<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Edit Profile Picture</q-toolbar-title>
      <q-btn
        flat
        label="Save"
        :loading="updatingProfilePic"
        @click="upload()"
      />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div>
      <single-image-upload
        @image-data="onChangeInImages"
        :errors="imageErrors"
        :ratio="1"
      />
    </div>
  </div>
</template>

<script>
import SingleImageUpload from "components/util/SelectImage";
import MediaUploader from "src/utils/MediaUploader";

export default {
  name: "ProfilePicForm",

  components: { SingleImageUpload },
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      imageData: null,
      updatingProfilePic: false
    };
  },
  methods: {
    upload() {
      if (!this.imageData || !this.imageData.blob) {
        return;
      }
      this.updateProfilePic({
        image: this.imageData
      });
    },
    async updateProfilePic(val) {
      this.updatingProfilePic = true;
      val.userId = this.user.id;
      const url = await MediaUploader.uploadMedia({
        media: val.image.blob,
        contentType: val.image.blob.type
      });
      val.url = url.id;
      this.$store
        .dispatch("users/updateProfilePic", val)
        .then(() => {
          this.updatingProfilePic = false;
          this.closeBtnClick();
        })
        .catch(() => (this.updatingProfilePic = false));
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
    onChangeInImages(val) {
      this.imageData = val;
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
