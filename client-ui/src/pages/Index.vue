<template>
  <q-page>
    <div class="row bg-white">
      <div class="col-xs-5">
        <q-img
          src="~assets/2827464-eps.svg"
          spinner-color="white"
          :ratio="16 / 9"
        />
      </div>
      <div class="col-xs-7">
        <div
          :class="{
            column: true,
            'text-center': true,
            'full-height': true,
            'justify-center': true,
          }"
        >
          <div
            style="height: auto"
            :class="{
              'text-secondary': true,
              'col-2': true,
              'col-3': $q.screen.xs,
              'text-bold': true,
              'text-h3': $q.screen.lg,
              'text-h4': $q.screen.md,
              'text-h5': $q.screen.sm,
            }"
          >
            Find the right yoga class that fit your needs!
          </div>
          <div :class="{ 'col-2': true, 'col-3': $q.screen.xs }">
            <q-btn
              color="secondary"
              dark
              label="Register for free"
              :size="$q.screen.lt.md ? 'sm' : 'md'"
              to="/directory/videos/0"
            />
          </div>
        </div>
      </div>
    </div>
    <q-banner
      dense
      :class="{
        'bg-secondary': true,
        'text-white': true,
        'text-center': true,
        'q-mt-sm': $q.screen.xs,
      }"
    >
      <div
        :class="{
          'text-bold': $q.screen.gt.sm,
          'text-h6': $q.screen.gt.sm,
          'q-mt-sm': $q.screen.xs,
        }"
      >
        It's time to join justyoga.fit and become your best self. Join the
        community and unlock your full potential.
      </div>
    </q-banner>
    <q-banner dense class="text-center q-mt-md">
      <div class="text-h5 text-weight-light">Join Our Community</div>
    </q-banner>

    <div class="row justify-center q-pa-sm">
      <div
        :class="{
          'col-12': true,
          'col-sm-6': true,
          'col-md-4': true,
          'col-lg-3': true,
          'q-pa-md': $q.screen.gt.xs,
          'q-py-sm': $q.screen.xs,
        }"
        v-for="(item, i) in cta"
        :key="i"
      >
        <q-card square @click="onCTAClickCard(item.to)" flat bordered>
          <div class="row q-pa-sm">
            <div class="col-4 q-pa-sm">
              <q-img :src="require(`src/assets/${item.img}`)" />
            </div>
            <div class="col-8">
              <div>
                <div class="col text-h6 ellipsis">
                  {{ item.title }}
                  <q-btn
                    class="absolute all-pointer-events q-mx-xs"
                    size="sm"
                    name="info"
                    color="secondary"
                    dense
                    flat
                    :label="item.actionLabel"
                    :to="item.to"
                  >
                  </q-btn>
                </div>
              </div>
              <div>
                <div class="text-caption text-grey">
                  {{ item.desc }}
                </div>
              </div>
            </div>
          </div>
        </q-card>
      </div>
    </div>

    <!-- <q-banner dense class="text-center q-mt-md">
      <div class="text-h5 text-weight-light">
        Find Your Yoga Class
      </div>
    </q-banner>

    <cta-section
      :look="{
        'col-12': true,
        'col-sm-6': true,
        'col-md-4': true,
        'col-lg-3': true,
        'q-pa-md': $q.screen.gt.xs,
        'q-py-sm': $q.screen.xs
      }"
    /> -->

    <!-- <q-banner dense class="text-center q-mt-md">
      <div class="text-h5 text-weight-light">
        Our Popular Yoga Collections
      </div>
    </q-banner>
    <div class="row justify-center q-pa-sm">
      <collection-section :collections="items.data" />
    </div> -->

    <q-banner dense class="text-center q-mt-md">
      <div class="text-h5 text-weight-light">
        Latest Yoga Trainers Joined Our Community
        <q-btn v-if="videos.hasNext" flat label="Browse More" color="primary" />
      </div>
    </q-banner>

    <div class="row justify-center q-pa-sm">
      <div
        :class="{
          'col-12': true,
          'col-sm-6': true,
          'col-md-4': true,
          'col-lg-3': true,
          'q-pa-md': $q.screen.gt.xs,
          'q-py-sm': $q.screen.xs,
        }"
        v-for="(data, i) in trainers.data.content"
        :key="i"
      >
        <trainer-card :user-info="data" />
      </div>
    </div>

    <q-banner dense class="text-center q-mt-md">
      <div class="text-h5 text-weight-light">Latest Yoga Feeds</div>
    </q-banner>

    <div class="row justify-center q-pa-sm">
      <div
        :class="{
          'col-12': true,
          'col-sm-6': true,
          'col-md-4': true,
          'col-lg-3': true,
          'q-pa-md': $q.screen.gt.xs,
          'q-py-sm': $q.screen.xs,
        }"
        v-for="data in videos.data.content"
        :key="data.id"
      >
        <directory-video-card :video-info="data" />
      </div>
      <div
        :class="{
          'col-12': true,
          'col-sm-6': true,
          'col-md-4': true,
          'col-lg-3': true,
          'q-pa-md': $q.screen.gt.xs,
          'q-py-sm': $q.screen.xs,
        }"
        v-for="data in blogs.data.content"
        :key="data.id"
      >
        <directory-blog-card :blog-info="data" />
      </div>
      <div
        :class="{
          'col-12': true,
          'col-sm-6': true,
          'col-md-4': true,
          'col-lg-3': true,
          'q-pa-md': $q.screen.gt.xs,
          'q-py-sm': $q.screen.xs,
        }"
        v-for="data in images.data.content"
        :key="data.id"
      >
        <directory-image-card :image-info="data" />
      </div>
    </div>
  </q-page>
</template>

