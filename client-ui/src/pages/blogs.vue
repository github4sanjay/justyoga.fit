<template>
  <q-page padding>
    <div class="row justify-center" style="width:100%;">
      <div class="col-12 col-md-10 col-lg-8">
        <q-card flat square>
          <q-card-actions>
            <q-item class="q-pl-sm">
              <q-item-section avatar>
                <q-avatar color="primary" text-color="white">
                  <user-image :user="blogger" />
                </q-avatar>
              </q-item-section>

              <q-item-section>
                <q-item-label>{{ blogger.name }}</q-item-label>
                <q-item-label caption>
                  {{ time }},
                  {{ readTime() }}
                </q-item-label>
              </q-item-section>
            </q-item>

            <q-space />

            <share-network
              v-if="$q.screen.gt.xs"
              :class="{ 'q-mt-xs': $q.screen.xs }"
              :url="getUrl()"
              :title="blog.blogTitle"
              :description="getDesc()"
              :quote="blog.blogTitle"
            />
            <q-btn
              v-if="$q.screen.gt.xs"
              :class="{ 'q-mt-xs': $q.screen.xs }"
              padding="xs"
              color="primary"
              icon="share"
              @click="shareCopy"
            />

            <q-btn
              :class="{ 'q-mt-xs': $q.screen.xs, 'q-px-sm': true }"
              v-if="editable"
              label="Edit"
              padding="xs"
              color="primary"
            >
              <q-menu transition-show="jump-down" transition-hide="jump-up">
                <q-list style="min-width: 100px">
                  <q-item
                    clickable
                    v-for="(item, i) in items"
                    :key="i"
                    @click="onMenuClick(item)"
                  >
                    <q-item-section>{{ item.title }}</q-item-section>
                  </q-item>
                </q-list>
              </q-menu>
            </q-btn>
          </q-card-actions>

          <q-card-actions v-if="$q.screen.xs">
            <share-network
              :class="{ 'q-mt-xs': $q.screen.xs }"
              :url="getUrl()"
              :title="blog.blogTitle"
              :description="getDesc()"
              :quote="blog.blogTitle"
            />
            <q-btn
              :class="{ 'q-mt-xs': $q.screen.xs }"
              padding="xs"
              color="primary"
              icon="share"
              @click="shareCopy"
            />
          </q-card-actions>

          <q-card-section v-if="blog.coverUrl" class="text-center">
            <q-img
              spinner-color="primary"
              class="bg-dark"
              contain
              :src="blog.coverUrl"
              :style="`max-height: ${$q.screen.gt.sm ? '600px' : '300px'};`"
            />
          </q-card-section>

          <q-card-section
            :class="{
              'text-h3': $q.screen.gt.sm,
              'text-h4': $q.screen.lt.md,
              'text-bold': true
            }"
          >
            {{ blog.blogTitle }}
          </q-card-section>

          <q-card-section v-html="blog.blogContent" />

          <q-separator />

          <q-card-actions class="q-px-none">
            <q-card-section>
              <div>
                {{ blog.comments.length }} comments,
                {{ blog.likes.length }} likes
              </div>
            </q-card-section>
            <q-space />
            <q-btn
              size="sm"
              outline
              unelevated
              color="grey"
              label="Comment"
              v-if="!currentUser"
              @click="showLoginMessage"
            />
            <q-btn
              size="sm"
              outline
              unelevated
              label="Like"
              :color="isLiked ? `primary` : `grey`"
              :loading="savingLike"
              icon="favorite"
              @click="like"
            />
          </q-card-actions>
        </q-card>
        <q-card flat v-if="currentUser" class="q-mt-md">
          <comment-box
            v-if="comment"
            @submit="saveComment"
            :user="currentUser"
            :saving="savingComment"
            :clear="clearComment"
          />
        </q-card>
        <div class="row" v-if="blog.comments && blog.comments.length > 0">
          <div class="col-12 q-my-md">Comments</div>
          <div
            class="col-12 q-mt-sm"
            v-for="comment in blog.comments"
            :key="comment.id"
          >
            <comment :comment="comment" />
            <q-separator />
          </div>
        </div>
      </div>
    </div>
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
    <q-dialog v-model="deleteBlogDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '850px' : ''
        }"
      >
        <confirm
          :loading="deletingBlog"
          :model="blog"
          :text="deleteBlogText"
          @close="deleteBlogDialogue = !deleteBlogDialogue"
          @confirm="deleteBlog"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="blogDialogue" :maximized="$q.screen.xs">
      <q-card
        :style="{
          'min-width': $q.screen.gt.xs ? '850px' : ''
        }"
      >
        <!-- can use a different blog form for editing instead of reusing-->
        <blog-form
          :loading="savingBlog"
          :currentUser="currentUser"
          :user="blogger"
          :initial-blog="blog"
          @close="blogDialogue = false"
          :clear="clearBlogForm"
        />
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script>
import config from "../../config";
import { mapGetters } from "vuex";
import NotifyUtil from "src/utils/NotifyUtil";
import CommentBox from "components/base/CommentBox";
import Comment from "components/base/Comment";
import BlogForm from "components/user/BlogForm";
import Confirm from "components/base/Confirm";
import MediaCarousel from "components/base/MediaCarousel";
import DefaultVideoCard from "components/base/DefaultVideoCard";
import TimeUtil from "src/utils/TimeUtil";
import { Loading } from "quasar";
import CopyUtil from "src/utils/CopyUtil";
import UserImage from "components/base/UserImage";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";
import ReadingTime from "src/utils/ReadingTime";
import ShareNetwork from "components/base/ShareNetwork";

