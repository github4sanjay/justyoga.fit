<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Update Image Details</q-toolbar-title>
      <q-btn flat label="Save" :loading="updating" @click="upload()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="imageEditForm">
        <div class="row">
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
                val => val.length < 30 || 'Max 30 characters'
              ]"
              lazy-rules
            />
          </div>
        </div>
        <div class="row">
          <div class="col-3 col-md-4" v-if="$q.screen.gt.sm">
            <div>Description</div>
          </div>
          <div class="col-12 col-md-8">
            <q-input
              outlined
              filled
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
      </q-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "ImageEditForm",
  components: {},
  props: {
    image: {
      type: Object,
      required: true
    },
    user: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      title: null,
      description: null,
      updating: false
    };
  },
  methods: {
    upload() {
      this.$refs.imageEditForm.validate().then(success => {
        if (success) {
          this.updateImage({
            title: this.title,
            description: this.description,
            id: this.image.id,
            url: this.image.url
          });
        } else {
          return;
        }
      });
    },
    updateImage(val) {
      this.updating = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("images/update", val)
        .then(() => {
          this.updating = false;
          this.closeBtnClick();
        })
        .catch(() => {
          this.updating = false;
        });
    },
    closeBtnClick() {
      this.$emit("close", {});
    }
  },
  created() {
    if (this.image) {
      this.title = this.image.title;
      this.description = this.image.description;
    }
  },
  watch: {
    image(val) {
      if (val) {
        this.title = val.title;
        this.description = val.description;
      }
    }
  }
};
</script>

<style scoped></style>
