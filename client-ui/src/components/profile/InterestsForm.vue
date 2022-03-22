<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Update Your Interests</q-toolbar-title>
      <q-btn flat label="Save" :loading="updatingInterests" @click="submit()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <div class="column">
        <div class="col items-center">
          <q-checkbox
            left-label
            v-model="isTrainerCheckBox"
            label="Are you a yoga instructor?"
          />
        </div>
        <div class="col items-center">
          <q-checkbox
            left-label
            v-model="isLookingForTrainerCheckBox"
            label="Are you looking for a yoga instructor?"
          />
        </div>
        <div class="col items-center">
          <q-checkbox
            left-label
            v-model="isBloggerCheckBox"
            label="Are you a yoga blogger?"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "InterestsForm",
  components: {},
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  data: function() {
    return {
      isTrainerCheckBox: false,
      isLookingForTrainerCheckBox: false,
      isBloggerCheckBox: false,
      updatingInterests: false
    };
  },
  methods: {
    submit() {
      this.updateInterest({
        trainer: this.isTrainerCheckBox,
        lookingForTrainer: this.isLookingForTrainerCheckBox,
        blogger: this.isBloggerCheckBox
      });
    },
    updateInterest(val) {
      this.updatingInterests = true;
      val.userId = this.user.id;
      if (this.user.interest) val.id = this.user.interest.id; // need id for update
      this.$store
        .dispatch("users/updateInterest", val)
        .then(() => {
          this.updatingInterests = false;
          this.closeBtnClick();
        })
        .catch(() => (this.updatingInterests = false));
    },
    closeBtnClick() {
      this.$emit("close", {});
    }
  },
  computed: {},
  created() {
    if (this.user && this.user.interest) {
      let interests = this.user.interest;
      this.isTrainerCheckBox = interests.trainer;
      this.isLookingForTrainerCheckBox = interests.lookingForTrainer;
      this.isBloggerCheckBox = interests.blogger;
    }
  },
  mounted() {}
};
</script>

<style scoped></style>