export default {
  name: "BlogHome",
  components: {
    ShareNetwork,
    MediaCarousel,
    Confirm,
    BlogForm,
    Comment,
    CommentBox,
    UserImage
  },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    try {
      let params = currentRoute.params;
      let blog = store.getters["blogs/findById"](params.id);
      if (blog === null || blog === undefined) {
        blog = await store.dispatch("blogs/get", { id: params.id });
        let promises = [];
        promises.push(store.dispatch("blogs/getImages", { blogId: blog.id }));
        promises.push(store.dispatch("blogs/getVideos", { blogId: blog.id }));
        promises.push(store.dispatch("users/storeUser", { id: blog.userId }));
        await Promise.all(promises);
      }

      let userIds = new Set(); // only unique users
      if (!blog.comments) {
        let comments = await store.dispatch("blogs/getComments", {
          blogId: blog.id
        });
        if (comments) {
          comments.forEach(comment => userIds.add(comment.userId));
        }
      }

      if (!blog.likes) {
        let likes = await store.dispatch("blogs/getLikes", {
          blogId: blog.id
        });
        if (likes) {
          likes.forEach(like => userIds.add(like.userId));
        }
      }
      let commentUserPromise = [];
      userIds.forEach(id =>
        commentUserPromise.push(store.dispatch("users/storeUser", { id: id }))
      );

      await Promise.all(commentUserPromise); // get all user from comment userId
    } catch (e) {
      redirect("/error404");
    }
    Loading.hide();
  },
  data: function() {
    return {
      // blog: this.$store.getters["blogs/findById"](this.$route.params.id),
      clearComment: false,
      savingComment: false,
      comment: true,
      savingLike: false,
      items: [{ title: "Edit" }, { title: "Delete" }],
      blogDialogue: false,
      savingBlog: false,
      clearBlogForm: false,
      deletingBlog: false,
      deleteBlogText: "Do you want to delete this blog ?",
      deleteBlogDialogue: false,
      mediaDialogue: false,
      mediaSelected: { type: "IMAGE", position: 0 },
      isLiked: null
    };
  },
  created() {
    if (this.blog) {
      this.isLiked = this.getIsLiked(this.blog);
    }
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user"
    }),
    blog() {
      return this.$store.getters["blogs/findById"](this.$route.params.id);
    },
    editable() {
      return this.currentUser && this.currentUser.id === this.blog.userId;
    },

    blogger() {
      return this.$store.getters["users/user"](this.blog.userId);
    },
    time() {
      return TimeUtil.timeDifference(this.blog.updatedAt);
    }
  },
  methods: {
    readTime() {
      let result = ReadingTime.readingTime(this.blog.blogText);
      if (result) return result.text;
      else return "Read Time Not Available";
    },
    getIsLiked(blog) {
      let likes = blog.likes;
      let isLiked = null;
      if (likes && this.currentUser) {
        for (let i = 0; i < likes.length; i++) {
          if (likes[i].userId === this.currentUser.id) {
            isLiked = likes[i];
            break;
          }
        }
      }
      return isLiked;
    },
    saveComment(val) {
      this.clearComment = false;
      this.savingComment = true;
      this.$store
        .dispatch("blogs/storeComments", {
          userId: this.currentUser.id,
          blogId: this.blog.id,
          text: val
        })
        .finally(() => {
          this.savingComment = false;
          this.clearComment = true;
        });
    },
    showLoginMessage() {
      NotifyUtil.showError(`Please login to like/comment`);
    },
    like() {
      if (!this.currentUser) {
        this.showLoginMessage();
        return;
      }
      if (this.isLiked) {
        this.savingLike = true;
        this.$store
          .dispatch("blogs/deleteLikes", {
            id: this.isLiked.id,
            blogId: this.blog.id
          })
          .then(() => (this.isLiked = null))
          .finally(() => {
            this.savingLike = false;
          });
      } else {
        this.savingLike = true;
        this.$store
          .dispatch("blogs/storeLikes", {
            userId: this.currentUser.id,
            blogId: this.blog.id
          })
          .then(data => (this.isLiked = data))
          .finally(() => {
            this.savingLike = false;
          });
      }
    },
    onMenuClick(val) {
      switch (val.title) {
        case "Edit":
          this.blogDialogue = true;
          break;
        case "Delete":
          this.deleteBlogDialogue = true;
          break;
      }
    },
    deleteBlog(val) {
      this.deletingBlog = true;
      this.$store
        .dispatch("blogs/remove", { id: val.id })
        .then(() => {
          this.deletingBlog = false;
          this.deleteBlogDialogue = false;
          this.$router.back(); // blog deleted go back
        })
        .catch(() => {
          this.deletingBlog = false;
        });
    },
    clickMedia(pos, type) {
      this.mediaDialogue = true;
      this.mediaSelected = { type: type, position: pos };
    },
    shareCopy() {
      CopyUtil.copyToClipboard(this.getUrl());
    },
    getTitle() {
      return (
        this.blog.blogTitle + (this.blogger ? " by " + this.blogger.name : "")
      );
    },
    getDesc() {
      return this.blog.blogText;
    },
    getUrl() {
      return `${config.uiUrl}/blogs/${this.blog.id}`;
    },
    getImage() {
      if (this.blog.coverUrl) {
        return this.blog.coverUrl;
      } else {
        return Cms.DEFAULT_IMAGE_URL;
      }
    },
    getKeyWords() {
      return `details,rating,reviews,yoga,feeds,
            blog,contact,yoga classes`;
    }
  },
  watch: {
    blog(val) {
      this.isLiked = this.getIsLiked(val);
    },
    currentUser(val) {
      this.isLiked = this.getIsLiked(this.blog);
    }
  },
  meta() {
    let title = this.getTitle();
    let description = this.getDesc();
    let url = this.getUrl();
    let image = this.getImage();
    let keywords = this.getKeyWords();
    return MetaUtil.getMeta(
      title,
      description,
      url,
      image,
      keywords,
      null,
      "article"
    );
  }
};
</script>
<style scoped></style>
