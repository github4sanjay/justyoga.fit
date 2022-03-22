<template>
  <div>
    <div class="row q-pa-sm">
      <div class="col-2">
        <q-avatar
          color="primary"
          text-color="white"
          :size="$q.screen.gt.sm ? '75px' : '50px'"
        >
          <user-image :user="user" />
        </q-avatar>
      </div>
      <div class="col-10">
        <q-card flat>
          <q-item>
            <q-item-section>
              <q-item-label>{{ user.name }}</q-item-label>
              <q-item-label caption>
                {{ time }}
              </q-item-label>
            </q-item-section>
          </q-item>
          <q-card-section>
            {{ comment.text }}
          </q-card-section>
        </q-card>
      </div>
    </div>
  </div>
  <!-- <v-row align="center">
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
  </v-row> -->
</template>

<script>
import UserImage from "components/base/UserImage";
import TimeUtil from "src/utils/TimeUtil";
export default {
  name: "Comment",
  components: { UserImage },
  props: {
    comment: {
      type: Object,
      required: true
    }
  },
  computed: {
    user() {
      return this.$store.getters["users/user"](this.comment.userId);
    },
    time() {
      return TimeUtil.timeDifference(this.comment.updatedAt);
    }
  }
};
</script>

<style scoped></style>
