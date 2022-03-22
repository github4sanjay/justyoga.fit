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
          Latest Videos
        </v-card-text>
      </v-col>
      <v-col class="pa-0" cols="6" sm="8">
        <v-row justify="end" align="center">
          <v-btn
            @click="videoDialogue = !videoDialogue"
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
    <v-row v-show="user.videos">
      <v-col cols="6" sm="4" v-for="(video, i) in videos" :key="i">
        <video-card
          :video="video"
          :editable="editable"
          @on-menu-click="onVideoMenuClick"
        />
      </v-col>
    </v-row>
    <v-dialog width="600" v-model="videoDialogue">
      <v-card flat>
        <video-form
          :clear="clearVideoForm"
          :uploading="savingVideo"
          @close="videoDialogue = !videoDialogue"
          @save="saveVideo"
        />
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="editVideoDialogue">
      <v-card flat>
        <video-edit-form
          :video="videoEditData"
          :updating="updatingVideo"
          @close="editVideoDialogue = !editVideoDialogue"
          @save="updateVideo"
        />
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="deleteVideoDialogue">
      <v-card flat>
        <confirm
          :loading="deletingVideo"
          :model="videoEditData"
          :text="deleteVideoText"
          @close="deleteVideoDialogue = !deleteVideoDialogue"
          @confirm="deleteVideo"
        />
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import VideoCard from "../base/VideoCard";
import VideoForm from "../utils/form/VideoForm";
import VideoEditForm from "../utils/form/VideoEditForm";
import Confirm from "../base/Confirm";

export default {
  name: "VideoSection",
  components: { VideoCard, Confirm, VideoEditForm, VideoForm },
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
      videoEditData: null,
      editVideoDialogue: false,
      updatingVideo: false,

      videoDialogue: false,
      clearVideoForm: false,
      savingVideo: false,

      deleteVideoDialogue: false,
      deleteVideoText: "Do you want to delete this video ?",
      deletingVideo: false,
    };
  },
  computed: {
    videos(){
      let videos = this.user.videos;
      if (videos.length <= 3){
        return videos;
      }else {
        return videos.slice(0, 3);
      }
    }
  },
  methods: {
    saveVideo(val) {
      this.savingVideo = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("videos/store", val)
        .then(() => {
          this.savingVideo = false;
          this.videoDialogue = false;
          this.clearVideoForm = true;
        })
        .catch(() => (this.savingVideo = false));
      this.clearVideoForm = false; // set again false so that it cab be cleared again
    },
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
    updateVideo(val) {
      this.updatingVideo = true;
      val.userId = this.user.id;
      this.$store
        .dispatch("videos/update", val)
        .then(() => {
          this.updatingVideo = false;
          this.editVideoDialogue = false;
        })
        .catch(() => (this.updatingVideo = false));
    },
    deleteVideo(val) {
      this.deletingVideo = true;
      this.$store
        .dispatch("videos/delete", val)
        .then(() => {
          this.deletingVideo = false;
          this.deleteVideoDialogue = false;
        })
        .catch(() => (this.deletingVideo = false));
    },
  },
};
</script>
