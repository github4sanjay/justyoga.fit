<template>
    <v-card class="pa-0 mx-auto">
      <v-img
        :src="image.url"
        max-height="800"
        contain
        class="grey darken-4">
        <v-container fill-height class="align-center pa-0">
          <v-row align="center">
            <v-col cols="10" offset="1">
              <v-btn :disabled="!isLeft" icon dark @click="leftClick">
                <v-icon color="primary">fa fa-arrow-alt-circle-left</v-icon>
              </v-btn>
            </v-col>
            <v-col cols="1">
              <v-btn :disabled="!isRight" icon dark @click="rightClick">
                <v-icon color="primary">fa fa-arrow-alt-circle-right</v-icon>
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-img>
    </v-card>
</template>

<script>

export default {
  name: "ImageShowWithContent",
  components: {  },
  props: {
    image: {
      type: Object,
      required: true
    },
    loading: {
      type: Boolean,
      required: true
    },
    isRight: {
      type: Boolean,
      required: true
    },
    isLeft: {
      type: Boolean,
      required: true
    },
   /* place: {
      type: Object,
      required: true
    },*/
    options: {
      type: Array,
      required: true
    }
  },
  data() {
    return {};
  },
  methods: {
    rightClick() {
      this.$emit("right-click");
    },
    leftClick() {
      this.$emit("left-click");
    },
    onOptionClick(index) {
      this.$emit("on-option-click", index);
    }
  }
};
</script>
