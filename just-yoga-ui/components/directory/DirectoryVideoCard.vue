<template>
  <v-card outlined class="mx-auto">
    <v-list-item>
      <v-list-item-avatar color="grey">
        <base-avatar :name="videoInfo.name" :url="videoInfo.profilePic" size="50"/>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="headline">
          {{videoInfo.title}}
        </v-list-item-title>
        <v-list-item-subtitle>{{time}}, by {{videoInfo.name}}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-img
      class="primary"
      :src="videoInfo.coverPic"
      height="244"
    >
      <template v-slot:placeholder>
       <image-loader/>
      </template>
      <v-container fill-height class="align-end pa-0" @click="onClickCard">
        <v-chip class="ma-2 pa-1" label small>
          {{ videoInfo.duration }}
        </v-chip>
        <v-spacer />
        <v-btn
          color="orange"
          class="ma-1 white--text"
          fab
          x-small
          router
          :to="`/videos/${videoInfo.id}`"
        >
          <v-icon>play_arrow</v-icon>
        </v-btn>
      </v-container>
    </v-img>
    <v-card-text>
     {{videoInfo.description}}
    </v-card-text>
    <v-card-actions>
      <v-btn router :to="`/videos/${videoInfo.id}`" text color="deep-purple accent-4">
        Details
      </v-btn>
      <v-btn text color="deep-purple accent-4">
        Bookmark
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import BaseAvatar from "../base/BaseAvatar";
import TImeUtil from "../../utils/TImeUtil";
import ImageLoader from "../base/ImageLoader";
export default {
  name: "DirectoryVideoCard",
  components: {ImageLoader, BaseAvatar},
  props: {
    videoInfo: {
      type: Object,
      required: true,
    },
  },
  methods: {
    onClickCard() {
      this.$router.push(`/videos/${this.videoInfo.id}`);
    },
  },
  computed: {
    time(){
      let time = TImeUtil.timeDifference(this.videoInfo.updatedAt);
      return time  + " ago";
    }
  }
};
</script>

<style scoped></style>
