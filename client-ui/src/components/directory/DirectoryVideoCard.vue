<template>
  <div>
    <q-card @click="onClickCard" square flat bordered>
      <q-item>
        <q-item-section avatar>
          <q-avatar
            color="primary"
            text-color="white"
            :size="$q.screen.gt.sm ? '65px' : '50px'"
          >
            <user-image :user="videoInfo" />
          </q-avatar>
        </q-item-section>
        <q-item-section>
          <q-item-label
            :class="{
              'text-h6': $q.screen.gt.sm,
              'text-subtitle2': $q.screen.lt.md
            }"
          >
            <v-clamp autoresize hyphens :max-lines="1">
              {{ videoInfo.title }}
            </v-clamp>
          </q-item-label>
          <q-item-label caption>
            {{ time }}, by {{ videoInfo.name }}
            <q-chip dense class="q-px-sm">Video</q-chip>
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-card-section class="q-pa-none">
        <q-media-player
          content-style="max-height: 300px"
          type="video"
          background-color="black"
          :show-big-play-button="true"
          :source="videoInfo.url"
          track-language="English"
          :poster="videoInfo.coverPic"
        >
          <template v-slot:overlay>
            <div class="q-pa-sm">
              <img
                src="~assets/white_logo_only.png"
                style="width: 30vw; max-width: 50px; opacity: 1;"
              />
            </div>
          </template>
        </q-media-player>
      </q-card-section>
      <q-card-section>
        <v-clamp autoresize hyphens :max-lines="2">
          {{ videoInfo.description }}
        </v-clamp>
      </q-card-section>
      <q-separator />
      <q-card-actions>
        <q-btn
          flat
          color="primary"
          label="Details"
          :to="`/videos/${videoInfo.id}`"
        />
        <q-space />
        <q-btn
          size="md"
          flat
          round
          color="grey"
          icon="share"
          v-on:click.stop="shareCopy()"
        />
      </q-card-actions>
    </q-card>
  </div>
</template>

<script>
import TimeUtil from "src/utils/TimeUtil";
import UserImage from "components/base/UserImage";
import CopyUtil from "src/utils/CopyUtil";
import config from "app/config";
import VClamp from "vue-clamp";

export default {
  name: "DirectoryVideoCard",
  components: { UserImage, VClamp },
  props: {
    videoInfo: {
      type: Object,
      required: true
    }
  },
  methods: {
    onClickCard() {
      this.$router.push(`/videos/${this.videoInfo.id}`);
    },
    shareCopy() {
      CopyUtil.copyToClipboard(`${config.uiUrl}/videos/${this.videoInfo.id}`);
    }
  },
  computed: {
    time() {
      return TimeUtil.timeDifference(this.videoInfo.updatedAt);
    }
  }
};
</script>

<style scoped></style>
