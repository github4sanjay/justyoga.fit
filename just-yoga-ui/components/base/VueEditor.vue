<template>
  <v-card light>
<div class="quillWrapper">
  <div ref="quillContainer" :id="id"></div>
</div>
  </v-card>
</template>
<script>
const Quill = process.browser ? require("quill") : "";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import Util from "@/utils/Util";

let defaultToolbar = [
  ["bold", "italic", "underline", "strike"],
  ["blockquote", "code-block"],

  [{ list: "ordered" }, { list: "bullet" }],

  [{ indent: "-1" }, { indent: "+1" }],
  [{ header: [1, 2, 3, 4, 5, 6, false] }],

  [{ color: [] }, { background: [] }],
  [{ font: [] }],
  [{ align: [] }],

  ["clean"]
];

export default {
  name: "vue-editor",
  props: {
    value: Object,
    id: {
      type: String,
      default: "quill-container"
    },
    placeholder: String,
    disabled: Boolean,
    editorToolbar: Array,
    clear: {
      type: Boolean,
      required: false,
    }
  },

  data() {
    return {
      quill: null,
      editor: null,
      toolbar: this.editorToolbar ? this.editorToolbar : defaultToolbar
    };
  },

  mounted() {
    this.initializeVue2Editor();
    this.handleUpdatedEditor();
  },

  watch: {
    value(val) {
      if (
        !Util.isObjectEmpty(val) &&
        val !== this.editor.innerHTML &&
        !this.quill.hasFocus()
      ) {
        this.editor.innerHTML = val;
      }
    },
    disabled(status) {
      this.quill.enable(!status);
    },
    clear(val) {
      if (val) {
        this.editor.innerHTML = "";
      }
    }
  },

  methods: {
    initializeVue2Editor() {
      this.setQuillElement();
      this.setEditorElement();
      this.checkForInitialContent();
    },

    setQuillElement() {
      this.quill = new Quill(this.$refs.quillContainer, {
        modules: {
          toolbar: this.toolbar
        },
        placeholder: this.placeholder ? this.placeholder : "",
        theme: "snow",
        readOnly: this.disabled ? this.disabled : false
      });
    },

    setEditorElement() {
      this.editor = document.querySelector(`#${this.id} .ql-editor`);
    },

    checkForInitialContent() {
      if (!Util.isObjectEmpty(this.value)) {
        this.editor.innerHTML = this.value.innerHTML;
      } else {
        this.editor.innerHTML = "";
      }
    },

    handleUpdatedEditor() {
      this.quill.on("text-change", () => {
        this.$emit("input", {
          html: this.editor.innerHTML,
          text: this.quill.getText()
        });
      });
    }
  }
};
</script>

<style>
#quill-container {
  height: 200px;
}
</style>
