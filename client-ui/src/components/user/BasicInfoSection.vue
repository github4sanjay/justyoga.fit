<template>
  <div>
    <q-card flat class="q-mr-sm q-pa-none">
      <q-card-actions class="q-pa-sm">
        <div class="text-bold">
          Basic Information & Location
        </div>
        <q-space />
        <q-btn
          @click="basicInfoDialogue = !basicInfoDialogue"
          v-show="editable"
          class="q-ma-2 q-px-sm"
          size="xs"
        >
          Edit
        </q-btn>
      </q-card-actions>
      <q-list v-if="user.basicInfo">
        <q-item v-ripple v-show="user.basicInfo.age">
          <q-item-section avatar>
            <q-icon color="primary" name="fa fa-birthday-cake" />
          </q-item-section>
          <q-item-section>
            <q-item-label> {{ user.basicInfo.age }} years old </q-item-label>
          </q-item-section>
        </q-item>
        <q-item v-ripple v-show="user.basicInfo.experienceYears">
          <q-item-section avatar>
            <q-icon color="primary" name="fas fa-user-tie" />
          </q-item-section>
          <q-item-section>
            <q-item-label
              >{{ user.basicInfo.experienceYears }} years of
              experience</q-item-label
            >
          </q-item-section>
        </q-item>
        <q-item v-ripple v-show="user.basicInfo.formattedAddress">
          <q-item-section avatar>
            <q-icon color="primary" name="fas fa-map-marker-alt" />
          </q-item-section>
          <q-item-section>
            <q-item-label>
              {{ `${user.basicInfo.formattedAddress}` }}</q-item-label
            >
          </q-item-section>
        </q-item>
      </q-list>
    </q-card>

    <q-dialog v-model="basicInfoDialogue" :maximized="$q.screen.xs">
      <q-card
        flat
        :style="{
          'min-width': $q.screen.gt.sm ? '400px' : ''
        }"
      >
        <basic-info-form @close="basicInfoDialogue = false" :user="user" />
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import BasicInfoForm from "components/user/BasicInfoForm";
export default {
  name: "BasicInfoSection",
  components: {
    BasicInfoForm
  },
  props: {
    user: {
      type: Object,
      required: true
    },
    editable: {
      type: Boolean,
      required: true
    }
  },
  data: function() {
    return {
      basicInfoDialogue: false
    };
  },
  methods: {}
};
</script>
