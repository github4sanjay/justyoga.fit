<template>
  <v-container pa-0>
  <v-img
    :src="computedData.previewURL ? computedData.previewURL: initialImage ? initialImage : placeHolder"
    :aspect-ratio="ratio">
    <v-container fill-height  class="align-end justify-end pa-0">
      <v-row>
        <v-col cols="2" class="text-xs-center" v-if="computedData.previewURL">
          <v-btn
            fab
            x-small
            @click="computedData.dialog = !computedData.dialog">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
        </v-col>
        <v-col cols="2" class="text-xs-center">
          <clipper-upload v-model="computedData.imgURL">
            <v-btn
              @click="computedData.dialog = !computedData.dialog"
              fab
              x-small>
              <v-icon>add_a_photo</v-icon>
            </v-btn>
          </clipper-upload>
        </v-col>
      </v-row>
    </v-container>
    <v-dialog
      :persistent="false"
      v-model="computedData.dialog"
      width="1400">
      <v-card>
        <v-card-actions>
          <v-card-title>
            <div class="font-weight-bold title">
              Edit Image
            </div>
          </v-card-title>
          <v-spacer></v-spacer>
          <v-btn color="info" @click="getResult()">clip image</v-btn>
        </v-card-actions>
        <v-divider></v-divider>
        <v-row>
          <v-col xs="4">
            <v-container fluid>
              <div class="title">
                Ratio
              </div>
              <v-radio-group v-model="computedData.ratio">
                <v-radio
                  v-for="(ratio,index) in ratios"
                  :key="index"
                  :label="ratio.text"
                  :value="ratio.value"
                ></v-radio>
              </v-radio-group>
              <v-subheader class="pl-0">Image Scale</v-subheader>
              <v-slider
                v-model="computedData.scale"
                :step="0.1"
                :min="0"
                :max="10"
                :thumb-size="24"
                thumb-label="always"
              ></v-slider>
              <v-subheader class="pl-0">Image Rotate</v-subheader>
              <v-slider
                v-model="computedData.rotate"
                :max="360"
                :thumb-size="24"
                thumb-label="always"
              ></v-slider>
            </v-container>
          </v-col>
          <v-col xs="8" pa="2">
            <v-row class="justify-center text-xs-center"
                      style="max-height: 800px;overflow: auto;">
              <v-card>
                <v-img contain>
                  <clipper-basic
                    :scale="computedData.scale"
                    :rotate="computedData.rotate"
                    :ratio="computedData.ratio"
                    @load="imgLoad()"
                    :style="computedData.basicStyle"
                    :ref="computedData.clipper"
                    :src="computedData.imgURL"
                    :preview="computedData.preview">
                    <div class="placeholder" slot="placeholder">No image</div>
                  </clipper-basic>
                </v-img>
              </v-card>
            </v-row>
          </v-col>
        </v-row>
      </v-card>
    </v-dialog>
  </v-img>
    <div v-if="errors" class="v-messages theme--light error--text" role="alert">{{errors.join(",")}}</div>
  </v-container>
</template>

<script>
export default {
  name: "SingleImageUpload",
  components: {},
  props: {
    initialImage: {
      type: String,
      required: false,
      default: "/images/util/choose_image_placeholder.png"
    },
    ratio:{
      type: Number,
      required: true
    },
    errors: {
      type: Array,
      required: false
    },
    clear: {
      type: Boolean,
      required: false
    }
  },
  data() {
    return {
      placeHolder: "/images/util/choose_image_placeholder.png",
      maxWidth: 700,
      maxHeight: 650,
      ratios: [
        {
          text: "No Ratio",
          value: NaN
        },
        {
          text: "1",
          value: 1
        },
        {
          text: "4/3",
          value: 4 / 3
        },
        {
          text: "16/9",
          value: 16 / 9
        }
      ],
      imageData: {
        imgURL: "",
        resultURL: "",
        previewURL: "",
        dialog: false,
        preview: "my-preview",
        clipper: "clipper",
        valid: true,
        blob: null,
        basicStyle: {},
        based: 850,
        ratio: NaN,
        rotate: 0,
        scale: 1
      }
    };
  },
  computed: {
    computedData() {
      let data = this.imageData;
      if (!data.previewURL && data.imgURL) {
        data.dialog = true;
      }
      if (data.resultURL) {
        data.previewURL = data.resultURL;
      } else {
        data.previewURL = data.imgURL;
      }
      data.basicStyle = {
        maxWidth: data.based + "px"
      };
      return data;
    }
  },
  watch: {
    computedData(val) {
      this.$emit("image-data", val);
    },
    clear(val) {
      if (val){
        this.imageData = {
          imgURL: "",
          resultURL: "",
          previewURL: "",
          dialog: false,
          preview: "my-preview",
          clipper: "clipper",
          valid: true,
          blob: null,
          basicStyle: {},
          based: 850,
          ratio: NaN,
          rotate: 0,
          scale: 1
        }
      }
    },
  },
  methods: {
    getResult() {
      const canvas = this.$refs.clipper.clip(); //call component's clip method
      this.imageData.resultURL = canvas.toDataURL("image/jpg", 1); //canvas->image
      canvas.toBlob(blob => {
        this.imageData.blob = blob;
      });
      this.imageData.dialog = false;
    },
    imgLoad: function() {
      const imgRatio = this.$refs.clipper.imgRatio;
      if (imgRatio < 1) this.imageData.based = this.maxHeight * imgRatio;
      else this.imageData.based = this.maxWidth;
    },
  },
};
</script>

<style scoped>
</style>
