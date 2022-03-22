<template>
  <v-card flat>
    <v-row align="center" justify="center">
      <v-col cols="12" class="pa-0">
        <v-card-text
          :class="{
            'py-0': true,
            'justify-center': $vuetify.breakpoint.smAndDown,
            title: $vuetify.breakpoint.smAndUp,
            'subtitle-2': $vuetify.breakpoint.xsOnly,
          }"
        >
          Basic information
          <v-btn
            @click="basicInfoDialogue = !basicInfoDialogue"
            v-show="editable"
            class="ma-2"
            :small="$vuetify.breakpoint.smAndUp"
            :x-small="$vuetify.breakpoint.xsOnly"
          >
            Edit
          </v-btn>
        </v-card-text>
      </v-col>
      <v-col
        v-if="user.basicInfo"
        cols="12"
        class="py-0 ml-3 align-center justify-center"
      >
        <v-chip-group column>
          <v-chip
            v-show="user.basicInfo.age"
            :small="$vuetify.breakpoint.xsOnly"
            label
          >
            <v-icon :small="$vuetify.breakpoint.xsOnly" left>stars </v-icon>
            {{ user.basicInfo.age }} years old
          </v-chip>
          <v-chip
            v-show="user.basicInfo.experienceYears"
            :small="$vuetify.breakpoint.xsOnly"
            label
          >
            <v-icon :small="$vuetify.breakpoint.xsOnly" left>explicit </v-icon>
            {{ user.basicInfo.experienceYears }} years of experience
          </v-chip>

          <v-chip
            v-show="user.basicInfo.formattedAddress"
            :small="$vuetify.breakpoint.xsOnly"
            label
          >
            <v-icon :small="$vuetify.breakpoint.xsOnly" left
              >location_on
            </v-icon>
            {{ user.basicInfo.formattedAddress }}
          </v-chip>
        </v-chip-group>
      </v-col>
    </v-row>
    <v-dialog width="600" v-model="basicInfoDialogue">
      <v-card>
        <basic-info-form
          @close="basicInfoDialogue = false"
          @error="trainerFormError"
          @update-trainer="updateTrainer"
          :updating="updatingBasicInfo"
          :trainer="user.basicInfo"
        />
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import BasicInfoForm from "./BasicInfoForm";
export default {
  name: "BasicInfoSection",
  components: { BasicInfoForm },
  props: {
    user: {
      type: Object,
      required: true,
    },
    editable: {
      type: Boolean,
      required: true,
    },
  },
  data: function () {
    return {
      updatingBasicInfo: false,
      basicInfoDialogue: false,
    };
  },
  methods: {
    trainerFormError(val) {
      this.$store.dispatch("shared/setErrorText", val);
      this.$store.dispatch("shared/setErrorSnackbar", true);
    },
    async updateTrainer(val) {
      this.updatingBasicInfo = true;
      try {
        let basicInfo = await this.$store.dispatch("place/storePlace", val);

        basicInfo.age = val.age;
        basicInfo.experienceMonths = val.experienceMonths;
        basicInfo.experienceYears = val.experienceYears;
        basicInfo.userId = this.user.id;
        if (this.user.basicInfo) {
          basicInfo.id = this.user.basicInfo.id;
        }
        this.$store
          .dispatch("users/updateBasicInfo", basicInfo)
          .then(() => {
            this.updatingBasicInfo = false;
            this.basicInfoDialogue = false;
          })
          .catch(() => {
            this.updatingBasicInfo = false;
          });
      } catch (e) {
        this.$nuxt.error({ statusCode: 500 });
      }
    },
  },
};
</script>
