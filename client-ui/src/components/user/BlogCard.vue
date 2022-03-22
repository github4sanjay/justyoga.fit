<template>
  <div class="row items-start" clickable>
    <q-card style="width: 100%" flat bordered square>
      <q-item>
        <q-item-section>
          <q-item-label>
            <v-clamp autoresize hyphens :max-lines="1">
              {{ blog.blogTitle }}
            </v-clamp>
          </q-item-label>
          <q-item-label caption>
            {{ time }}
          </q-item-label>
        </q-item-section>
      </q-item>

      <q-img :src="blog.coverUrl" :ratio="16 / 9" style="width: 100%">
        <div class="absolute-bottom text-subtitle1">
          <v-clamp autoresize hyphens :max-lines="3">
            {{ blog.blogText }}
          </v-clamp>
        </div>
      </q-img>

      <q-card-actions>
        <q-btn flat color="primary" label="Details" :to="`/blogs/${blog.id}`" />
        <q-btn
          v-if="editable"
          flat
          color="primary"
          label="Update Cover"
          @click="updateBlogCover(blog)"
        />
      </q-card-actions>
      <q-dialog v-model="BlogCoverDialogue" :maximized="$q.screen.xs">
        <q-card
          flat
          :style="{
            'min-width': $q.screen.gt.sm ? '850px' : ''
          }"
        >
          <blog-cover-form
            @close="BlogCoverDialogue = false"
            :blog-data="blog"
          />
        </q-card>
      </q-dialog>
    </q-card>
    <q-dialog v-model="mediaDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '850px' : ''
        }"
      >
        <media-carousel
          :images="blog.images"
          :videos="blog.videos"
          :select="mediaSelected"
          @close="mediaDialogue = false"
        />
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import TimeUtil from "../../utils/TimeUtil";
import UserImage from "components/base/UserImage";
import VClamp from "vue-clamp";
import BlogCoverForm from "components/user/BlogCoverForm.vue";
import { mapGetters } from "vuex";

export default {
  name: "BlogCard",
  components: {
    VClamp,
    BlogCoverForm
  },
  props: {
    blogId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      imageDialog: false,
      index: 0,
      options: [{ text: "Delete" }],
      mediaSelected: { type: "IMAGE", position: 0 },
      mediaDialogue: false,
      BlogCoverDialogue: false
    };
  },
  created() {},
  methods: {
    clickMedia(pos, type) {
      this.mediaDialogue = true;
      this.mediaSelected = { type: type, position: pos };
    },
    rightClick() {
      this.index++;
    },
    leftClick() {
      this.index--;
    },
    openCarousel(index) {
      this.index = index;
      this.imageDialog = true;
    },
    onOptionClick(val) {
      this.$store.dispatch("shared/setSuccessText", val);
      this.$store.dispatch("shared/setSuccessSnackbar", true);
    },
    updateBlogCover(blog) {
      this.BlogCoverDialogue = true;
    }
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    time() {
      if (this.blog) {
        return TimeUtil.timeDifference(this.blog.updatedAt);
      } else {
        return "";
      }
    },
    blog() {
      return this.$store.getters["blogs/findById"](this.blogId);
    },
    editable() {
      return this.currentUser && this.currentUser.id === this.blog.userId;
    }
  }
};
</script>

<style scoped></style>
