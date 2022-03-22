<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title
        :class="{
          'text-subtitle1': $q.screen.xs
        }"
        >Add Image</q-toolbar-title
      >
      <q-btn flat label="Save" :loading="saving" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="collectionAddImageForm">
        <div class="row q-pa-xs">
          <div class="col-12">
            <q-input
              v-model="imageId"
              label="Image ID"
              :rules="[val => !!val || '* Required']"
              lazy-rules
            />
          </div>
        </div>
      </q-form>
    </div>
  </div>
</template>

<script>
import NotifyUtil from "src/utils/NotifyUtil";

export default {
  name: "AddImageForm",
  props: {
    collectionId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      saving: false,
      imageId: null
    };
  },
  methods: {
    upload() {
      this.$refs.collectionAddImageForm.validate().then(success => {
        if (success) {
          this.save();
        } else {
          return;
        }
      });
    },
    async save(val) {
      this.saving = true;
      try {
        await this.$store.dispatch("collection/storeImage", {
          collectionId: this.collectionId,
          imageId: this.imageId
        });
        this.closeBtnClick();
        this.clearForm();
      } catch (e) {
        NotifyUtil.showError("Error in adding image");
      } finally {
        this.saving = false;
      }
    },
    closeBtnClick() {
      this.$emit("close");
    },
    clearForm() {
      this.$refs.collectionAddImageForm.resetValidation();
    }
  }
};
</script>

<style scoped></style>
