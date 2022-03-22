<template>
  <v-card class="mt-3">
    <v-row>
      <v-col cols="12" class="mt-3">
        <v-card flat>
          <v-card-text
            :class="{
              'py-0': true,
              'justify-center': $vuetify.breakpoint.smAndDown,
              headline: $vuetify.breakpoint.mdAndUp,
              title: $vuetify.breakpoint.sm,
              'subtitle-2': $vuetify.breakpoint.xsOnly,
            }"
          >
            Medical Expertise
            <v-btn
              v-show="editable"
              class="ma-2"
              x-small
              @click="editClick('MEDICAL_EXPERTISE')"
            >
              Edit
            </v-btn>
          </v-card-text>
          <v-row v-show="user.medicalExpertise">
            <span
              class="mx-5 mt-5"
              v-for="(medicalExpertise, i) in user.medicalExpertise"
              :key="i"
            >
              <medical-expertise :id="medicalExpertise.medicalExpertiseId" />
            </span>
          </v-row>
        </v-card>
      </v-col>
      <v-col cols="12" class="mt-3">
        <v-card flat>
          <v-card-text
            :class="{
              'py-0': true,
              'justify-center': $vuetify.breakpoint.smAndDown,
              headline: $vuetify.breakpoint.mdAndUp,
              title: $vuetify.breakpoint.sm,
              'subtitle-2': $vuetify.breakpoint.xsOnly,
            }"
          >
            Yoga Expertise
            <v-btn
              v-show="editable"
              class="ma-2"
              x-small
              @click="editClick('YOGA_EXPERTISE')"
            >
              Edit
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" class="mt-3">
        <v-card flat>
          <v-card-text
            :class="{
              'py-0': true,
              'justify-center': $vuetify.breakpoint.smAndDown,
              headline: $vuetify.breakpoint.mdAndUp,
              title: $vuetify.breakpoint.sm,
              'subtitle-2': $vuetify.breakpoint.xsOnly,
            }"
          >
            Yoga Certificate
            <v-btn
              v-show="editable"
              class="ma-2"
              x-small
              @click="editClick('YOGA_CERTIFICATE')"
            >
              Edit
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
      <v-dialog width="400" v-model="var_dialogue">
        <v-card flat>
          <expertise-form
            :reload="var_dialogue"
            :expertise="expertise"
            :headline="headline"
            :trainerExpertise="trainerExpertise"
            :updating="updating"
            @close="var_dialogue = false"
            @update="update"
          />
        </v-card>
      </v-dialog>
    </v-row>
  </v-card>
</template>

<script>
import ExpertiseForm from "@/components/expertise/ExpertiseForm";
import { mapGetters } from "vuex";
import MedicalExpertise from "@/components/expertise/MedicalExpertise";

