<template>
  <div>
    <q-editor
      :placeholder="placeholder"
      ref="editor"
      v-model="qeditor"
      :definitions="{
        image: {
          tip: 'Insert Image',
          icon: 'photo',
          label: 'Image',
          handler: insertImg
        },
        video: {
          tip: 'Insert Video',
          icon: 'videocam',
          label: 'Video',
          handler: insertVideo
        }
      }"
      :toolbar="[
        [
          {
            label: $q.lang.editor.align,
            icon: $q.iconSet.editor.align,
            fixedLabel: true,
            options: ['left', 'center', 'right', 'justify']
          },
          'print',
          'fullscreen',
          'image',
          'video'
        ],
        ['bold', 'italic', 'strike', 'underline', 'subscript', 'superscript'],
        [
          'token',
          'hr',
          'link',
          'custom_btn',
          'quote',
          'unordered',
          'ordered',
          'outdent',
          'indent'
        ],
        [
          {
            label: $q.lang.editor.formatting,
            icon: $q.iconSet.editor.formatting,
            list: 'no-icons',
            options: ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'code']
          },
          {
            label: $q.lang.editor.fontSize,
            icon: $q.iconSet.editor.fontSize,
            fixedLabel: true,
            fixedIcon: true,
            list: 'no-icons',
            options: [
              'size-1',
              'size-2',
              'size-3',
              'size-4',
              'size-5',
              'size-6',
              'size-7'
            ]
          },
          {
            label: $q.lang.editor.defaultFont,
            icon: $q.iconSet.editor.font,
            fixedIcon: true,
            list: 'no-icons',
            options: [
              'default_font',
              'arial',
              'arial_black',
              'comic_sans',
              'courier_new',
              'impact',
              'lucida_grande',
              'times_new_roman',
              'verdana'
            ]
          },
          'removeFormat',
          'undo',
          'redo'
        ]
      ]"
      :fonts="{
        arial: 'Arial',
        arial_black: 'Arial Black',
        comic_sans: 'Comic Sans MS',
        courier_new: 'Courier New',
        impact: 'Impact',
        lucida_grande: 'Lucida Grande',
        times_new_roman: 'Times New Roman',
        verdana: 'Verdana'
      }"
    >
      <template v-slot:token>
        <q-btn-dropdown
          dense
          no-caps
          ref="token"
          no-wrap
          unelevated
          color="white"
          text-color="primary"
          label="Text Color"
          size="sm"
        >
          <q-list dense>
            <q-item
              tag="label"
              clickable
              @click="color('backColor', highlight)"
            >
              <q-item-section side>
                <q-icon name="highlight" />
              </q-item-section>
              <q-item-section>
                <q-color
                  v-model="highlight"
                  default-view="palette"
                  no-header
                  no-footer
                  :palette="[
                    '#ffccccaa',
                    '#ffe6ccaa',
                    '#ffffccaa',
                    '#ccffccaa',
                    '#ccffe6aa',
                    '#ccffffaa',
                    '#cce6ffaa',
                    '#ccccffaa',
                    '#e6ccffaa',
                    '#ffccffaa',
                    '#ff0000aa',
                    '#ff8000aa',
                    '#ffff00aa',
                    '#00ff00aa',
                    '#00ff80aa',
                    '#00ffffaa',
                    '#0080ffaa',
                    '#0000ffaa',
                    '#8000ffaa',
                    '#ff00ffaa'
                  ]"
                  class="my-picker"
                />
              </q-item-section>
            </q-item>
            <q-item
              tag="label"
              clickable
              @click="color('foreColor', foreColor)"
            >
              <q-item-section side>
                <q-icon name="format_paint" />
              </q-item-section>
              <q-item-section>
                <q-color
                  v-model="foreColor"
                  no-header
                  no-footer
                  default-view="palette"
                  :palette="[
                    '#ff0000',
                    '#ff8000',
                    '#ffff00',
                    '#00ff00',
                    '#00ff80',
                    '#00ffff',
                    '#0080ff',
                    '#0000ff',
                    '#8000ff',
                    '#ff00ff'
                  ]"
                  class="my-picker"
                />
              </q-item-section>
            </q-item>
          </q-list>
        </q-btn-dropdown>
      </template>
    </q-editor>
    <q-dialog v-model="imageDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.sm ? '650px' : ''
        }"
      >
        <q-toolbar class="bg-primary text-white">
          <q-toolbar-title>Choose Image</q-toolbar-title>
          <q-btn
            flat
            label="Attach In Blog"
            :loading="mediaUploading"
            @click="imageSubmit()"
          />
          <q-btn flat round icon="close" v-close-popup />
        </q-toolbar>
        <single-image-upload
          @image-data="onChangeInImages"
          :ratio="2"
          :clear="clearImage"
        />
      </q-card>
    </q-dialog>
    <q-dialog v-model="videoDialogue">
      <q-card
        :style="{
          'min-width': $q.screen.gt.sm ? '450px' : ''
        }"
      >
        <q-toolbar class="bg-primary text-white">
          <q-toolbar-title>Choose Video</q-toolbar-title>
          <q-btn
            flat
            label="Attach In Blog"
            :loading="mediaUploading"
            @click="videoSubmit()"
          />
          <q-btn flat round icon="close" v-close-popup />
        </q-toolbar>
        <q-file
          accept="video/*"
          v-model="videoFile"
          label="Pick file"
          style="max-width: 300px"
          class="q-ma-sm"
        >
          <template v-slot:prepend>
            <q-icon name="attach_file" />
          </template>
        </q-file>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import SingleImageUpload from "components/util/SelectImage";
