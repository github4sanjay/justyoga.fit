<template>
  <div>
    <q-img
      :ratio="ratio"
      :src="
        computedData.previewURL
          ? computedData.previewURL
          : initialImage
          ? initialImage
          : placeHolder
      "
    >
      <div class="absolute-bottom text-subtitle1 text-center">
        <div class="row justify-center">
          <div class="col-4" v-if="computedData.previewURL">
            <q-btn
              color="positive"
              label="EDIT"
              size="sm"
              @click="computedData.dialog = !computedData.dialog"
            >
            </q-btn>
          </div>
          <div class="col-4">
            <clipper-upload v-model="computedData.imgURL" accept="image/*">
              <q-btn
                color="positive"
                @click="computedData.dialog = !computedData.dialog"
                label="CHOOSE"
                size="sm"
              >
              </q-btn>
            </clipper-upload>
          </div>
        </div>
      </div>
      <q-dialog :persistent="false" v-model="computedData.dialog" width="1400">
        <div>
          <q-toolbar class="bg-primary text-white">
            <q-toolbar-title>Edit Image</q-toolbar-title>
            <q-btn
              flat
              label="clip image"
              :loading="uploading"
              @click="getResult()"
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
                      size="sm"
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
                    Image Rotate
                    <q-slider
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
                    @load="imgLoad()"
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
    </q-img>
    <div v-if="errors" class="q-pa-sm text-negative">
      {{ errors.join(",") }}
    </div>
  </div>
</template>

<script>
export default {
  name: "SelectImage",
  components: {},
  props: {
    initialImage: {
      type: String,
      required: false,
      default: "statics/images/util/choose_image_placeholder.png"
    },
    ratio: {
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
      placeHolder: "statics/images/util/choose_image_placeholder.png",
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
      if (val) {
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
        };
      }
    }
  },
  methods: {
    getResult() {
      this.uploading = true;
      const canvas = this.$refs.clipper.clip(); //call component's clip method
      this.imageData.resultURL = canvas.toDataURL("image/jpg", 1); //canvas->image
      canvas.toBlob(blob => {
        this.imageData.blob = blob;
      });
      this.imageData.dialog = false;
      this.uploading = false;
    },
    imgLoad: function() {
      const imgRatio = this.$refs.clipper.imgRatio;
      if (imgRatio < 1) this.imageData.based = this.maxHeight * imgRatio;
      else this.imageData.based = this.maxWidth;
    }
  }
};
</script>

<style scoped></style>
