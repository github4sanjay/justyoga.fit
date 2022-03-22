<template>
  <q-card square flat bordered>
    <q-item>
      <q-item-section>
        <q-item-label
          :class="{
            'text-h6': $q.screen.gt.sm,
            'text-subtitle2': $q.screen.lt.md
          }"
        >
          {{ collection.name }}
          <!-- <q-chip square color="secondary" text-color="white" icon="star">
            8
          </q-chip> -->
        </q-item-label>
      </q-item-section>
    </q-item>
    <q-img
      :src="collection.coverUrl"
      style="height: 200px;"
      basic
      native-context-menu
    >
      <div class="absolute-bottom text-subtitle1 text-center">
        <q-chip size="12px" square>
          <q-avatar color="secondary" text-color="white">{{
            collection.videoCount
          }}</q-avatar>
          Videos
        </q-chip>
        <q-chip size="12px" square>
          <q-avatar color="secondary" text-color="white">{{
            collection.imageCount
          }}</q-avatar>
          Images
        </q-chip>
        <q-chip size="12px" square>
          <q-avatar color="secondary" text-color="white">{{
            collection.blogCount
          }}</q-avatar>
          Blogs
        </q-chip>
      </div>
    </q-img>
    <q-card-section>
      <v-clamp autoresize hyphens :max-lines="3">
        {{ collection.description }}
        <template #after="{ toggle, expanded, clamped }">
          <q-btn flat size="xs" v-if="expanded || clamped" @click="toggle">
            Read more
          </q-btn>
        </template>
      </v-clamp>
    </q-card-section>
    <q-separator />
    <q-card-actions>
      <q-btn
        color="primary"
        flat
        label="Details"
        :to="`/collection-detail/${collection.id}`"
      />
      <q-space />
      <q-btn
        size="sm"
        dense
        flat
        round
        color="grey"
        icon="share"
        @click="shareCopy()"
      />
      <q-btn
        v-show="isAdmin"
        size="sm"
        dense
        flat
        round
        color="grey"
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
</template>

<script>
import config from "../../../config";
import VClamp from "vue-clamp";
import CopyUtil from "src/utils/CopyUtil";

export default {
  name: "CollectionIndex",
  components: { VClamp },
  props: {
    collection: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      items: [
        { title: "Edit Details", CODE: "EDIT_DETAILS" },
        { title: "Edit Cover", CODE: "EDIT_COVER" },
        { title: "Delete", CODE: "DELETE" }
      ]
    };
  },
  methods: {
    onClickCard() {
      //this.$router.push(`/profile/${this.userInfo.id}/details`);
    },
    shareCopy() {
      CopyUtil.copyToClipboard(
        `${config.uiUrl}/collection-detail/${this.collection.id}`
      );
    },
    onMenuClick(item) {
      this.$emit("on-menu-click", { item: item.CODE, data: this.collection });
    }
  },
  computed: {
    isAdmin() {
      return this.$store.getters["login/isUserAdmin"];
    }
  }
};
</script>
