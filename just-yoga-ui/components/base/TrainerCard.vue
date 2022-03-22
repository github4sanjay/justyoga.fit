<template>
  <v-card outlined hover max-width="344" class="mx-auto" @click="onClickCard">
    <v-list-item class="py-0">
      <v-list-item-content class="py-0">
        <v-list-item-title class="title">
          {{ userInfo.name }}
          <v-chip
            small
            label
            class="ma-2"
            color="success darken-1"
            text-color="white"
          >
            <v-icon left small>mdi-star</v-icon>
            {{ rating }}
          </v-chip>
        </v-list-item-title>
        <v-list-item-subtitle> </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
    <v-img
      class="primary"
      :src="
        userInfo.profilePic
          ? userInfo.profilePic
          : require('@/assets/white_logo_only.png')
      "
      height="250"
    >
      <template v-slot:placeholder>
        <image-loader/>
      </template>
    </v-img>
    <v-container>
      <v-row dense justify="center" align="center">
        <v-col cols="4">
          <v-chip
            v-show="userInfo.age"
            small
            :x-small="$vuetify.breakpoint.xsOnly"
            label
          >
            <v-icon small :x-small="$vuetify.breakpoint.xsOnly" left
              >stars
            </v-icon>
            {{ userInfo.age }} years old
          </v-chip>
        </v-col>
        <v-col cols="8">
          <v-chip
            v-show="userInfo.experienceYears"
            small
            :x-small="$vuetify.breakpoint.xsOnly"
            label
          >
            <v-icon small :x-small="$vuetify.breakpoint.xsOnly" left
              >explicit
            </v-icon>
            {{ userInfo.experienceYears }} years of experience
          </v-chip>
        </v-col>
      </v-row>
    </v-container>
    <v-card-text class="py-0">
      {{ userInfo.description }}
    </v-card-text>
    <v-divider class="mt-2"></v-divider>
    <v-card-subtitle v-if="expertise.length > 0" class="py-0 mt-3"
      >Expertise in</v-card-subtitle
    >
    <v-card-text class="py-0">
      <v-chip-group column>
        <v-chip
          v-for="medicalExpertise in expertise"
          :key="medicalExpertise"
          small
          :x-small="$vuetify.breakpoint.xsOnly"
          label
        >
          {{ medicalExpertise }}
        </v-chip>
      </v-chip-group>
    </v-card-text>
    <v-card-actions>
      <v-btn
        router
        :to="`/profile/${userInfo.id}/details`"
        text
        color="deep-purple accent-4"
      >
        Details
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn icon v-on:click.stop="shareCopy">
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import config from "@/config.js";
import ImageLoader from "./ImageLoader";
export default {
  name: "TrainerCard",
  components: {ImageLoader},
  props: {
    userInfo: {
      type: Object,
      required: true,
    },
  },
  methods: {
    onClickCard() {
      this.$router.push(`/profile/${this.userInfo.id}/details`);
    },
    async shareCopy() {
      try {
        await this.$copyText(
          `${config.uiUrl}/profile/${this.userInfo.id}/details`
        );
        await this.$store.dispatch(
          "shared/setSuccessText",
          "Copied"
        );
        await this.$store.dispatch("shared/setSuccessSnackbar", true);
      } catch (e) {
        await this.$store.dispatch(
          "shared/setErrorText",
          "Something went wrong"
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
      }
    },
  },
  computed: {
    expertise() {
      let expertise = [];
      let medicalExpertise = this.userInfo.medicalExpertise;
      let yogaCertificate = this.userInfo.yogaCertificate;
      let yogaExpertise = this.userInfo.yogaExpertise;
      if (medicalExpertise) {
        expertise.push(...medicalExpertise.split(","));
      }
      if (yogaCertificate) {
        expertise.push(...yogaCertificate.split(","));
      }
      if (yogaExpertise) {
        expertise.push(...yogaExpertise.split(","));
      }
      return expertise;
    },
    rating() {
      if (this.userInfo.rating) {
        return Math.round(this.userInfo.rating * 10) / 10;
      } else {
        return "Not Reviewed";
      }
    },
  },
};
</script>

<style scoped></style>
