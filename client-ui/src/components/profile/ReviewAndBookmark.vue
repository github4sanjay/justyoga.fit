<template>
  <div>
    <q-card flat>
      <div class="column items-center">
        <div class="col text-center">
          <q-btn
            unelevated
            size="sm"
            color="positive"
            label="Add Review"
            @click="clickBtn('REVIEW')"
          />
        </div>
      </div>
    </q-card>
    <q-dialog v-model="reviewDialogue" :maximized="$q.screen.xs">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.sm ? '800px' : ''
        }"
      >
        <review-form
          :user="user"
          :currentUser="currentUser"
          @close="reviewDialogue = false"
        />
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import Util from "../../utils/Util";
import ReviewForm from "./ReviewForm";
import NotifyUtil from "../../utils/NotifyUtil";

export default {
  name: "ReviewAndBookmark",
  components: {
    ReviewForm
  },
  props: {
    user: {
      type: Object,
      required: true
    },
    currentUser: {
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
      reviewDialogue: false,
      savingReview: false
    };
  },
  methods: {
    clickBtn(val) {
      switch (val) {
        case "REVIEW":
          if (Util.isObjectEmpty(this.currentUser)) {
            NotifyUtil.showError(`Please login to rate/review`);
          } else {
            this.reviewDialogue = true;
          }
          break;
        case "BOOKMARK":
          break;
      }
    }
  }
};
</script>

<style scoped></style>
