<template>
  <div>
    <div class="row">
      <div class="col-6 col-sm-4" v-for="(image, i) in images" :key="i">
        <image-card
          :image="
            typeof image === 'string'
              ? $store.getters['images/byId'](image)
              : image
          "
          :editable="editable"
          @on-menu-click="onImageMenuClick"
        />
      </div>
    </div>

    <q-dialog v-model="editImageDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.sm ? '850px' : ''
        }"
      >
        <image-edit-form
          :image="imageEditData"
          :user="user"
          @close="editImageDialogue = !editImageDialogue"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="deleteImageDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.sm ? '650px' : ''
        }"
      >
        <confirm
          :loading="deletingImage"
          :model="imageEditData"
          :text="deleteImageText"
          @close="deleteImageDialogue = !deleteImageDialogue"
          @confirm="deleteImage"
        />
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import ImageCard from "components/user/ImageCard";
import Confirm from "components/base/Confirm";
import ImageEditForm from "components/user/ImageEditForm";

export default {
  name: "image-section",
  components: {
    ImageCard,
    ImageEditForm,
    Confirm
  },
  props: {
    images: {
      type: Array,
      required: true
    },
    user: {
      type: Object,
      required: true
    },
    editable: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data: function() {
    return {
      imageDialogue: false,
      clearImageForm: false,
      savingImage: false,

      imageEditData: null,
      editImageDialogue: false,

      deleteImageDialogue: false,
      deleteImageText: "Do you want to delete this image ?",
      deletingImage: false
    };
  },
  methods: {
    onImageMenuClick(val) {
      switch (val.item) {
        case "Edit":
          this.editImageDialogue = true;
          this.imageEditData = val.data;
          break;
        case "Delete":
          this.deleteImageDialogue = true;
          this.imageEditData = val.data;
          break;
      }
    },
    deleteImage(val) {
      this.deletingImage = true;
      this.$store
        .dispatch("images/remove", val)
        .then(() => {
          this.deletingImage = false;
          this.deleteImageDialogue = false;
        })
        .catch(() => {
          this.deletingImage = false;
        });
    }
  }
};
</script>
