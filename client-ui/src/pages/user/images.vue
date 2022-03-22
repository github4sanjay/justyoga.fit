<template>
  <q-page>
    <div class="row" v-if="images.totalElements > 0">
      <div class="col-12">
        <q-card flat square bordered>
          <q-card-actions>
            <div
              :class="{
                'text-bold': true
              }"
            >
              Images
              <q-badge
                color="accent"
                text-color="white"
                :label="images.totalElements"
              />
            </div>
            <q-space />
            <q-btn
              :disable="!images.hasPrevious"
              flat
              round
              color="primary"
              icon="fas fa-arrow-circle-left"
              :to="`${+images.page - 1}`"
            />
            <q-separator vertical />
            <q-btn
              :disable="!images.hasNext"
              flat
              round
              color="primary"
              icon="fas fa-arrow-circle-right"
              :to="`${+images.page + 1}`"
            />
          </q-card-actions>
        </q-card>
        <image-section
          :user="user"
          :editable="editable"
          :images="images.data"
        />
      </div>
    </div>
    <div v-else>
      <div class="text-center q-mt-lg">
        <p>
          <img
            src="~assets/found-icon-20.jpg"
            style="width:30vw;max-width:150px;"
          />
        </p>
        <p class="text-weight-light">
          Have not shared anything yet <br />
          Share your yoga experience by writing blogs, uploading images or
          videos.
        </p>
      </div>
    </div>
  </q-page>
</template>

<script>
import { mapGetters } from "vuex";
import { Loading } from "quasar";
import ImageSection from "components/user/ImageSection";
export default {
  name: "ImagePageIndex",
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let page = currentRoute.params.page ? currentRoute.params.page : 0;
    let data = store.getters["images/byPageAndUserId"](
      currentRoute.params.id,
      page
    );
    if (!data) {
      try {
        await store.dispatch("images/getProfileImages", {
          page: page,
          userId: currentRoute.params.id,
          count: 4
        });
      } catch (e) {
        redirect("/error404");
      }
    }
    Loading.hide();
  },
  components: { ImageSection },
  data: function() {
    return {};
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    user() {
      return this.$store.getters["users/user"](this.$route.params.id);
    },
    editable() {
      return (
        this.currentUser && this.user && this.currentUser.id === this.user.id
      );
    },
    images() {
      return this.$store.getters["images/byPageAndUserId"](
        this.$route.params.id,
        this.$route.params.page
      );
    }
  }
};
</script>

<style scoped></style>
