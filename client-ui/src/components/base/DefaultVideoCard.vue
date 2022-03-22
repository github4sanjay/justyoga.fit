<template>
  <q-card class="bg-blue-grey-10">
    <q-img :ratio="16 / 9" contain src="~assets/white_logo_only.png" basic>
      <q-btn
        class="absolute all-pointer-events"
        :size="$q.screen.lt.md ? 'sm' : 'md'"
        name="info"
        color="accent"
        style="bottom: 8px; right: 8px"
        dense
        round
        icon="play_arrow"
      >
      </q-btn>
    </q-img>
  </q-card>
  <!-- <v-card hover class="mx-auto grey darken-3" max-width="600">
    <v-img
      contain
      :aspect-ratio="16 / 9"
      :src="require('@/assets/white_logo_only.png')"
    >
      <v-container fill-height class="align-end pa-0">
        <v-spacer />
        <v-btn color="red" class="ma-1 white--text" fab x-small>
          <v-icon x-small>play_arrow</v-icon>
        </v-btn>
      </v-container>
    </v-img>
  </v-card> -->
</template>

<script>
export default {
  name: "DefaultVideoCard",
  props: {
    video: {
      type: Object,
      required: true
    },
    editable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      items: [{ title: "Edit" }, { title: "Delete" }]
    };
  },
  methods: {
    onMenuClick(item) {
      this.$emit("on-menu-click", { item: item.title, data: this.video });
    },
    onClickCard() {
      this.$router.push(`/videos/${this.video.id}`);
    }
  }
};
</script>

<style scoped></style>
