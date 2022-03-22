<template>
  <div>
    <div class="row">
      <div class="col-12">
        <q-card flat bordered square>
          <q-list bordered separator>
            <q-expansion-item
              switch-toggle-side
              group="somegroup"
              :default-opened="
                user.yogaExpertise && user.yogaExpertise.length > 0
              "
              expand-separator
              v-show="
                editable ||
                  (user.yogaExpertise && user.yogaExpertise.length > 0)
              "
            >
              <template v-slot:header>
                <q-item-section class="text-bold">
                  Yoga Expertise
                </q-item-section>

                <q-item-section side>
                  <div class="row items-center">
                    <q-btn
                      v-on:click.stop="editClick('YOGA_EXPERTISE')"
                      v-show="editable"
                      class="q-ma-2"
                      size="xs"
                    >
                      Edit
                    </q-btn>
                  </div>
                </q-item-section>
              </template>
              <q-card flat>
                <q-card-section v-show="user.yogaExpertise">
                  <q-chip
                    v-for="(yogaExpertise, i) in user.yogaExpertise"
                    :key="i"
                    square
                    dense
                    :label="
                      getYogaExpertise(yogaExpertise.yogaExpertiseId).name
                    "
                  />
                </q-card-section>
              </q-card>
            </q-expansion-item>
            <q-expansion-item
              switch-toggle-side
              group="somegroup"
              expand-separator
              v-show="
                editable ||
                  (user.yogaCertificates && user.yogaCertificates.length > 0)
              "
            >
              <template v-slot:header>
                <q-item-section class="text-bold">
                  Yoga Certificate
                </q-item-section>

                <q-item-section side>
                  <div class="row items-center">
                    <q-btn
                      v-on:click.stop="editClick('YOGA_CERTIFICATE')"
                      v-show="editable"
                      class="q-ma-2"
                      size="xs"
                    >
                      Edit
                    </q-btn>
                  </div>
                </q-item-section>
              </template>
              <q-card flat>
                <q-card-section v-show="user.yogaCertificates">
                  <q-chip
                    v-for="(yogaCertificate, i) in user.yogaCertificates"
                    :key="i"
                    square
                    dense
                    :label="
                      getYogaCertificate(yogaCertificate.yogaCertificateId).name
                    "
                  />
                </q-card-section>
              </q-card>
            </q-expansion-item>
            <q-expansion-item
              switch-toggle-side
              group="somegroup"
              expand-separator
              v-show="
                editable ||
                  (user.medicalExpertiseId &&
                    user.medicalExpertiseId.length > 0)
              "
            >
              <template v-slot:header>
                <q-item-section class="text-bold">
                  Medical Expertise
                </q-item-section>

                <q-item-section side>
                  <div class="row items-center">
                    <q-btn
                      v-on:click.stop="editClick('MEDICAL_EXPERTISE')"
                      v-show="editable"
                      class="q-ma-2"
                      size="xs"
                      >Edit</q-btn
                    >
                  </div>
                </q-item-section>
              </template>
              <q-card flat>
                <q-card-section v-show="user.medicalExpertise">
                  <q-chip
                    v-for="(medicalExpertise, i) in user.medicalExpertise"
                    :key="i"
                    square
                    dense
                    :label="
                      getMedicalExpertise(medicalExpertise.medicalExpertiseId)
                        .name
                    "
                  />
                </q-card-section>
              </q-card>
            </q-expansion-item>
          </q-list>
        </q-card>
      </div>
    </div>
    <div>
      <q-dialog v-model="var_dialogue">
        <q-card
          flat
          :style="{
            'min-width': $q.screen.gt.sm ? '400px' : ''
          }"
        >
          <expertise-form
            :reload="var_dialogue"
            :expertise="expertise"
            :headline="headline"
            :trainerExpertise="trainerExpertise"
            :updating="updating"
            @close="var_dialogue = false"
            @update="update"
          />
        </q-card>
      </q-dialog>
    </div>
  </div>
</template>

<script>
import ExpertiseForm from "components/user/ExpertiseForm";
export default {
  name: "ExpertieseSection",
  components: { ExpertiseForm },
  props: {
    user: {
      type: Object,
      required: true
    },
    editable: {
      type: Boolean,
      required: false
    }
  },
  data() {
    return {
      var_dialogue: false,
      expertise: [],
      trainerExpertise: [],
      updating: false,
      headline: null,
      section: null,
      medicalExpertise: this.$store.getters["expertise/medicalExpertise"],
      yogaExpertise: this.$store.getters["expertise/yogaExpertise"],
      yogaCertificates: this.$store.getters["expertise/yogaCertificates"]
    };
  },
  methods: {
    getMedicalExpertise(id) {
      return this.$store.getters["expertise/medicalExpertiseById"](id);
    },
    getYogaExpertise(id) {
      return this.$store.getters["expertise/yogaExpertiseById"](id);
    },
    getYogaCertificate(id) {
      return this.$store.getters["expertise/yogaCertificateById"](id);
    },
    editClick(val) {
      switch (val) {
        case "MEDICAL_EXPERTISE":
          this.section = "MEDICAL_EXPERTISE";
          this.expertise = this.medicalExpertise;
          this.trainerExpertise = [];
          this.user.medicalExpertise.forEach(trainerExpertise => {
            this.medicalExpertise.forEach(expertise => {
              if (expertise.id === trainerExpertise.medicalExpertiseId) {
                this.trainerExpertise.push(expertise);
              }
            });
          });
          this.headline = "Select medical expertise";
          break;
        case "YOGA_EXPERTISE":
          this.section = "YOGA_EXPERTISE";
          this.expertise = this.yogaExpertise;
          this.trainerExpertise = [];
          this.user.yogaExpertise.forEach(trainerExpertise => {
            this.yogaExpertise.forEach(expertise => {
              if (expertise.id === trainerExpertise.yogaExpertiseId) {
                this.trainerExpertise.push(expertise);
              }
            });
          });
          this.headline = "Select yoga expertise";
          break;
        case "YOGA_CERTIFICATE":
          this.section = "YOGA_CERTIFICATE";
          this.expertise = this.yogaCertificates;
          this.trainerExpertise = [];
          this.user.yogaCertificates.forEach(trainerExpertise => {
            this.yogaCertificates.forEach(expertise => {
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
        present.add(presentYogaExpertise[i].yogaExpertiseId);
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

      toAdd.userId = this.user.id;
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
    }
  }
};
</script>
