import colors from "vuetify/es5/util/colors";

export default {
  mode: "universal",
  /*
   ** Headers of the page
   */
  head: {
    title: "JustYoga - Yoga Networking",
    meta: [
      { charset: "utf-8" },
      { name: "viewport", content: "width=device-width, initial-scale=1" },
      {
        hid: "description",
        name: "description",
        content:
          "Search yoga trainers/instructor here, create your yoga trainer/instructor " +
          "profile here. Read reviews and blog of yoga classes and trainer/instructors.",
      },
      {
        hid: "keywords",
        name: "keywords",
        content:
          "yoga, yoga blog, yoga trainer, yoga sessions, yoga tutors, yoga class, yoga education, yoga trainer ratings," +
          "yoga trainer reviews",
      },
    ],
    link: [
      { rel: "icon", type: "image/x-icon", href: "/white_logo_only.png" },
      {
        rel: "stylesheet",
        href:
          "https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons",
      },
      {
        rel: "stylesheet",
        href: "https://use.fontawesome.com/releases/v5.0.13/css/all.css"
      },
    ],
  },
  /*
   ** Customize the progress-bar color
   */
  loading: { color: "#fff" },
  /*
   ** Global CSS
   */
  css: [],
  /*
   ** Plugins to load before mounting the App
   */
  plugins: [
    "@/plugins/vuetify",
    "@/plugins/fireauth",
    { src: `~plugins/vimeo-player`, ssr: false },
  ],
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: ["@nuxtjs/vuetify"],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc: https://axios.nuxtjs.org/usage
    "@nuxtjs/axios",
    "cookie-universal-nuxt",
  ],
  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {},
  /*
   ** vuetify module configuration
   ** https://github.com/nuxt-community/vuetify-module
   */
  vuetify: {
    customVariables: ["~/assets/variables.scss"],
    icons: {
      iconfont: 'fa',
    },
    theme: {
      dark: false,
      themes: {
        dark: {
          primary: colors.blue.darken2,
          accent: colors.grey.darken3,
          secondary: colors.amber.darken3,
          info: colors.teal.lighten1,
          warning: colors.amber.base,
          error: colors.red.accent4,
          success: colors.green.darken4,
          primaryLight: colors.grey.darken3,
        },
        light: {
          /*primary: '#00695C',
          secondary: '#004D40',
          accent: '#1DE9B6',
          error: '#FF5252',
          info: '#2196F3',
          success: '#4CAF50',
          warning: '#FFC107',
          background: '#455A64',
          indicatorColor: colors.blueGrey.darken4,*/
          primaryLight: colors.grey.lighten3,
          colorPrimaryText: "#FFFFFF",
          colorSecondaryText: "#757575",
          blueBackground: "#6200ea",
        },
      },
    },
  },
  /*
   ** Build configuration
   */
  build: {
    /*
     ** You can extend webpack config here
     */
    extend(config, ctx) {},
    transpile: [/^vue2-google-maps($|\/)/, 'vue-clamp', 'resize-detector']
  },
};
