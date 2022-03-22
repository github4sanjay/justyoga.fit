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
            <user-image :user="imageInfo" />
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
              {{ imageInfo.title }}
            </v-clamp>
          </q-item-label>
          <q-item-label caption>
            {{ time }}, by {{ imageInfo.name }}
            <q-chip dense class="q-px-sm">Photo</q-chip>
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-card-section class="q-pa-none bg-dark">
        <q-img contain :src="imageInfo.url" ratio="1.78" />
      </q-card-section>
      <q-card-section>
        <v-clamp autoresize hyphens :max-lines="2">
          {{ imageInfo.description }}
        </v-clamp>
      </q-card-section>
      <q-separator />
      <q-card-actions>
        <q-btn
          flat
          color="primary"
          label="Details"
          :to="`/images/${imageInfo.id}`"
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
  name: "DirectoryImageCard",
  components: { UserImage, VClamp },
  props: {
    imageInfo: {
      type: Object,
      required: true
    }
  },
  methods: {
    onClickCard() {
      this.$router.push(`/images/${this.imageInfo.id}`);
    },
    shareCopy() {
      CopyUtil.copyToClipboard(`${config.uiUrl}/images/${this.imageInfo.id}`);
    }
  },
  computed: {
    time() {
      return TimeUtil.timeDifference(this.imageInfo.updatedAt);
    }
  }
};
</script>

<style scoped></style>
