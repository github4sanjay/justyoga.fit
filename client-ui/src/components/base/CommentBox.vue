<template>
  <div>
    <div class="row q-pa-sm">
      <div class="col-3 col-sm-2">
        <q-avatar
          color="primary"
          text-color="white"
          :size="$q.screen.gt.sm ? '100px' : '75px'"
        >
          <user-image :user="user" />
        </q-avatar>
      </div>
      <div class="col-9 col-sm-10">
        <q-form ref="commentForm">
          <q-card flat>
            <q-input
              outlined
              filled
              type="textarea"
              v-model="comment"
              label="Comment here"
              maxlength="500"
              counter
              :rules="[
                val => !!val || '* Required',
                val => val.length <= 500 || 'Max 500 characters'
              ]"
              lazy-rules
            />
            <q-card-actions>
              <q-space />
              <q-btn
                unelevated
                text
                :loading="saving"
                color="secondary"
                @click="sendMessage"
              >
                Comment
              </q-btn>
            </q-card-actions>
          </q-card>
        </q-form>
      </div>
    </div>
  </div>
</template>

<script>
import UserImage from "components/base/UserImage";

export default {
  name: "CommentBox",
  components: { UserImage },
  props: {
    saving: {
      type: Boolean,
      required: true
    },
    defaultText: {
      type: String,
      required: false
    },
    clear: {
      type: Boolean,
      required: false
    },
    user: {
      type: Object,
      required: true
    }
  },
  data: () => ({
    avatarSize: 75,
    comment: ""
  }),
  created: function() {
    this.comment = this.defaultText;
  },
  computed: {},
  methods: {
    sendMessage() {
      this.$refs.commentForm.validate().then(success => {
        if (success) {
          this.$emit("submit", this.comment);
        } else {
          return;
        }
      });
    }
  },
  watch: {
    clear(val) {
      if (val) {
        this.$refs.commentForm.resetValidation();
        this.comment = "";
      }
    }
  }
};
</script>

<style scoped></style>
