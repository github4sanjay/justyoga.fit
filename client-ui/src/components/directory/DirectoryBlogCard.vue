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
            <user-image :user="blogInfo" />
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
              {{ blogInfo.title }}
            </v-clamp>
          </q-item-label>
          <q-item-label caption>
            {{ time }}, by {{ blogInfo.name }}
            <q-chip dense class="q-px-sm">Blog</q-chip>
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-img
        :src="
          blogInfo.coverUrl
            ? blogInfo.coverUrl
            : require(`src/assets/yoga-poses.jpg`)
        "
        ratio="1.78"
        basic
        native-context-menu
      >
        <!-- <div class="absolute-bottom text-subtitle1 text-center">
          <q-chip size="12px" square>
            <q-avatar color="secondary" text-color="white">{{
              blogInfo.videoCount
            }}</q-avatar>
            Videos
          </q-chip>
          <q-chip size="12px" square>
            <q-avatar color="secondary" text-color="white">{{
              blogInfo.imageCount
            }}</q-avatar>
            Images
          </q-chip>
        </div> -->
      </q-img>
      <q-card-section>
        <v-clamp autoresize hyphens :max-lines="2">
          {{ blogInfo.blogText }}
        </v-clamp>
      </q-card-section>
      <q-separator />
      <q-card-actions>
        <q-btn
          flat
          color="primary"
          label="Details"
          :to="`/blogs/${blogInfo.id}`"
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
import UserImage from "components/base/UserImage";
import VClamp from "vue-clamp";
import CopyUtil from "src/utils/CopyUtil";
import config from "app/config";
import TimeUtil from "src/utils/TimeUtil";

export default {
  name: "DirectoryBlogCard",
  components: {
    UserImage,
    VClamp
  },
  props: {
    blogInfo: {
      type: Object,
      required: true
    }
  },
  methods: {
    onClickCard() {
      this.$router.push(`/blogs/${this.blogInfo.id}`);
    },
    shareCopy() {
      CopyUtil.copyToClipboard(`${config.uiUrl}/blogs/${this.blogInfo.id}`);
    }
  },
  computed: {
    time() {
      return TimeUtil.timeDifference(this.blogInfo.updatedAt);
    }
  }
};
</script>
