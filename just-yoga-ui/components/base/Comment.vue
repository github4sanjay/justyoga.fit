<template>
  <v-row align="center">
    <v-col cols="3" sm="2" class="text-center">
      <base-avatar :url="user.profilePic" :name="user.name" size="50" />
    </v-col>
    <v-col cols="10" sm="9">
      <v-row dense>
        <v-col class="subtitle-2" cols="9">
          {{ user.name }}
        </v-col>
        <v-col cols="3">
          <span
            class="pl-2 grey--text text--darken-3 font-weight-light caption"
          >
            {{ time }}
          </span>
        </v-col>
        <v-col class="body-2">
          {{ comment.text }}
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
import BaseAvatar from "./BaseAvatar";
import TImeUtil from "@/utils/TImeUtil";
export default {
  name: "Comment",
  components: { BaseAvatar },
  props: {
    comment: {
      type: Object,
      required: true,
    },
  },
  computed: {
    user() {
      return this.$store.getters["users/user"](this.comment.userId);
    },
    time() {
      let time = TImeUtil.timeDifference(this.comment.updatedAt);
      return time + " ago";
    },
  },
};
</script>

<style scoped></style>
