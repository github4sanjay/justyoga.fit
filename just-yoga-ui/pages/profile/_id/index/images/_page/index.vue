<template>
  <v-card class="mt-3">
    <v-card-actions>
      <h4>Images ({{ images.totalElements }})</h4>
      <v-spacer></v-spacer>
      <v-btn
        router
        :to="`/profile/${userId}/images/${previousPage}`"
        icon
        :disabled="!images.hasPrevious"
      >
        <v-icon color="primary">fa fa-arrow-alt-circle-left</v-icon>
      </v-btn>
      <v-divider vertical />
      <v-btn
        router
        :to="`/profile/${userId}/images/${nextPage}`"
        icon
        :disabled="!images.hasNext"
      >
        <v-icon color="primary">fa fa-arrow-alt-circle-right</v-icon>
      </v-btn>
    </v-card-actions>
    <v-divider />
    <v-row class="pa-3">
      <v-col cols="12" sm="6" v-for="(image, i) in images.data" :key="i">
        <image-card
          :image="$store.getters['images/byId'](image)"
          :editable="editable"
        />
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import ReviewCard from "@/components/base/ReviewCard";
import { mapGetters } from "vuex";
import ImageCard from "@/components/base/ImageCard";
export default {
  name: "ImagePageIndex",
  middleware: "profile_image",
  components: { ImageCard, ReviewCard },
  data: function () {
    return {
      page: this.$route.params.page,
      images: this.$store.getters["images/byPageAndUserId"](
        this.$route.params.id,
        this.$route.params.page
      ),
      userId: this.$route.params.id,
      nextPage: +this.$route.params.page + 1,
      previousPage: +this.$route.params.page - 1,
    };
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user",
    }),
    user() {
      return this.$store.getters["users/user"](this.$route.params.id);
    },
    editable() {
      return (
        this.currentUser && this.user && this.currentUser.id === this.user.id
      );
    },
  },
};
</script>

<style scoped></style>
