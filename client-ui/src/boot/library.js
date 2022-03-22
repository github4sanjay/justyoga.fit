import Vue from "vue";
import VClamp from "vue-clamp";
import VueClipboard from "vue-clipboard2";
import Vuelidate from "vuelidate";
import VuejsClipper from "vuejs-clipper";

Vue.use(VClamp);

Vue.use(VueClipboard);

export default ({ Vue }) => {
  Vue.use(Vuelidate);
  Vue.use(VuejsClipper, {
    components: {
      clipperBasic: true,
      clipperPreview: true,
      clipperUpload: true
    }
  });
  // Vue.use(VueGoogleMaps, {
  //   load: {
  //     key: "AIzaSyCg9KqjmKWZOFws3MUGPdx4uPJgjlndwds",
  //     libraries: "places" // This is required if you use the Autocomplete plugin
  //     // OR: libraries: 'places,drawing'
  //     // OR: libraries: 'places,drawing,visualization'
  //     // (as you require)

  //     //// If you want to set the version, you can do so:
  //     // v: '3.26',
  //   }
  // });
};
