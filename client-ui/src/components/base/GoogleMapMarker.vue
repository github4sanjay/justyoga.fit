<script>
import { POINT_MARKER_ICON_CONFIG } from "../../constants/MapSettings";

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
    marker: {
      type: Object,
      required: true
    }
  },

  mounted() {
    new this.google.maps.Marker({
      position: this.marker.position,
      marker: this.marker,
      map: this.map,
      icon: POINT_MARKER_ICON_CONFIG
    });
  },

  // eslint-disable-next-line vue/require-render-return
  render() {}
};
</script>
