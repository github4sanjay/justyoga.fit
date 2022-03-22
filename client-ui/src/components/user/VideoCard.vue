<template>
  <div class="q-pa-sm row items-start" clickable>
    <q-card class="my-card" style="width: 100%" square>
      <q-img :src="video.coverPic" :ratio="16 / 9" style="width: 100%">
        <div
          class="absolute transparent"
          :style="{
            bottom: '0px',
            left: '-10px',
            height: $q.screen.lt.md ? '50px' : '60px'
          }"
        >
          <q-chip
            dark
            :class="{
              'bg-accent': true
            }"
            square
            label
            :size="$q.screen.lt.md ? 'sm' : 'md'"
          >
            {{ video.duration }}
          </q-chip>
        </div>
        <q-btn
          class="absolute all-pointer-events"
          :size="$q.screen.lt.md ? 'sm' : 'md'"
          name="info"
          color="accent"
          style="bottom: 8px; right: 8px"
          dense
          round
          icon="play_arrow"
          :to="`/videos/${video.id}`"
        >
        </q-btn>
      </q-img>
      <q-card-actions :class="{ 'q-pa-sm': $q.screen.lt.md }">
        <div>
          {{ video.title }}
        </div>
        <q-space />
        <q-btn
          v-if="editable"
          size="sm"
          name="info"
          color="grey"
          dense
          flat
          round
          icon="fas fa-ellipsis-v"
        >
          <q-menu transition-show="jump-down" transition-hide="jump-up">
            <q-list style="min-width: 100px">
              <q-item
                clickable
                v-for="(item, i) in items"
                :key="i"
                @click="onMenuClick(item)"
              >
                <q-item-section>{{ item.title }}</q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-btn>
      </q-card-actions>
    </q-card>
  </div>
</template>

<script>
export default {
  name: "VideoCard",
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
