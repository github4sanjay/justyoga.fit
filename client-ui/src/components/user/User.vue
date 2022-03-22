<template>
  <div class="row">
    <div class="col-12">
      <q-card square flat bordered>
        <div class="row text-center q-pt-md" v-if="user">
          <div class="col-12">
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
              <q-badge floating color="secondary" v-show="editable"
                >Change</q-badge
              >
            </q-avatar>
          </div>
          <div class="col-12 q-mt-sm">
            <div
              :class="{
                'text-weight-bold': true
              }"
            >
              {{ user.name }}
              <q-btn
                @click="profileDialogue = !profileDialogue"
                v-show="editable"
                class="q-ma-2"
                size="xs"
              >
                Edit
              </q-btn>
              <q-chip
                v-show="!editable && user.phoneNumber"
                clickable
                square
                color="positive"
                text-color="white"
                size="sm"
                @click="contactDialogue = true"
              >
                Contact
              </q-chip>
            </div>
          </div>
          <div class="col-12">
            <div class="q-pa-md text-weight-regular text-caption text-italic">
              {{ user.description }}
            </div>
          </div>
          <div class="col-12 q-mb-sm" v-if="!editable">
            <review-and-bookmark
              :editable="editable"
              :user="user"
              :currentUser="currentUser ? currentUser : {}"
            />
          </div>
        </div>
      </q-card>
    </div>

    <div class="col-12 q-mt-sm">
      <q-card square flat bordered>
        <q-card-actions class="q-pa-sm">
          <div class="text-bold">
            Interests
          </div>
          <q-space />
          <q-btn
            @click="interestDialogue = true"
            v-show="editable"
            class="q-ma-2 q-px-sm q-mr-sm"
            size="xs"
          >
            Edit
          </q-btn>
        </q-card-actions>

        <div class="column items-center">
          <div class="col q-mb-md text-center" v-if="user.interest">
            <q-chip
              dense
              square
              color="primary"
              text-color="white"
              v-if="user.interest.blogger"
            >
              Blogger
            </q-chip>
            <q-chip
              dense
              square
              color="primary"
              text-color="white"
              v-if="user.interest.trainer"
            >
              Yoga Trainer
            </q-chip>
            <q-chip
              dense
              square
              color="primary"
              text-color="white"
              v-if="user.interest.lookingForTrainer"
            >
              I am looking for a trainer
            </q-chip>
          </div>
        </div>
      </q-card>
    </div>

    <div class="col-12 q-mt-sm">
      <expertise-section :user="user" :editable="editable" />
    </div>

    <div class="col-12 q-mt-sm">
      <q-card square flat bordered>
        <div class="row">
          <div class="col-12 text-left">
            <basic-info-section :user="user" :editable="editable" />
          </div>
          <div
            class="col-12 q-mt-sm"
            v-if="
              user.basicInfo &&
                user.basicInfo.latitude &&
                user.basicInfo.longitude
            "
          >
            <profile-map :basicInfo="user.basicInfo" class="profile-map" />
          </div>
        </div>
      </q-card>
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
import BasicInfoSection from "components/user/BasicInfoSection";
import ProfilePicForm from "components/profile/ProfilePicForm";
import InterestsForm from "components/profile/InterestsForm";
import UserImage from "components/base/UserImage";
import ProfileMap from "components/user/ProfileMap";
import ReviewAndBookmark from "components/profile/ReviewAndBookmark";
import ExpertiseSection from "components/user/ExpertiseSection";
import { mapGetters } from "vuex";

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
    UserImage,
    BasicInfoSection,
    ProfileMap,
    ReviewAndBookmark,
    ExpertiseSection
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
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    })
  },
  methods: {
    editProfilePic() {
      if (this.editable) {
        this.profilePicDialogue = true;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.profile-map {
  height: 200px;
}
</style>