import AxiosUtil from "src/utils/AxiosUtil";
import ApiEndpoints from "src/constants/ApiEndpoints";
import NotifyUtil from "src/utils/NotifyUtil";
import MediaUploader from "src/utils/MediaUploader";

export default {
  name: "Editor",
  components: { SingleImageUpload },
  props: {
    default: {
      type: String,
      required: false
    },
    clear: {
      type: Boolean,
      required: false
    },
    placeholder: {
      type: String,
      required: false,
      default: "Write here"
    }
  },
  data() {
    return {
      foreColor: "#000000",
      highlight: "#ffff00aa",
      qeditor: "",
      imageDialogue: false,
      imageData: null,
      clearImage: false,
      mediaUploading: false,
      videoDialogue: false,
      videoFile: null
    };
  },
  created() {
    if (this.default) {
      this.qeditor = this.default;
    }
  },
  methods: {
    color(cmd, name) {
      const edit = this.$refs.editor;
      this.$refs.token.hide();
      edit.caret.restore();
      edit.runCmd(cmd, name);
      edit.focus();
    },
    onChangeInImages(val) {
      this.imageData = val;
      this.clearImage = false;
    },
    insertImg() {
      this.imageDialogue = true;
    },
    insertVideo() {
      this.videoDialogue = true;
    },
    async videoSubmit() {
      if (!this.videoFile) {
        NotifyUtil.showError("Please choose an video to attach");
        return;
      }
      try {
        this.mediaUploading = true;
        let media = await this.uploadMedia({
          media: this.videoFile,
          contentType: this.videoFile.type
        });
        document.execCommand(
          "insertHTML",
          true,
          '<div style="overflow: hidden;padding-top: 56.25%;position: relative;"><iframe style="border: 0;height: 100%;left: 0;position: absolute;top: 0;width: 100%;" src="' +
            media.url +
            '" frameborder="0" allowfullscreen id="' +
            media.id +
            '"></iframe></div>'
        );
      } catch (e) {
        NotifyUtil.showError("Something went wrong");
      } finally {
        this.videoDialogue = false;
        this.mediaUploading = false;
      }
    },
    async imageSubmit() {
      if (!this.imageData || !this.imageData.blob) {
        NotifyUtil.showError("Please choose an image to attach");
        return;
      }
      try {
        this.mediaUploading = true;
        let media = await this.uploadMedia({
          media: this.imageData.blob,
          contentType: this.imageData.blob.type
        });

        document.execCommand(
          "insertHTML",
          true,
          '<div style="display: grid;height: 100%;"><img src="' +
            media.url +
            '" style="max-width: 100%;max-height: 100%;margin: auto;object-fit: contain" id= "' +
            media.id +
            '" /></div>'
        );
      } catch (e) {
        NotifyUtil.showError("Something went wrong");
      } finally {
        this.imageDialogue = false;
        this.mediaUploading = false;
      }
    },
    imageClose() {},
    async uploadMedia(payload) {
      let url = await MediaUploader.getUploadUrlPublic();
      await MediaUploader.upload(
        url.uploadUrl,
        payload.media,
        payload.contentType
      );
      return { url: url.downloadUrl, id: url.id };
    }
  },
  watch: {
    qeditor(val) {
      this.$emit("input", val);
    },
    clear(val) {
      if (val) {
        this.qeditor = null;
      }
    }
  }
};
</script>
<style lang="sass" scoped>
.my-picker
  max-width: 150px
</style>