<script>
import { mapGetters } from "vuex";
//import CollectionSection from "components/collection/CollectionSection";
import DirectoryVideoCard from "components/directory/DirectoryVideoCard";
import DirectoryBlogCard from "components/directory/DirectoryBlogCard";
import DirectoryImageCard from "components/directory/DirectoryImageCard";
import TrainerCard from "components/directory/TrainerCard";
//import CtaSection from "components/base/CtaSection";
import { Loading } from "quasar";
import Cms from "src/constants/Cms";

export default {
  name: "PageIndex",
  components: {
    //CollectionSection,
    DirectoryVideoCard,
    DirectoryBlogCard,
    DirectoryImageCard,
    TrainerCard,
  },
  async preFetch({ store, currentRoute, previousRoute, redirect, ssrContext }) {
    Loading.show();
    let requests = [];
    let page = currentRoute.params.page ? currentRoute.params.page : 0;
    let collectionPage = store.getters["collection/byPage"](page);
    if (collectionPage === null || collectionPage === undefined) {
      requests.push(
        store.dispatch("collection/getCollectionsPage", {
          page: page,
          count: 10,
          sort: "updatedAt",
          order: "desc",
        })
      );
    }
    let blogs = store.getters["directory_blogs/byPage"](page);
    if (!blogs) {
      requests.push(
        store.dispatch("directory_blogs/get", {
          page: page,
        })
      );
    }
    let images = store.getters["directory_images/byPage"](page);
    if (!images) {
      requests.push(
        store.dispatch("directory_images/get", {
          page: page,
        })
      );
    }
    let videos = store.getters["directory_videos/byPage"](page);
    if (!videos) {
      requests.push(
        store.dispatch("directory_videos/get", {
          page: page,
        })
      );
    }
    let trainers = store.getters["directory/byPage"](page);
    if (!trainers) {
      requests.push(
        store.dispatch("directory/get", {
          page: page,
        })
      );
    }
    try {
      await Promise.all(requests);
    } catch (error) {
      redirect("/error500");
    } finally {
      Loading.hide();
    }
    Loading.hide();
  },
  data() {
    return {};
  },
  methods: {
    getCollectionById(id) {
      return this.$store.getters[`collection/byId`](id);
    },
    onCTAClickCard(to) {
      this.$router.push(to);
    },
  },
  computed: {
    // display the item from store state.
    items() {
      return this.$store.getters["collection/byPage"](0);
    },
    blogs() {
      return this.$store.getters["directory_blogs/byPage"](0);
    },
    images() {
      return this.$store.getters["directory_images/byPage"](0);
    },
    videos() {
      return this.$store.getters["directory_videos/byPage"](0);
    },
    trainers() {
      return this.$store.getters["directory/byPage"](0);
    },
    ...mapGetters({
      currentUser: "login/user",
    }),
    ctaLoginOrProfile() {
      return this.currentUser
        ? `/users/${this.currentUser.id}/detail`
        : `/login`;
    },
    cta() {
      return [
        {
          title: "Yoga Trainers",
          desc: "Yoga trainers can join our community by registering and in profile section choosing interest as trainer.",
          img: "guru.svg",
          actionLabel: "Register",
          to: this.ctaLoginOrProfile,
        },
        {
          title: "Yoga Learners",
          desc: "Yoga enthuisiasts and beginners can find trusted trainer near them. You can compare ratings and other's review.",
          img: "computer.svg",
          actionLabel: "Find trainer",
          to: "/directory/trainers/0",
        },
        {
          title: "Yoga Bloggers",
          desc: "Do you love writting and sharing right knowledge with others? It's just a button away, click on ADD BLOG on you profile section",
          img: "chakra.svg",
          actionLabel: "Start",
          to: this.ctaLoginOrProfile,
        },

        // {
        //   title: "Yoga Classes",
        //   desc:
        //     "Kept in sent gave feel will oh it we. Has pleasure procured me laughing shutters nay. Old insipidity motionless continuing law shy partiality.",
        //   img: "wellness.svg"
        // }
      ];
    },
  },
  meta() {
    return {
      title: "JustYoga - Yoga Networking",
      meta: {
        description: {
          name: "description",
          content: `Search yoga trainers here, create yoga trainer profile here. Read reviews and blog of yoga classes and trainer.`,
        },
        keywords: {
          name: "keywords",
          content: `yoga, yoga blog, yoga trainer, yoga sessions, yoga tutors, yoga class, yoga education, yoga trainer ratings, yoga trainer reviews`,
        },
        equiv: {
          "http-equiv": "Content-Type",
          content: "text/html; charset=UTF-8",
        },
        ogType: {
          property: "og:type",
          content: "website",
        },
        ogUrl: {
          property: "og:url",
          content: "https://justyoga.fit/",
        },
        ogTitle: {
          property: "og:title",
          content: "JustYoga - Yoga Networking",
        },
        ogDesc: {
          property: "og:description",
          content:
            "Search yoga trainers/instructor here, create your yoga trainer/instructor profile here. Read reviews and blog of yoga classes and trainer/instructors.",
        },
        ogImage: {
          property: "og:image",
          content: Cms.DEFAULT_IMAGE_URL,
        },
        twitterUrl: {
          property: "twitter:url",
          content: "https://justyoga.fit/",
        },
        twitterTitle: {
          property: "twitter:title",
          content: "JustYoga - Yoga Networking",
        },
        twitterDesc: {
          property: "twitter:description",
          content:
            "Search yoga trainers/instructor here, create your yoga trainer/instructor profile here. Read reviews and blog of yoga classes and trainer/instructors.",
        },
        twitterImage: {
          property: "twitter:image",
          content: Cms.DEFAULT_IMAGE_URL,
        },
      },
    };
  },
};
</script>
