<template>
  <v-card hover outlined class="mx-auto" @click="onClickCard">
    <v-list-item>
      <v-list-item-avatar color="grey">
        <base-avatar :name="imageInfo.name" :url="imageInfo.profilePic" size="50"/>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="headline">
          {{imageInfo.title}}
        </v-list-item-title>
        <v-list-item-subtitle>{{time}}, by {{imageInfo.name}}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-img
      class="primary"
      :src="imageInfo.url"
    >
      <template v-slot:placeholder>
        <image-loader/>
      </template>
    </v-img>
    <v-card-text>
     {{imageInfo.description}}
    </v-card-text>
    <v-card-actions>
      <v-btn router :to="`/images/${imageInfo.id}`" text color="deep-purple accent-4">
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
  name: "DirectoryImageCard",
  components: {ImageLoader, BaseAvatar},
  props: {
    imageInfo: {
      type: Object,
      required: true,
    },
  },
  methods: {
    onClickCard() {
      this.$router.push(`/images/${this.imageInfo.id}`);
    },
  },
  computed: {
    time(){
      let time = TImeUtil.timeDifference(this.imageInfo.updatedAt);
      return time  + " ago";
    }
  }
};
</script>

<style scoped></style>
