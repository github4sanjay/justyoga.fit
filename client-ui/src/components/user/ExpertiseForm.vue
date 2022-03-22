<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>{{ headline }}</q-toolbar-title>
      <q-btn flat label="Save" :loading="updating" @click="submit()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <multi-select
        :reload="reload"
        :selections="trainerExpertise"
        @change-selections="var_expertise = $event"
        :options="expertise"
      />
    </div>
  </div>

  <!-- <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Update Details</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="updating" @click="submit">
          Update
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
      <multi-select
        :reload="reload"
        :headline="headline"
        :selections="trainerExpertise"
        @change-selections="var_expertise = $event"
        :options="expertise"
      />
    </v-container>
  </v-container> -->
</template>

<script>
import { validationMixin } from "vuelidate";
import MultiSelect from "components/base/MultiSelect";

export default {
  name: "ExpertiseForm",
  mixins: [validationMixin],

  validations: {},
  components: {
    MultiSelect
  },
  props: {
    reload: {
      type: Boolean,
      default: false
    },
    headline: {
      type: String,
      required: true
    },
    trainerExpertise: {
      type: Array,
      required: true
    },
    expertise: {
      type: Array,
      required: true
    },
    updating: {
      type: Boolean,
      default: false
    }
  },
  data: function() {
    return {
      var_expertise: []
    };
  },
  methods: {
    submit() {
      this.$emit("update", this.var_expertise);
    },
    closeBtnClick() {
      this.$emit("close", {});
    }
  },
  computed: {},
  created() {
    this.var_expertise = this.trainerExpertise;
  },
  mounted() {}
};
</script>

<style scoped></style>
