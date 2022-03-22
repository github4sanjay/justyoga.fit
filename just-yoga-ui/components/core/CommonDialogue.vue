<template>
  <client-only>
    <div class="text-center">
      <v-dialog v-model="commonLoadingDialog" persistent fullscreen>
        <v-sheet color="black" height="100%">
          <v-row class="fill-height" align="center" justify="center">
            <v-card flat color="black">
              <v-card-title>
                <div class="mr-3 colorPrimaryText--text">{{dialogText}}</div>
                <v-progress-circular indeterminate color="white" class="mb-0">
                </v-progress-circular>
              </v-card-title>
            </v-card>
          </v-row>
        </v-sheet>
      </v-dialog>
    </div>
  </client-only>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "CommonDialogue",
  data() {
    return {
      // test: true
    };
  },
  computed: {
    ...mapGetters({
      commonLoadingDialog: "shared/dialog",
      dialogText: "shared/dialogText",
    }),
  },
};
</script>

<style scoped></style>
