<template>
  <v-container fluid class="pa-0">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="closeBtnClick()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Update Your Interests</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text :loading="updating" @click="submit">
          Update
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-container>
      <v-row dense>
        <v-col cols="12">
          <v-checkbox
            v-model="isTrainerCheckBox"
            label="Are you a yoga instructor?"
          ></v-checkbox>
        </v-col>
        <v-col cols="12">
          <v-checkbox
            v-model="isLookingForTrainerCheckBox"
            label="Are you looking for a yoga instructor?"
          ></v-checkbox>
        </v-col>
        <v-col cols="12">
          <v-checkbox
            v-model="isBloggerCheckBox"
            label="Are you a yoga blogger?"
          ></v-checkbox>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
export default {
  name: "InterestsForm",
  components: {},
  props: {
    interests: {
      type: Object,
      required: true,
    },
    updating: {
      type: Boolean,
      required: false,
    },
  },
  data: function () {
    return {
      isTrainerCheckBox: false,
      isLookingForTrainerCheckBox: false,
      isBloggerCheckBox: false,
    };
  },
  methods: {
    submit() {
      this.$emit("update", {
        trainer: this.isTrainerCheckBox,
        lookingForTrainer: this.isLookingForTrainerCheckBox,
        blogger: this.isBloggerCheckBox,
      });
    },
    closeBtnClick() {
      this.$emit("close", {});
    },
  },
  computed: {},
  created() {
    if (this.interests) {
      this.isTrainerCheckBox = this.interests.trainer;
      this.isLookingForTrainerCheckBox = this.interests.lookingForTrainer;
      this.isBloggerCheckBox = this.interests.blogger;
    }
  },
  mounted() {},
};
</script>

<style scoped></style>
