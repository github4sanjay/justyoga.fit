<script>
import { LINE_PATH_CONFIG } from "../../constants/MapSettings";

export default {
  props: {
    google: {
      type: Object,
      required: true
    },
    map: {
      type: Object,
      required: true
    },
    path: {
      type: Array,
      required: true
    }
  },

  mounted() {
    new this.google.maps.Polyline({
      path: this.path,
      map: this.map,
      ...LINE_PATH_CONFIG
    });
  },

  // eslint-disable-next-line vue/require-render-return
  render() {}
};
</script>
