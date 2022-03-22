<template>
  <div>
    <q-select
      label="Select Options"
      multiple
      filled
      use-chips
      stack-label
      v-model="var_selections"
      :options="options"
      :option-label="
        opt => (Object(opt) === opt && 'name' in opt ? opt.name : '- Null -')
      "
      emit-value
      map-options
    />
  </div>
</template>

<script>
export default {
  name: "MultiSelect",
  props: {
    options: {
      type: Array,
      required: false,
      default: () => []
    },
    selections: {
      type: Array,
      required: false,
      default: () => []
    },
    reload: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      var_selections: this.selections
    };
  },
  computed: {},
  watch: {
    var_selections(val) {
      this.$emit("change-selections", val);
    },
    reload(val) {
      if (val) {
        this.var_selections = this.selections;
      }
    }
  }
};
</script>

<style scoped></style>
