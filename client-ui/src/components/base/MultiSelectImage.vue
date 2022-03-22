<template>
  <div class="row justify-center">
    <div class="col-12">
      <div class="q-mb-sm">
        {{ headline }}
        <q-btn
          color="primary"
          :label="buttonText"
          size="sm"
          @click="fileChoose"
        />
      </div>
    </div>
    <div
      class="q-pa-sm"
      v-for="(computedData, index) in computedDataList"
      :key="index"
    >
      <div style="width:220px">
        <q-card square>
          <div class="row justify-center">
            <div class="col-12 q-my-sm">
              <q-img
                contain
                :src="
                  computedData.previewURL
                    ? computedData.previewURL
                    : placeHolder
                "
                :ratio="4 / 3"
              >
              </q-img>
            </div>
            <div class="col-4 q-mb-sm">
              <div class="column items-center">
                <div class="col">
                  <q-btn
                    v-if="computedData.previewURL"
                    color="primary"
                    label="Edit"
                    size="sm"
                    @click="computedData.dialog = !computedData.dialog"
                  />
                </div>
              </div>
            </div>
            <div class="col-4 q-mb-sm">
              <clipper-upload v-model="computedData.imgURL" accept="image/*">
                <q-btn color="primary" label="Choose" size="sm" />
              </clipper-upload>
            </div>
            <div class="col-4 q-mb-sm">
              <q-btn
                color="negative"
                label="Remove"
                size="sm"
                @click="remove(index)"
              />
            </div>
          </div>
        </q-card>
      </div>
      <q-dialog :persistent="false" v-model="computedData.dialog" width="1400">
        <div>
          <q-toolbar class="bg-primary text-white">
            <q-toolbar-title>Edit Image</q-toolbar-title>
            <q-btn
              flat
              label="clip image"
              :loading="uploading"
              @click="getResult(index)"
            />
            <q-btn flat round icon="close" v-close-popup />
          </q-toolbar>
          <q-card square flat>
            <div class="row">
              <div class="col-4">
                <div>
                  <div class="q-pa-sm">
                    Ratio
                    <q-option-group
                      dense
                      size="20px"
                      v-model="computedData.ratio"
                      :options="ratios"
                      color="primary"
                    />
                  </div>
                  <div class="q-pa-sm">
                    Image Scale
                    <q-slider
                      v-model="computedData.scale"
                      :step="0.1"
                      :min="0"
                      :max="10"
                      color="primary"
                      label
                    />
                  </div>
                  <div class="q-pa-sm">
                    Image Rotate<q-slider
                      v-model="computedData.rotate"
                      :step="10"
                      :min="0"
                      :max="360"
                      color="primary"
                      label
                    />
                  </div>
                </div>
              </div>
              <div class="col-8 q-pa-sm">
                <div class="row">
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
                    <div class="placeholder" slot="placeholder">
                      No image
                    </div>
                  </clipper-basic>
                </div>
              </div>
            </div>
          </q-card>
        </div>
      </q-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "MultiSelectImage",
  components: {},
  props: {
    headline: {
      type: String,
      required: false,
      default: "Add Images"
    },
    max: {
      type: Number,
      required: true
    },
    clear: {
      type: Boolean,
      required: false
    }
  },
  data() {
    return {
      pictureInput: {},
      numberOfImage: 1,
      placeHolder: "statics/images/util/choose_image_placeholder.png",
      noOfImages: 1,
      maxWidth: 700,
      maxHeight: 650,
      uploading: false,
      ratios: [
        {
          label: "No Ratio",
          value: NaN
        },
        {
          label: "1",
          value: 1
        },
        {
          label: "4/3",
          value: 4 / 3
        },
        {
          label: "16/9",
          value: 16 / 9
        }
      ],
      imageData: []
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
          maxWidth: data[i].based + "px"
        };
      }
      return data;
    }
  },
  watch: {
    imageData(val) {
      this.$emit("image-data", val);
    },
    clear(val) {
      if (val) {
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
          scale: 1
        });
      } else {
        this.$emit("max-exceed", true);
      }
    },
    getResult(index) {
      this.uploading = true;
      const canvas = this.$refs[this.imageData[index].clipper][0].clip(); //call component's clip method
      this.imageData[index].resultURL = canvas.toDataURL("image/jpg", 1); //canvas->image
      canvas.toBlob(blob => {
        this.imageData[index].blob = blob;
      });
      this.imageData[index].dialog = false;
      this.uploading = false;
    },
    remove(index) {
      this.imageData.splice(index, 1);
    },
    imgLoad: function(index) {
      const imgRatio = this.$refs[this.imageData[index].clipper][0].imgRatio;
      if (imgRatio < 1) this.imageData[index].based = this.maxHeight * imgRatio;
      else this.imageData[index].based = this.maxWidth;
    }
  }
};
</script>

<style scoped></style>
