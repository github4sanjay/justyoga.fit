import config from '@/config.js';

const API_V1 = '/api/v1';
const AUTH = '/auth';
const USER = '/user';
const LOCATIONS = '/location';
const PROFILE = '/profile';
const PLACE = '/place';
const COUNTRIES = '/countries';
const ADMINISTRATIVE_AREAS = '/administrative-areas';
const LOCALITIES = '/localities';
const SUB_LOCALITIES1 = '/sub-localities-1';
const SUB_LOCALITIES2 = '/sub-localities-2';
const REVIEWS = '/review';
const SEARCH = '/search';
const COLLECTION = '/collection';

function getBaseUrl() {
  if (process.server) {
    return config.dockerBaseUrl;
  }
  // Client-side
  if (process.client) {
    return config.baseUrl;
  }
}


export default {
  PLACES: getBaseUrl() + PLACE + API_V1 +  "/places",

  COUNTRIES: getBaseUrl() + PLACE + API_V1 + COUNTRIES,
  ADMINISTRATIVE_AREAS: getBaseUrl() + PLACE + API_V1 +  ADMINISTRATIVE_AREAS,
  LOCALITIES: getBaseUrl() + PLACE + API_V1 +  LOCALITIES,
  SUB_LOCALITIES1: getBaseUrl() + PLACE + API_V1 +  SUB_LOCALITIES1,
  SUB_LOCALITIES2: getBaseUrl() + PLACE + API_V1 +  SUB_LOCALITIES2,
  PLACE_IDS: getBaseUrl() + PLACE + API_V1 + "/ip",

  BLOGS: API_V1 + '/blogs',
  LOCATIONS: API_V1 + '/locations',
  FORM_DATA: API_V1 + '/form-data',
  CATEGORIES: API_V1 + '/categories',


  USERS: getBaseUrl() + USER + API_V1 + '/users',
  USERS_COLLECTION: getBaseUrl() + USER + API_V1 + '/user-collection',

  LIKES: '/likes',
  COMMENTS: '/comments',
  EP_IMAGES: '/ep-images',


  REVIEWS: getBaseUrl() + REVIEWS + API_V1 + '/reviews',
  REVIEW_COMMENTS: getBaseUrl() + REVIEWS + API_V1 + '/comments',
  REVIEW_LIKES: getBaseUrl() + REVIEWS + API_V1 + '/likes',
  REVIEW_IMAGES: getBaseUrl() + REVIEWS + API_V1 + '/images',
  REVIEW_VIDEOS: getBaseUrl() + REVIEWS + API_V1 + '/videos',

  REVERSE_GEOCODE: getBaseUrl() + LOCATIONS + API_V1 + '/reverse-geocode',
  AUTOCOMPLETE_RESULTS: getBaseUrl() + LOCATIONS + API_V1 + '/autocomplete-results',
  PLACE_DETAILS: getBaseUrl() + LOCATIONS + API_V1 + '/place-details',

  SIGN_IN: getBaseUrl() + AUTH + API_V1 + '/sign-in',
  LOGOUT: getBaseUrl() + AUTH + API_V1 + '/logout',

  MEDICAL_EXPERTISE:  getBaseUrl() + PROFILE + API_V1 +  "/medical-expertise",
  YOGA_EXPERTISE:  getBaseUrl() + PROFILE + API_V1 +  "/yoga-expertise",
  YOGA_CERTIFICATE:  getBaseUrl() + PROFILE + API_V1 +  "/yoga-certificate",

  BASIC_INFO: getBaseUrl() + PROFILE + API_V1 +  "/basic-info",
  BASIC_INFO_COLLECTION: getBaseUrl() + PROFILE + API_V1 +  "/basic-info-collection",

  INTERESTS: getBaseUrl() + PROFILE + API_V1 +  "/interests",

  VIDEOS: getBaseUrl() + PROFILE + API_V1 + "/videos",
  IMAGES: getBaseUrl() + PROFILE + API_V1 + "/images",
  USER_MEDICAL_EXPERTISE: getBaseUrl() + PROFILE + API_V1 + "/user-medical-expertise",
  USER_YOGA_EXPERTISE: getBaseUrl() + PROFILE + API_V1 + "/user-yoga-expertise",
  USER_YOGA_CERTIFICATE: getBaseUrl() + PROFILE + API_V1 + "/user-yoga-certificate",

  SEARCH_USER_INFO: getBaseUrl() + SEARCH + API_V1 + "/user-info",
  SEARCH_IMAGE_INFO: getBaseUrl() + SEARCH + API_V1 + "/image-info",
  SEARCH_VIDEO_INFO: getBaseUrl() + SEARCH + API_V1 + "/video-info",

  PLACES_MANAGED: '/places-managed',
  USER_COVER: '/cover-pic',
  USER_PROFILE: '/profile-pic',
  FEED_LOCATION_WISE: '/api/open/feeds',

  COLLECTIONS: getBaseUrl() + COLLECTION + API_V1 + "/collections"
}
