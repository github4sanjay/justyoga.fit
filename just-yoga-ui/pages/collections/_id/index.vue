<template>
  <v-container>
    <v-card class="mx-auto mx-0">
      <v-list-item class="py-0">
        <v-list-item-content class="py-0">
          <v-list-item-title class="title">
            {{ collection.name }}
            <v-chip
              small
              label
              class="ma-2"
              color="success darken-1"
              text-color="white"
            >
              <v-icon left small>mdi-star</v-icon>
              8
            </v-chip>
          </v-list-item-title>
          <v-list-item-subtitle> </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-img
        class="primary"
        :src="
          collection.coverUrl
            ? collection.coverUrl
            : require('@/assets/white_logo_only.png')
        "
      >
        <template v-slot:placeholder>
          <image-loader />
        </template>
      </v-img>
      <v-card-text>
        <v-clamp autoresize hyphens :max-lines="3">
          {{ collection.description }}
        </v-clamp>
      </v-card-text>
      <v-divider class="mt-2"></v-divider>
      <!--<v-card-subtitle v-if="expertise.length > 0" class="py-0 mt-3">
      Expertise in
    </v-card-subtitle>-->
      <v-card-actions>
        <v-chip-group column>
          <v-chip small :x-small="$vuetify.breakpoint.xsOnly" label>
            <v-icon small :x-small="$vuetify.breakpoint.xsOnly" left
              >stars
            </v-icon>
            8 videos
          </v-chip>
          <v-chip small :x-small="$vuetify.breakpoint.xsOnly" label>
            <v-icon small :x-small="$vuetify.breakpoint.xsOnly" left
              >explicit
            </v-icon>
            10 Blogs
          </v-chip>
          <v-chip small :x-small="$vuetify.breakpoint.xsOnly" label>
            <v-icon small :x-small="$vuetify.breakpoint.xsOnly" left
              >explicit
            </v-icon>
            15 Images
          </v-chip>
        </v-chip-group>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>mdi-heart</v-icon>
        </v-btn>
        <v-btn icon v-on:click.stop="shareCopy">
          <v-icon>mdi-share-variant</v-icon>
        </v-btn>
        <v-menu bottom left v-if="isAdmin">
          <template v-slot:activator="{ on }">
            <v-btn icon v-on="on">
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(item, i) in items"
              :key="i"
              @click="onMenuClick(item)"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import config from "@/config.js";
import VClamp from "vue-clamp";
import ImageLoader from "@/components/base/ImageLoader";

export default {
  name: "CollectionCard",
  components: { ImageLoader, VClamp },
  data() {
    return {
      items: [
        { title: "Edit Details", CODE: "EDIT_DETAILS" },
        { title: "Edit Cover", CODE: "EDIT_COVER" },
        { title: "Delete", CODE: "DELETE" },
      ],
      descriptionLength: 100,
      collection: this.$store.getters[`collection/byId`](this.$route.params.id),
    };
  },
  methods: {
    async shareCopy() {
      try {
        await this.$copyText(
          `${config.uiUrl}/collections/${this.collection.id}`
        );
        await this.$store.dispatch("shared/setSuccessText", "Copied");
        await this.$store.dispatch("shared/setSuccessSnackbar", true);
      } catch (e) {
        await this.$store.dispatch(
          "shared/setErrorText",
          "Something went wrong"
        );
        await this.$store.dispatch("shared/setErrorSnackbar", true);
      }
    },
    onMenuClick(item) {
      this.$emit("on-menu-click", { item: item.CODE, data: this.collection });
    },
  },
  computed: {
    isAdmin() {
      return this.$store.getters["login/isUserAdmin"];
    },
    description() {
      if (this.collection.description.length > this.descriptionLength) {
        return this.collection.description
          .substring(0, this.descriptionLength - 1)
          .concat("...");
      }
    },
    /*expertise() {
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
      },*/
  },
};
</script>

<style scoped></style>