export default {
  name: "expertise",
  components: { MedicalExpertise, ExpertiseForm },
  middleware: "expertise",
  data: function () {
    return {
      var_dialogue: false,
      expertise: [],
      trainerExpertise: [],
      updating: false,
      headline: null,
      section: null,
      medicalExpertise: this.$store.getters["expertise/medicalExpertise"],
      yogaExpertise: this.$store.getters["expertise/yogaExpertise"],
      yogaCertificates: this.$store.getters["expertise/yogaCertificates"],
    };
  },
  computed: {
    ...mapGetters({
      currentUser: "login/user",
    }),
    user() {
      return this.$store.getters["users/user"](this.$route.params.id);
    },
    editable() {
      return (
        this.currentUser && this.user && this.currentUser.id === this.user.id
      );
    },
  },
  methods: {
    editClick(val) {
      switch (val) {
        case "MEDICAL_EXPERTISE":
          this.section = "MEDICAL_EXPERTISE";
          this.expertise = this.medicalExpertise;
          this.trainerExpertise = [];
          this.user.medicalExpertise.forEach((trainerExpertise) => {
            this.medicalExpertise.forEach((expertise) => {
              if (expertise.id === trainerExpertise.medicalExpertiseId) {
                this.trainerExpertise.push(expertise);
              }
            });
          });
          this.headline = "Select medical expertise";
          break;
        case "YOGA_EXPERTISE":
          this.section = "MEDICAL_EXPERTISE";
          this.expertise = this.yogaExpertise;
          this.trainerExpertise = [];
          this.user.yogaExpertise.forEach((trainerExpertise) => {
            this.yogaExpertise.forEach((expertise) => {
              if (expertise.id === trainerExpertise.yogaExpertiseId) {
                this.trainerExpertise.push(expertise);
              }
            });
          });
          this.headline = "Select yoga expertise";
          break;
        case "YOGA_CERTIFICATE":
          this.section = "MEDICAL_EXPERTISE";
          this.expertise = this.yogaCertificates;
          this.trainerExpertise = [];
          this.user.yogaCertificates.forEach((trainerExpertise) => {
            this.yogaExpertise.forEach((expertise) => {
              if (expertise.id === trainerExpertise.yogaCertificateId) {
                this.trainerExpertise.push(expertise);
              }
            });
          });
          this.headline = "Select yoga certificates";
          break;
      }
      this.var_dialogue = true;
    },
    update(val) {
      switch (this.section) {
        case "MEDICAL_EXPERTISE":
          this.updateMedicalExpertise(val);
          break;
        case "YOGA_EXPERTISE":
          this.updateYogaExpertise(val);
          break;
        case "YOGA_CERTIFICATE":
          this.updateYogaCertificate(val);
          break;
      }
    },
    updateMedicalExpertise(val) {
      let toBe = new Set();
      let present = new Set();

      let presentMedicalExpertise = this.user.medicalExpertise
        ? this.user.medicalExpertise
        : [];
      for (let i = 0; i < presentMedicalExpertise.length; i++) {
        present.add(presentMedicalExpertise[i].medicalExpertiseId);
      }

      let toDelete = new Set();
      let toAdd = new Set();
      for (let i = 0; i < val.length; i++) {
        if (!present.has(val[i].id)) {
          toAdd.add(val[i]);
        }
        toBe.add(val[i].id);
      }

      for (let i = 0; i < presentMedicalExpertise.length; i++) {
        if (!toBe.has(presentMedicalExpertise[i].medicalExpertiseId)) {
          toDelete.add(presentMedicalExpertise[i].id);
        }
      }

      toAdd.userId = this.user.id;
      this.updating = true;
      this.$store
        .dispatch("users/storeMedicalExpertise", toAdd)
        .then(() => {
          // now delete whatever was removed
          toDelete.userId = this.user.id;
          this.$store
            .dispatch("users/deleteMedicalExpertise", toDelete)
            .then(() => {
              this.updating = false;
              this.var_dialogue = false;
            })
            .catch(() => {
              this.updating = false;
            });
        })
        .catch(() => {
          this.updating = false;
        });
    },

    updateYogaExpertise(val) {
      let toBe = new Set();
      let present = new Set();

      let presentYogaExpertise = this.user.yogaExpertise
        ? this.user.yogaExpertise
        : [];
      for (let i = 0; i < presentYogaExpertise.length; i++) {
        present.add(presentYogaExpertise[i].medicalExpertiseId);
      }

      let toDelete = new Set();
      let toAdd = new Set();
      for (let i = 0; i < val.length; i++) {
        if (!present.has(val[i].id)) {
          toAdd.add(val[i]);
        }
        toBe.add(val[i].id);
      }

      for (let i = 0; i < presentYogaExpertise.length; i++) {
        if (!toBe.has(presentYogaExpertise[i].yogaExpertiseId)) {
          toDelete.add(presentYogaExpertise[i].id);
        }
      }

      toAdd.userId = this.user.id;
      this.updating = true;
      this.$store
        .dispatch("users/storeYogaExpertise", toAdd)
        .then(() => {
          // now delete whatever was removed
          toDelete.userId = this.user.id;
          this.$store
            .dispatch("users/deleteYogaExpertise", toDelete)
            .then(() => {
              this.updating = false;
              this.var_dialogue = false;
            })
            .catch(() => {
              this.updating = false;
            });
        })
        .catch(() => {
          this.updating = false;
        });
    },
    updateYogaCertificate(val) {
      let toBe = new Set();
      let present = new Set();

      let presentYogaCertificates = this.user.yogaCertificates
        ? this.user.yogaCertificates
        : [];
      for (let i = 0; i < presentYogaCertificates.length; i++) {
        present.add(presentYogaCertificates[i].yogaCertificateId);
      }

      let toDelete = new Set();
      let toAdd = new Set();
      for (let i = 0; i < val.length; i++) {
        if (!present.has(val[i].id)) {
          toAdd.add(val[i]);
        }
        toBe.add(val[i].id);
      }

      for (let i = 0; i < presentYogaCertificates.length; i++) {
        if (!toBe.has(presentYogaCertificates[i].yogaCertificateId)) {
          toDelete.add(presentYogaCertificates[i].id);
        }
      }

      toAdd.userId = this.userId.id;
      this.updating = true;
      this.$store
        .dispatch("users/storeYogaCertificate", toAdd)
        .then(() => {
          // now delete whatever was removed
          toDelete.userId = this.user.id;
          this.$store
            .dispatch("users/deleteYogaCertificate", toDelete)
            .then(() => {
              this.updating = false;
              this.var_dialogue = false;
            })
            .catch(() => {
              this.updating = false;
            });
        })
        .catch(() => {
          this.updating = false;
        });
    },
  },
};
</script>

<style scoped></style>
