<template>
  <v-row class="ma-2">
    <v-col cols="12">
      <v-card flat>
        <v-card-text
          :class="{
            'pl-0': true,
            'justify-center': $vuetify.breakpoint.smAndDown,
            headline: $vuetify.breakpoint.mdAndUp,
            title: $vuetify.breakpoint.sm,
            'subtitle-2': $vuetify.breakpoint.xsOnly,
          }"
        >
          {{ headline }}
          <base-btn
            color="primary"
            class="ml-2"
            :text="buttonText"
            @click="fileChoose"
          />
        </v-card-text>
      </v-card>
    </v-col>
    <v-col
      cols="6"
      sm="4"
      md="3"
      v-for="(computedData, index) in computedDataList"
      :key="index"
    >
      <v-card width="200px" elevation="1">
        <v-row justify="center" class="text-center pb-2">
          <v-col cols="12">
            <v-img
              :src="
                computedData.previewURL ? computedData.previewURL : placeHolder
              "
              :aspect-ratio="4 / 3"
            >
            </v-img>
          </v-col>
          <v-col cols="4" class="pa-0">
            <v-tooltip bottom v-if="computedData.previewURL">
              <template v-slot:activator="{ on }">
                <v-btn
                  dark
                  v-on="on"
                  x-small
                  fab
                  color="indigo"
                  @click="computedData.dialog = !computedData.dialog"
                >
                  <v-icon small>edit</v-icon>
                </v-btn>
              </template>
              <span>Edit</span>
            </v-tooltip>
          </v-col>
          <v-col cols="4" class="pa-0">
            <clipper-upload v-model="computedData.imgURL">
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-btn v-on="on" x-small fab color="primary">
                    <v-icon small>add_a_photo</v-icon>
                  </v-btn>
                </template>
                <span>Choose Photo</span>
              </v-tooltip>
            </clipper-upload>
          </v-col>
          <v-col cols="4" class="pa-0">
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-btn
                  v-on="on"
                  x-small
                  fab
                  @click="remove(index)"
                  color="error"
                >
                  <v-icon small>fa fa-trash-alt</v-icon>
                </v-btn>
              </template>
              <span>Remove</span>
            </v-tooltip>
          </v-col>
        </v-row>
      </v-card>
      <v-dialog persistent v-model="computedData.dialog" width="1400">
        <v-card>
          <v-card-actions>
            <v-card-title>
              <div class="font-weight-bold title">
                Edit Image
              </div>
            </v-card-title>
            <v-spacer></v-spacer>
            <v-btn color="info" @click="getResult(index)">
              clip image
            </v-btn>
          </v-card-actions>
          <v-divider></v-divider>
          <v-row>
            <v-col cols="4">
              <v-container fluid>
                <div class="title">
                  Ratio
                </div>
                <v-radio-group v-model="computedData.ratio">
                  <v-radio
                    v-for="(ratio, index) in ratios"
                    :key="index"
                    :label="ratio.text"
                    :value="ratio.value"
                  ></v-radio>
                </v-radio-group>
                <v-subheader class="pl-0">
                  Image Scale
                </v-subheader>
                <v-slider
                  v-model="computedData.scale"
                  :step="0.1"
                  :min="0"
                  :max="10"
                  :thumb-size="24"
                  thumb-label="always"
                ></v-slider>
                <v-subheader class="pl-0">
                  Image Rotate
                </v-subheader>
                <v-slider
                  v-model="computedData.rotate"
                  :max="360"
                  :thumb-size="24"
                  thumb-label="always"
                ></v-slider>
              </v-container>
            </v-col>
            <v-col cols="8" pa-2>
              <v-layout
                class="justify-center text-xs-center"
                style="max-height: 800px; overflow: auto;"
              >
                <v-card>
                  <v-img contain>
                    <clipper-basic
                      :scale="computedData.scale"
                      :rotate="computedData.rotate"
                      :ratio="computedData.ratio"
                      @load="imgLoad(index)"
                      :style="computedData.basicStyle"
                      :ref="computedData.clipper"
                      :src="computedData.imgURL"
                      :preview="computedData.preview"
                    >
                      <div class="placeholder" slot="placeholder">No image</div>
                    </clipper-basic>
                  </v-img>
                </v-card>
              </v-layout>
            </v-col>
          </v-row>
        </v-card>
      </v-dialog>
    </v-col>
  </v-row>
</template>

<script>
import BaseBtn from "@/components/base/BaseBtn";
export default {
  name: "ImageSelectAndPreview",
  components: { BaseBtn },
  props: {
    headline: {
      type: String,
      required: false,
      default: "Add Images",
    },
    max: {
      type: Number,
      required: true,
    },
    clear: {
      type: Boolean,
      required: false,
    }
  },
  data() {
    return {
      pictureInput: {},
      numberOfImage: 1,
      placeHolder: "/images/util/choose_image_placeholder.png",
      noOfImages: 1,
      maxWidth: 700,
      maxHeight: 650,
      ratios: [
        {
          text: "No Ratio",
          value: NaN,
        },
        {
          text: "1",
          value: 1,
        },
        {
          text: "4/3",
          value: 4 / 3,
        },
        {
          text: "16/9",
          value: 16 / 9,
        },
      ],
      imageData: [],
    };
  },
  computed: {
    buttonText() {
      return this.computedDataList.length > 0 ? "Add More" : "Add";
    },
    computedDataList() {
      let data = this.imageData;
      for (let i = 0; i < data.length; i++) {
        if (!data[i].previewURL && data[i].imgURL) {
          data[i].dialog = true;
        }
        if (data[i].resultURL) {
          data[i].previewURL = data[i].resultURL;
        } else {
          data[i].previewURL = data[i].imgURL;
        }
        data[i].basicStyle = {
          maxWidth: data[i].based + "px",
        };
      }
      return data;
    },
  },
  watch: {
    imageData(val) {
      this.$emit("image-data", val);
    },
    clear(val){
      if (val){
        this.imageData = [];
      }
    }
  },
  methods: {
    fileChoose() {
      if (this.imageData.length < this.max) {
        this.imageData.push({
          imgURL: "",
          previewURL: "",
          resultURL: "",
          dialog: false,
          preview: `my-preview${this.imageData.length}`,
          clipper: `clipper${this.imageData.length}`,
          valid: true,
          blob: null,
          scale: 1,
        });
      } else {
        this.$emit("max-exceed", true);
      }
    },
    getResult(index) {
      const canvas = this.$refs[this.imageData[index].clipper][0].clip(); //call component's clip method
      this.imageData[index].resultURL = canvas.toDataURL("image/jpg", 1); //canvas->image
      canvas.toBlob((blob) => {
        this.imageData[index].blob = blob;
      });
      this.imageData[index].dialog = false;
    },
    remove(index) {
      this.imageData.splice(index, 1);
    },
    imgLoad: function (index) {
      const imgRatio = this.$refs[this.imageData[index].clipper][0].imgRatio;
      if (imgRatio < 1) this.imageData[index].based = this.maxHeight * imgRatio;
      else this.imageData[index].based = this.maxWidth;
    },
  },
};
</script>

<style scoped></style>
