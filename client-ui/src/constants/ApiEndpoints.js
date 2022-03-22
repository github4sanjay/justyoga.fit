import config from "../../config.js";
import { Platform } from "quasar";

const API_V1 = "/api/v1";
const AUTH = "/auth";
const USER = "/user";
const LOCATIONS = "/location";
const PROFILE = "/profile";
const PLACE = "/place";
const COUNTRIES = "/countries";
const ADMINISTRATIVE_AREAS = "/administrative-areas";
const LOCALITIES = "/localities";
const SUB_LOCALITIES1 = "/sub-localities-1";
const SUB_LOCALITIES2 = "/sub-localities-2";
const REVIEWS = "/review";
const SEARCH = "/search";
const COLLECTION = "/collection";
const BLOGS = "/blog";
const MEDIA = "/library";

function getBaseUrl() {
  if (process.env.SERVER) {
    return config.dockerBaseUrl;
  } else if (process.env.CLIENT) {
    if (Platform.is.android) {
      return config.androidBaseUrl;
    } else {
      return config.baseUrl;
    }
  }
}

export default {
  PLACES: getBaseUrl() + PLACE + API_V1 + "/places",

  COUNTRIES: getBaseUrl() + PLACE + API_V1 + COUNTRIES,
  ADMINISTRATIVE_AREAS: getBaseUrl() + PLACE + API_V1 + ADMINISTRATIVE_AREAS,
  LOCALITIES: getBaseUrl() + PLACE + API_V1 + LOCALITIES,
  SUB_LOCALITIES1: getBaseUrl() + PLACE + API_V1 + SUB_LOCALITIES1,
  SUB_LOCALITIES2: getBaseUrl() + PLACE + API_V1 + SUB_LOCALITIES2,
  PLACE_IDS: getBaseUrl() + PLACE + API_V1 + "/ip",

  BLOGS: getBaseUrl() + BLOGS + API_V1 + "/blogs",
  BLOG_COMMENTS: getBaseUrl() + BLOGS + API_V1 + "/comments",
  BLOG_LIKES: getBaseUrl() + BLOGS + API_V1 + "/likes",
  BLOG_IMAGES: getBaseUrl() + BLOGS + API_V1 + "/images",
  BLOG_VIDEOS: getBaseUrl() + BLOGS + API_V1 + "/videos",

  LOCATIONS: API_V1 + "/locations",
  FORM_DATA: API_V1 + "/form-data",
  CATEGORIES: API_V1 + "/categories",

  USERS: getBaseUrl() + USER + API_V1 + "/users",
  USERS_COLLECTION: getBaseUrl() + USER + API_V1 + "/user-collection",

  LIKES: "/likes",
  COMMENTS: "/comments",
  EP_IMAGES: "/ep-images",

  REVIEWS: getBaseUrl() + REVIEWS + API_V1 + "/reviews",
  REVIEW_COMMENTS: getBaseUrl() + REVIEWS + API_V1 + "/comments",
  REVIEW_LIKES: getBaseUrl() + REVIEWS + API_V1 + "/likes",
  REVIEW_IMAGES: getBaseUrl() + REVIEWS + API_V1 + "/images",
  REVIEW_VIDEOS: getBaseUrl() + REVIEWS + API_V1 + "/videos",

  BLOGS: getBaseUrl() + BLOGS + API_V1 + "/blogs",
  BLOG_COMMENTS: getBaseUrl() + BLOGS + API_V1 + "/comments",
  BLOG_LIKES: getBaseUrl() + BLOGS + API_V1 + "/likes",
  BLOG_IMAGES: getBaseUrl() + BLOGS + API_V1 + "/images",
  BLOG_VIDEOS: getBaseUrl() + BLOGS + API_V1 + "/videos",

  REVERSE_GEOCODE: getBaseUrl() + LOCATIONS + API_V1 + "/reverse-geocode",
  AUTOCOMPLETE_RESULTS:
    getBaseUrl() + LOCATIONS + API_V1 + "/autocomplete-results",
  PLACE_DETAILS: getBaseUrl() + LOCATIONS + API_V1 + "/place-details",

  SIGN_IN: getBaseUrl() + AUTH + API_V1 + "/sign-in",
  LOGOUT: getBaseUrl() + AUTH + API_V1 + "/logout",

  MEDICAL_EXPERTISE: getBaseUrl() + PROFILE + API_V1 + "/medical-expertise",
  YOGA_EXPERTISE: getBaseUrl() + PROFILE + API_V1 + "/yoga-expertise",
  YOGA_CERTIFICATE: getBaseUrl() + PROFILE + API_V1 + "/yoga-certificate",

  BASIC_INFO: getBaseUrl() + PROFILE + API_V1 + "/basic-info",
  BASIC_INFO_COLLECTION:
    getBaseUrl() + PROFILE + API_V1 + "/basic-info-collection",

  INTERESTS: getBaseUrl() + PROFILE + API_V1 + "/interests",

  VIDEOS: getBaseUrl() + PROFILE + API_V1 + "/videos",
  IMAGES: getBaseUrl() + PROFILE + API_V1 + "/images",
  USER_MEDICAL_EXPERTISE:
    getBaseUrl() + PROFILE + API_V1 + "/user-medical-expertise",
  USER_YOGA_EXPERTISE: getBaseUrl() + PROFILE + API_V1 + "/user-yoga-expertise",
  USER_YOGA_CERTIFICATE:
    getBaseUrl() + PROFILE + API_V1 + "/user-yoga-certificate",

  SEARCH_USER_INFO: getBaseUrl() + SEARCH + API_V1 + "/user-info",
  SEARCH_IMAGE_INFO: getBaseUrl() + SEARCH + API_V1 + "/image-info",
  SEARCH_VIDEO_INFO: getBaseUrl() + SEARCH + API_V1 + "/video-info",
  SEARCH_BLOG_INFO: getBaseUrl() + SEARCH + API_V1 + "/blog-info",
  SEARCH_COLLECTION_INFO: getBaseUrl() + SEARCH + API_V1 + "/collection-info",
  SEARCH_FEED: getBaseUrl() + SEARCH + API_V1 + "/feed",

  SEARCH_COLLECTION_IMAGE_INFO:
    getBaseUrl() + SEARCH + API_V1 + "/collection/image-info",
  SEARCH_COLLECTION_VIDEO_INFO:
    getBaseUrl() + SEARCH + API_V1 + "/collection/video-info",
  SEARCH_COLLECTION_BLOG_INFO:
    getBaseUrl() + SEARCH + API_V1 + "/collection/blog-info",

  PLACES_MANAGED: "/places-managed",
  USER_COVER: "/cover-pic",
  USER_PROFILE: "/profile-pic",
  FEED_LOCATION_WISE: "/api/open/feeds",

  COLLECTIONS: getBaseUrl() + COLLECTION + API_V1 + "/collections",
  COLLECTION_IMAGES: getBaseUrl() + COLLECTION + API_V1 + "/collection-images",
  COLLECTION_BLOGS: getBaseUrl() + COLLECTION + API_V1 + "/collection-blogs",
  COLLECTION_VIDEOS: getBaseUrl() + COLLECTION + API_V1 + "/collection-videos",

  MEDIA_UPLOAD: getBaseUrl() + MEDIA + API_V1 + "/media-upload",
  MEDIA_UPLOAD_URL_PUBLIC:
    getBaseUrl() + MEDIA + API_V1 + "/media-upload/url/public",
  MEDIA_UPLOAD_URL: getBaseUrl() + MEDIA + API_V1 + "/media-upload/url"
};
