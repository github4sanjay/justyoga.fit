<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Add New Image</q-toolbar-title>
      <q-btn flat label="Save" :loading="savingImage" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="imageForm">
        <div class="row items-center">
          <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
            <div>Title</div>
          </div>
          <div class="col-12 col-md-8">
            <q-input
              v-model="title"
              label="Title"
              maxlength="30"
              counter
              :rules="[
                val => !!val || '* Required',
                val => val.length <= 30 || 'Max 30 characters'
              ]"
              lazy-rules
            />
          </div>
        </div>
        <div class="row q-mt-sm">
          <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
            <div>Description</div>
          </div>
          <div class="col-12 col-md-8">
            <q-input
              outlined
              type="textarea"
              v-model="description"
              label="Description"
              maxlength="500"
              counter
              :rules="[
                val => !!val || '* Required',
                val =>
                  (val.length > 50 && val.length < 500) ||
                  'Min 50 and Max 500 characters'
              ]"
              lazy-rules
            />
          </div>
        </div>
        <div class="row">
          <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
            <div>Image</div>
          </div>
          <div class="col-12 col-md-8">
            <single-image-upload
              @image-data="onChangeInImages"
              :errors="coverErrors"
              :ratio="2"
              :clear="clearCover"
            />
          </div>
        </div>
      </q-form>
    </div>
  </div>
</template>

<script>
import SingleImageUpload from "components/util/SelectImage";

export default {
  name: "ImageForm",

  components: { SingleImageUpload },
  props: {
    user: {
      type: Object,
      required: false
    },
    data: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      title: null,
      description: null,
      clearCover: false,
      coverPicData: null,
      savingImage: false
    };
  },
  methods: {
    upload() {
      this.$refs.imageForm.validate().then(success => {
        if (success) {
          this.saveImage({
            coverPic: this.coverPicData,
            title: this.title,
            description: this.description
          });
        } else {
          return;
        }
      });
    },
    saveImage(val) {
      this.savingImage = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("images/store", val)
        .then(() => {
          this.savingImage = false;
          this.closeBtnClick();
          this.clear();
        })
        .catch(() => (this.savingImage = false));
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
    onChangeInImages(val) {
      this.coverPicData = val;
      this.clearCover = false; // set false so that cab be cleared in future
    },
    clear() {
      this.title = "";
      this.description = "";
      this.clearCover = true; // clear single-image-upload
    }
  },
  created() {
    if (this.data) {
      this.title = this.data.title;
      this.description = this.data.description;
    }
  },
  computed: {
    coverErrors() {
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
