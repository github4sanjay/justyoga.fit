<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title
        :class="{
          'text-subtitle1': $q.screen.xs
        }"
        >Add Blog</q-toolbar-title
      >
      <q-btn flat label="Save" :loading="saving" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="collectionAddBlogForm">
        <div class="row q-pa-xs">
          <div class="col-12">
            <q-input
              v-model="blogId"
              label="Blog ID"
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
  name: "AddBlogForm",
  props: {
    collectionId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      saving: false,
      blogId: null
    };
  },
  methods: {
    upload() {
      this.$refs.collectionAddBlogForm.validate().then(success => {
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
        await this.$store.dispatch("collection/storeBlog", {
          collectionId: this.collectionId,
          blogId: this.blogId
        });
        this.closeBtnClick();
        this.clearForm();
      } catch (e) {
        NotifyUtil.showError("Error in adding blog");
      } finally {
        this.saving = false;
      }
    },
    closeBtnClick() {
      this.$emit("close");
    },
    clearForm() {
      this.$refs.collectionAddBlogForm.resetValidation();
    }
  }
};
</script>

<style scoped></style>
