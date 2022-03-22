<template>
  <v-container>
    <v-row>
      <v-col cols="2" sm="2">
        <base-avatar
          :url="user.profilePic"
          :name="user.name"
          size="100"
        />
      </v-col>
      <v-col cols="10" sm="10">
        <v-container pb-0>
          <v-card flat>
            <v-textarea
              name="comment"
              label="Comment here"
              outlined
              auto-grow
              v-model="comment"
              :error-messages="nameErrors"
              :counter="500"
              @input="$v.comment.$touch()"
            ></v-textarea>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                text
                :loading="saving"
                color="secondary"
                @click="sendMessage"
              >
                Send
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, maxLength } from "vuelidate/lib/validators";
import BaseAvatar from "./BaseAvatar";

export default {
  mixins: [validationMixin],

  validations: {
    comment: { required, maxLength: maxLength(500) },
  },

  name: "CommentBox",
  components: { BaseAvatar },
  props: {
    saving: {
      type: Boolean,
      required: true,
    },
    defaultText: {
      type: String,
      required: false,
    },
    clear: {
      type: Boolean,
      required: false,
    },
    user: {
      type: Object,
      required: true,
    }
  },
  data: () => ({
    avatarSize: 75,
    comment: "",
  }),
  created: function () {
    this.comment = this.defaultText;
  },
  computed: {
    nameErrors() {
      const errors = [];
      if (!this.$v.comment.$dirty) return errors;
      !this.$v.comment.maxLength &&
        errors.push("Comment must be at most 500 characters long");
      !this.$v.comment.required && errors.push("Comment is required.");
      return errors;
    },
  },
  methods: {
    sendMessage() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.$emit("submit", this.comment);
      }
    },
  },
  watch: {
    clear(val) {
      if (val){
        this.$v.$reset();
        this.comment = "";
      }
    },
  },
};
</script>

<style scoped></style>
