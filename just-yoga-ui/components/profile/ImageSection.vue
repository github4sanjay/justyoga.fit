<template>
  <v-card flat>
    <v-row align="center">
      <v-col cols="6" sm="4" class="pa-0">
        <v-card-text
          :class="{
            'py-0': true,
            'font-weight-bold': false,
            'grey--text': false,
            title: $vuetify.breakpoint.smAndUp,
            'subtitle-2': $vuetify.breakpoint.xsOnly,
          }"
        >
          Latest Images
        </v-card-text>
      </v-col>
      <v-col class="pa-0" cols="6" sm="8">
        <v-row justify="end" align="center">
          <v-btn
            @click="imageDialogue = !imageDialogue"
            v-show="editable"
            class="mr-2 success"
            :small="$vuetify.breakpoint.smAndUp"
            :x-small="$vuetify.breakpoint.xsOnly"
          >
            Add
          </v-btn>
          <v-btn
            :small="$vuetify.breakpoint.smAndUp"
            :x-small="$vuetify.breakpoint.xsOnly"
            class="mr-6 secondary"
          >
            explore more
          </v-btn>
        </v-row>
      </v-col>
    </v-row>
    <v-row v-show="user.images">
      <v-col cols="6" sm="4" v-for="(image, i) in images" :key="i">
        <image-card
          :image="image"
          :editable="editable"
          @on-menu-click="onImageMenuClick"
        />
      </v-col>
    </v-row>
    <v-dialog width="600" v-model="imageDialogue">
      <v-card flat>
        <image-form
          :clear="clearImageForm"
          :uploading="savingImage"
          @close="imageDialogue = !imageDialogue"
          @save="saveImage"
        />
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="editImageDialogue">
      <v-card flat>
        <image-edit-form
          :image="imageEditData"
          :updating="updatingImage"
          @close="editImageDialogue = !editImageDialogue"
          @save="updateImage"
        />
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="deleteImageDialogue">
      <v-card flat>
        <confirm
          :loading="deletingImage"
          :model="imageEditData"
          :text="deleteImageText"
          @close="deleteImageDialogue = !deleteImageDialogue"
          @confirm="deleteImage"
        />
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import ImageCard from "../base/ImageCard";
import Confirm from "../base/Confirm";
import ImageForm from "../utils/form/ImageForm";
import ImageEditForm from "../utils/form/ImageEditForm";

export default {
  name: "image-section",
  components: {
    ImageCard,
    ImageEditForm,
    ImageForm,
    Confirm,
  },
  props: {
    user: {
      type: Object,
      required: true,
    },
    editable: {
      type: Boolean,
      required: true,
    },
  },
  data: function () {
    return {
      imageDialogue: false,
      clearImageForm: false,
      savingImage: false,

      imageEditData: null,
      editImageDialogue: false,
      updatingImage: false,

      deleteImageDialogue: false,
      deleteImageText: "Do you want to delete this image ?",
      deletingImage: false,
    };
  },
  computed: {
    images(){
      let images = this.user.images;
      if (images.length <= 3){
        return images;
      }else {
        return images.slice(0, 3);
      }
    }
  },
  methods: {
    saveImage(val) {
      this.savingImage = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("images/store", val)
        .then(() => {
          this.savingImage = false;
          this.imageDialogue = false;
          this.clearImageForm = true;
        })
        .catch(() => (this.savingImage = false));
      this.clearImageForm = false; // set again false so that it cab be cleared again
    },
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
    updateImage(val) {
      this.updatingImage = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("images/update", val)
        .then(() => {
          this.updatingImage = false;
          this.editImageDialogue = false;
        })
        .catch(() => {
          this.updatingImage = false;
        });
    },
    deleteImage(val) {
      this.deletingImage = true;
      this.$store
        .dispatch("images/delete", val)
        .then(() => {
          this.deletingImage = false;
          this.deleteImageDialogue = false;
        })
        .catch(() => {
          this.deletingImage = false;
        });
    },
  },
};
</script>
