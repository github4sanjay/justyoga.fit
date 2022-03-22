<template>
  <v-card hover class="mx-auto" max-width="600" @click="onClickCard">
    <v-img :aspect-ratio="16 / 9" :src="video.coverPic">
      <v-container fill-height class="align-end pa-0">
        <v-chip class="ma-2 pa-1" label small>
          {{ video.duration }}
        </v-chip>
        <v-spacer />
        <v-btn
          color="orange"
          class="ma-1 white--text"
          fab
          x-small
          router
          :to="`/videos/${video.id}`"
        >
          <v-icon>play_arrow</v-icon>
        </v-btn>
      </v-container>
    </v-img>
    <v-card-actions>
      <div class="subtitle-2">
        {{ video.title }}
      </div>
      <v-spacer />
      <v-menu bottom left v-if="editable">
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            @click="onMenuClick(item)"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "VideoCard",
  props: {
    video: {
      type: Object,
      required: true,
    },
    editable: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      items: [{ title: "Edit" }, { title: "Delete" }],
    };
  },
  methods: {
    onMenuClick(item) {
      this.$emit("on-menu-click", { item: item.title, data: this.video });
    },
    onClickCard() {
      this.$router.push(`/videos/${this.video.id}`);
    },
  },
};
</script>

<style scoped></style>
