<template>
  <div class="row justify-around q-pt-md" v-if="user">
    <div class="col-12 col-sm-3">
      <div class="row justify-center">
        <q-avatar
          color="primary"
          text-color="white"
          clickable
          @click="editProfilePic"
          :size="
            $q.screen.gt.md
              ? '200px'
              : $q.screen.md
              ? '150px'
              : $q.screen.sm
              ? '100px'
              : '75px'
          "
        >
          <user-image :user="user" />
          <q-badge floating color="teal" v-show="editable">Change</q-badge>
        </q-avatar>
      </div>
    </div>
    <div class="col-12 col-sm-9 self-center">
      <div class="column items-center">
        <div class="col">
          <div
            :class="{
              'text-h6': $q.screen.gt.sm,
              'text-subtitle2': $q.screen.lt.md,
              'text-weight-bold': true
            }"
          >
            {{ user.name }}
            <q-btn
              @click="profileDialogue = !profileDialogue"
              v-show="editable"
              class="q-ma-2"
              size="sm"
            >
              Edit
            </q-btn>
            <q-chip
              v-show="!editable && user.phoneNumber"
              clickable
              square
              color="positive"
              text-color="white"
              @click="contactDialogue = true"
            >
              Contact
            </q-chip>
          </div>
        </div>
        <div class="col q-pa-md text-center">
          <div class="text-weight-regular text-caption text-italic">
            {{ user.description }}
          </div>
        </div>
        <div class="col">
          <div class="row" v-if="!user.interest && editable">
            <q-btn
              @click="interestDialogue = true"
              size="sm"
              color="positive"
              class="q-mt-md"
            >
              What are you interested in ?
            </q-btn>
          </div>
        </div>
        <div class="col q-mt-md text-center" v-if="user.interest">
          <q-chip
            square
            color="primary"
            text-color="white"
            v-if="user.interest.blogger"
          >
            Blogger
          </q-chip>
          <q-chip
            square
            color="primary"
            text-color="white"
            v-if="user.interest.trainer"
          >
            Yoga Trainer
          </q-chip>
          <q-chip
            square
            color="primary"
            text-color="white"
            v-if="user.interest.lookingForTrainer"
          >
            I am looking for a trainer
          </q-chip>
        </div>
        <div class="col q-mt-md">
          <div v-if="user.interest && editable" class="row justify-center">
            <q-btn
              @click="interestDialogue = true"
              size="sm"
              color="positive"
              class="q-mt-md"
            >
              Edit Your Interest
            </q-btn>
          </div>
        </div>
      </div>
    </div>
    <q-dialog v-model="profilePicDialogue" :maximized="$q.screen.xs">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.sm ? '400px' : ''
        }"
      >
        <profile-pic-form
          @close="profilePicDialogue = !profilePicDialogue"
          :user="user"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="profileDialogue" :maximized="$q.screen.xs">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.sm ? '850px' : ''
        }"
      >
        <user-form @close="profileDialogue = !profileDialogue" :user="user" />
      </q-card>
    </q-dialog>
    <q-dialog v-model="interestDialogue">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.sm ? '400px' : ''
        }"
      >
        <interests-form
          @close="interestDialogue = !interestDialogue"
          :user="user"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="contactDialogue">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.sm ? '600px' : ''
        }"
      >
        <q-toolbar class="bg-primary text-white">
          <q-toolbar-title>Contact Details</q-toolbar-title>
          <q-btn flat round icon="close" v-close-popup />
        </q-toolbar>

        <q-card-section class="q-pt-none text-center text-bold q-mt-md">
          Mobile <br />
          {{ user.phoneNumber }}
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import UserForm from "components/profile/UserForm";
import ProfilePicForm from "components/profile/ProfilePicForm";
import InterestsForm from "components/profile/InterestsForm";
import UserImage from "components/base/UserImage";

export default {
  name: "User",
  props: {
    user: {
      type: Object,
      required: true
    },
    trainer: {
      type: Object
    },
    editable: {
      type: Boolean,
      required: false
    }
  },
  components: {
    InterestsForm,
    ProfilePicForm,
    UserForm,
    UserImage
  },
  data: function() {
    return {
      profileDialogue: false,
      altProfilePic: "/images/util/profile_picture_blank_portrait.png",
      isTrainerCheckBox: !!this.trainer,

      profilePicDialogue: false,

      interestDialogue: false,
      updatingInterests: false,

      contactDialogue: false
    };
  },
  computed: {},
  methods: {
    editProfilePic() {
      if (this.editable) {
        this.profilePicDialogue = true;
      }
    }
  }
};
</script>

<style scoped></style>
