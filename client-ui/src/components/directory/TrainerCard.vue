<template>
  <div>
    <q-card @click="onClickCard" square flat bordered>
      <q-item>
        <q-item-section>
          <q-item-label
            :class="{
              'text-capitalize': true,
              'text-h6': $q.screen.gt.sm,
              'text-subtitle2': $q.screen.lt.md
            }"
          >
            {{ userInfo.name }}
            <q-chip square color="secondary" text-color="white" icon="star">
              {{ rating }}
            </q-chip>
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-img
        :src="userInfo.profilePic"
        style="height: 250px;"
        basic
        native-context-menu
      >
        <div
          v-if="userInfo.description"
          class="absolute-bottom text-subtitle1 text-center"
        >
          <v-clamp autoresize hyphens :max-lines="2">
            {{ userInfo.description }}
          </v-clamp>
        </div>
      </q-img>
      <q-separator />
      <q-card-section class="text-center">
        <q-chip size="12px" square>
          <q-avatar color="secondary" text-color="white">{{
            userInfo.age
          }}</q-avatar>
          years old
        </q-chip>
        <q-chip size="12px" square>
          <q-avatar color="secondary" text-color="white">{{
            userInfo.experienceYears
          }}</q-avatar>
          years of experience
        </q-chip>
        <q-chip
          size="12px"
          square
          clickable
          color="grey"
          text-color="white"
          v-on:click.stop="showingMedicalExpertise()"
        >
          <q-avatar color="secondary" text-color="white">{{
            medicalExpertise.length
          }}</q-avatar>
          Medical Expertise
          <q-menu
            transition-show="scale"
            transition-hide="scale"
            v-model="showingMedicalExpertise"
          >
            <q-list style="min-width: 100px">
              <q-item v-for="item in medicalExpertise" :key="item">
                <q-item-section>{{ item }}</q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-chip>
        <q-chip
          size="12px"
          square
          clickable
          color="grey"
          text-color="white"
          v-on:click.stop="showingYogaExpertise()"
        >
          <q-avatar color="secondary" text-color="white">{{
            yogaExpertise.length
          }}</q-avatar>
          Yoga Expertise
          <q-menu
            transition-show="scale"
            transition-hide="scale"
            v-model="showingYogaExpertise"
          >
            <q-list style="min-width: 100px">
              <q-item v-for="item in yogaExpertise" :key="item">
                <q-item-section>{{ item }}</q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-chip>
        <q-chip
          size="12px"
          square
          clickable
          color="grey"
          text-color="white"
          v-on:click.stop="showingYogaCertificate()"
        >
          <q-avatar color="secondary" text-color="white">{{
            yogaCertificate.length
          }}</q-avatar>
          Yoga Certificate
          <q-menu
            transition-show="scale"
            transition-hide="scale"
            v-model="showingYogaCertificate"
          >
            <q-list style="min-width: 100px">
              <q-item v-for="item in yogaCertificate" :key="item">
                <q-item-section>{{ item }}</q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-chip>
      </q-card-section>
      <q-separator />
      <q-card-actions>
        <q-btn
          flat
          color="primary"
          label="Details"
          :to="`/users/${userInfo.id}/detail`"
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
import CopyUtil from "src/utils/CopyUtil";
import config from "../../../config";
import VClamp from "vue-clamp";

export default {
  name: "TrainerCard",
  components: {
    VClamp
  },
  props: {
    userInfo: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      showingMedicalExpertise: false,
      showingYogaExpertise: false,
      showingYogaCertificate: false
    };
  },
  methods: {
    onClickCard() {
      this.$router.push(`/users/${this.userInfo.id}/detail`);
    },
    shareCopy() {
      CopyUtil.copyToClipboard(
        `${config.uiUrl}/users/${this.userInfo.id}/detail`
      );
    }
  },
  computed: {
    medicalExpertise() {
      let expertise = [];
      let medicalExpertise = this.userInfo.medicalExpertise;
      if (medicalExpertise) {
        expertise.push(...medicalExpertise.split("|"));
      }
      return expertise;
    },
    yogaExpertise() {
      let expertise = [];
      let yogaExpertise = this.userInfo.yogaExpertise;
      if (yogaExpertise) {
        expertise.push(...yogaExpertise.split("|"));
      }
      return expertise;
    },
    yogaCertificate() {
      let expertise = [];
      let yogaCertificate = this.userInfo.yogaCertificate;
      if (yogaCertificate) {
        expertise.push(...yogaCertificate.split("|"));
      }
      return expertise;
    },
    rating() {
      if (this.userInfo.rating) {
        return Math.round(this.userInfo.rating * 10) / 10;
      } else {
        return "No Review";
      }
    }
  }
};
</script>

<style scoped></style>
