import Vue from "vue";
import Vuetify from "vuetify";
import colors from "vuetify/es5/util/colors";
import "vuetify/dist/vuetify.min.css";
import vueSmoothScroll from "vue-smoothscroll";
import Snotify, { SnotifyPosition } from "vue-snotify";
import Croppa from "vue-croppa";
import VuejsClipper from "vuejs-clipper";
import VueRx from "vue-rx";
import Viewer from "v-viewer";
import ErrorAlert from "../components/alerts/ErrorAlert";
import BaseBtn from "@/components/base/Btn";
import BaseCard from "@/components/base/Card";
import BaseSubheading from "@/components/base/Subheading";
import VueClipboard from "vue-clipboard2";
import * as VueGoogleMaps from "vue2-google-maps";
import VClamp from 'vue-clamp'

Vue.component(BaseBtn.name, BaseBtn);
Vue.component(BaseCard.name, BaseCard);
Vue.component(BaseSubheading.name, BaseSubheading);

Vue.use(VueRx);
Vue.use(VuejsClipper);
Vue.use(Viewer);

Vue.use(VuejsClipper, {
  components: {
    clipperBasic: true,
    clipperPreview: true,
  },
});

// import * as VueGoogleMaps from "vue2-google-maps";

/* Vue.use(Vuetify, {
  theme: {
    primary: '#121212', // a color that is not in the material colors palette
    accent: colors.grey.darken3,
    secondary: colors.amber.darken3,
    info: colors.teal.lighten1,
    warning: colors.amber.base,
    error: colors.deepOrange.accent4,
    success: colors.green.accent3
  }
}); */

Vue.use(Vuetify, {
  theme: {
    // primary: '#CBAA5C',
    // secondary: '#083759',
    primary: "#00695C",
    secondary: "#004D40",
    accent: "#1DE9B6",
    error: "#FF5252",
    info: "#2196F3",
    success: "#4CAF50",
    warning: "#FFC107",
    background: "#455A64",
    indicatorColor: colors.blueGrey.darken4,
    primaryLight: "#B2DFDB",
    colorPrimaryText: "#FFFFFF",
    colorSecondaryText: "#757575",
    blueBackground: "#6200ea",
  },
});

Vue.use(vueSmoothScroll);
Vue.component("app-alert-error", ErrorAlert);

const options = {
  toast: {
    position: SnotifyPosition.rightTop,
  },
};

Vue.use(Snotify, options);
Vue.use(Croppa);

Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyCg9KqjmKWZOFws3MUGPdx4uPJgjlndwds",
    libraries: "places", // This is required if you use the Autocomplete plugin
    // OR: libraries: 'places,drawing'
    // OR: libraries: 'places,drawing,visualization'
    // (as you require)

    //// If you want to set the version, you can do so:
    // v: '3.26',
  },
});

Vue.use(VueClipboard);
Vue.use(VClamp);
