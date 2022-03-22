<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title
        :class="{
          'text-subtitle1': $q.screen.xs
        }"
        >Add Video</q-toolbar-title
      >
      <q-btn flat label="Save" :loading="saving" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="collectionAddVideoForm">
        <div class="row q-pa-xs">
          <div class="col-12">
            <q-input
              v-model="videoId"
              label="Video ID"
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
  name: "AddVideoForm",
  props: {
    collectionId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      saving: false,
      videoId: null
    };
  },
  methods: {
    upload() {
      this.$refs.collectionAddVideoForm.validate().then(success => {
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
        await this.$store.dispatch("collection/storeVideo", {
          collectionId: this.collectionId,
          videoId: this.videoId
        });
        this.closeBtnClick();
        this.clearForm();
      } catch (e) {
        NotifyUtil.showError("Error in adding video");
      } finally {
        this.saving = false;
      }
    },
    closeBtnClick() {
      this.$emit("close");
    },
    clearForm() {
      this.$refs.collectionAddVideoForm.resetValidation();
    }
  }
};
</script>

<style scoped></style>
