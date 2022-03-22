<template>
  <div>
    <div class="row">
      <div class="col-6 col-sm-4" v-for="(video, i) in videos" :key="i">
        <video-card
          :video="
            typeof video === 'string'
              ? $store.getters['videos/byId'](video)
              : video
          "
          :editable="editable"
          @on-menu-click="onVideoMenuClick"
        />
      </div>
    </div>

    <q-dialog v-model="editVideoDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.sm ? '850px' : ''
        }"
      >
        <video-edit-form
          :video="videoEditData"
          :user="user"
          @close="editVideoDialogue = !editVideoDialogue"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="deleteVideoDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.sm ? '650px' : ''
        }"
      >
        <confirm
          :loading="deletingVideo"
          :model="videoEditData"
          :text="deleteVideoText"
          @close="deleteVideoDialogue = !deleteVideoDialogue"
          @confirm="deleteVideo"
        />
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import VideoCard from "components/user/VideoCard";
import VideoEditForm from "components/user/VideoEditForm";
import Confirm from "components/base/Confirm";

export default {
  name: "VideoSection",
  components: {
    VideoCard,
    Confirm,
    VideoEditForm
  },
  props: {
    videos: {
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
      videoEditData: null,
      editVideoDialogue: false,

      deleteVideoDialogue: false,
      deleteVideoText: "Do you want to delete this video ?",
      deletingVideo: false
    };
  },
  methods: {
    onVideoMenuClick(val) {
      switch (val.item) {
        case "Edit":
          this.editVideoDialogue = true;
          this.videoEditData = val.data;
          break;
        case "Delete":
          this.deleteVideoDialogue = true;
          this.videoEditData = val.data;
          break;
      }
    },
    deleteVideo(val) {
      this.deletingVideo = true;
      this.$store
        .dispatch("videos/remove", val)
        .then(() => {
          this.deletingVideo = false;
          this.deleteVideoDialogue = false;
        })
        .catch(() => (this.deletingVideo = false));
    }
  }
};
</script>
