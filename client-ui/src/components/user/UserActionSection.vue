<template>
  <div>
    <q-card flat>
      <q-card-actions class="justify-center">
        <div
          :class="{
            'text-bold': true
          }"
        >
          {{
            $q.screen.gt.sm
              ? "Share your yoga experiences here"
              : $q.screen.sm
              ? "Share your experience"
              : ""
          }}
        </div>
        <q-space v-show="$q.screen.gt.xs" />
        <div class="row">
          <div class="col-12">
            <div>
              <q-btn
                unelevated
                class="q-mx-sm"
                color="grey"
                label="Add Blog"
                @click="blogDialogue = true"
              />
              <q-btn
                unelevated
                class="q-mx-sm"
                color="grey"
                label="Add Photo"
                @click="imageDialogue = true"
              />
              <q-btn
                unelevated
                class="q-mx-sm"
                color="grey"
                label="Add Video"
                @click="videoDialogue = true"
              />
            </div>
          </div>
        </div>
      </q-card-actions>
    </q-card>
    <q-dialog v-model="blogDialogue" :maximized="$q.screen.xs">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '650px' : ''
        }"
      >
        <blog-form :user="user" @close="blogDialogue = !blogDialogue" />
      </q-card>
    </q-dialog>
    <q-dialog v-model="imageDialogue" :maximized="$q.screen.xs">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '650px' : ''
        }"
      >
        <image-form :user="user" @close="imageDialogue = !imageDialogue" />
      </q-card>
    </q-dialog>
    <q-dialog v-model="videoDialogue" :maximized="$q.screen.xs">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '650px' : ''
        }"
      >
        <video-form :user="user" @close="videoDialogue = !videoDialogue" />
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import ImageForm from "components/user/ImageForm";
import VideoForm from "components/user/VideoForm";
import BlogForm from "components/user/BlogForm";

export default {
  name: "UserActionSection",
  components: {
    BlogForm,
    ImageForm,
    VideoForm
  },
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  data: function() {
    return {
      blogDialogue: false,
      imageDialogue: false,
      videoDialogue: false
    };
  },
  methods: {}
};
</script>
