<template>
  <q-page>
    <div class="row" v-if="!showError">
      <div class="col-12">
        <q-card flat square bordered>
          <q-card-actions>
            <div
              :class="{
                'text-bold': true
              }"
            >
              Latest Shared Eperiences
            </div>
          </q-card-actions>
        </q-card>
      </div>
      <div class="col-12" v-if="latestData">
        <div class="row justify-center" v-if="loadingLatestData">
          <div class="q-pa-md flex flex-center">
            <q-circular-progress
              indeterminate
              size="50px"
              color="primary"
              class="q-ma-md"
            />
          </div>
        </div>
        <div class="row justify-center" v-else>
          <div
            :class="{
              'col-12': true,
              'col-sm-6': true,
              'q-pa-md': $q.screen.gt.xs,
              'q-py-sm': $q.screen.xs
            }"
            v-for="data in latestData.videos.content"
            :key="data.id"
          >
            <directory-video-card :video-info="data" />
          </div>
          <div
            :class="{
              'col-12': true,
              'col-sm-6': true,
              'q-pa-md': $q.screen.gt.xs,
              'q-py-sm': $q.screen.xs
            }"
            v-for="data in latestData.blogs.content"
            :key="data.id"
          >
            <directory-blog-card :blog-info="data" />
          </div>
          <div
            :class="{
              'col-12': true,
              'col-sm-6': true,
              'q-pa-md': $q.screen.gt.xs,
              'q-py-sm': $q.screen.xs
            }"
            v-for="data in latestData.images.content"
            :key="data.id"
          >
            <directory-image-card :image-info="data" />
          </div>
        </div>
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
import DirectoryVideoCard from "components/directory/DirectoryVideoCard";
import DirectoryBlogCard from "components/directory/DirectoryBlogCard";
import DirectoryImageCard from "components/directory/DirectoryImageCard";
import config from "app/config";
import Cms from "src/constants/Cms";
import MetaUtil from "src/utils/MetaUtil";
import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";

export default {
  name: "UserDetail",
  components: {
    DirectoryVideoCard,
    DirectoryBlogCard,
    DirectoryImageCard
  },
  props: {},
  data: function() {
    return {
      latestData: null,
      loadingLatestData: true
    };
  },
  methods: {
    getTitle() {
      let title = "Home | ";
      let model = this.user;
      if (model.name) {
        title = title + model.name;
      }
      if (model.interest) {
        let interest = model.interest;
        if (interest.blogger) title = title + " | " + "Yoga Blogger";
        if (interest.trainer) title = title + " | " + "Yoga Trainer";
        if (interest.ookingForTrainer)
          title = title + " | " + "Looking for Yoga Trainer";
      }
      if (model.basicInfo) {
        let basicInfo = model.basicInfo;
        title = title + " in " + basicInfo.formattedAddress;
      }
      return title;
    },
    getDesc() {
      let description = "";
      let model = this.user;
      if (model.name) {
        description = description + model.name;
      }
      if (model.description) {
        description = description + " - " + model.description;
      }
      return description;
    },
    getUrl() {
      return `${config.uiUrl}/users/${this.user.id}/detail`;
    },
    getImage() {
      if (this.user.profilePic) {
        return this.user.profilePic;
      } else {
        return Cms.DEFAULT_IMAGE_URL;
      }
    },
    getKeyWords() {
      return `details,rating,reviews,yoga,feeds,
            blog,contact,yoga classes`;
    }
  },
  async created() {
    this.loadingLatestData = true;
    const feedConfigData = {
      url: ApiEndpoints.SEARCH_FEED,
      method: "get",
      params: {
        userId: this.user.id
      },
      withCredentials: true
    };
    try {
      let feedResponse = await AxiosUtil.getAxiosRequest(feedConfigData, null);
      if (
        feedResponse &&
        feedResponse.status === 200 &&
        feedResponse.data.data
      ) {
        this.latestData = feedResponse.data.data;
      } else {
        NotifyUtil.showError("Something went wrong");
      }
    } catch (e) {
      NotifyUtil.showError("Something went wrong");
    } finally {
      this.loadingLatestData = false;
    }
  },
  computed: {
    user() {
      return this.$store.getters["users/user"](this.$route.params.id);
    },
    currentUser() {
      return this.$store.getters["login/user"];
    },
    editable() {
      return !!(this.currentUser && this.currentUser.id === this.user.id);
    },
    images() {
      let images = this.user.images;
      if (images.length <= 3) {
        return images;
      } else {
        return images.slice(0, 3);
      }
    },
    videos() {
      let videos = this.user.videos;
      if (videos.length <= 3) {
        return videos;
      } else {
        return videos.slice(0, 3);
      }
    },
    showError() {
      if (this.loadingLatestData) return false;
      if (!this.latestData) return true;
      if (
        this.latestData.images.content.length > 0 ||
        this.latestData.videos.content.length > 0 ||
        this.latestData.blogs.content.length > 0
      )
        return false;
      else return true;
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
      "website"
    );
  }
};
</script>

<style lang="scss" scoped>
.profile-map {
  height: 200px;
}
</style>
