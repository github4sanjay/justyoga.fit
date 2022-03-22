<template>
  <div>
    <q-toolbar class="bg-primary text-white">
      <q-toolbar-title>Update Profile Details</q-toolbar-title>
      <q-btn flat label="Save" :loading="updatingUser" @click="submit()" />
      <q-btn flat round icon="close" v-close-popup @click="closeBtnClick()" />
    </q-toolbar>
    <div
      :class="{
        'q-pa-md': true
      }"
    >
      <q-form ref="collectionDetailForm">
        <div class="row">
          <div class="col-3 col-sm-4">
            <div>Email</div>
          </div>
          <div class="col-9 col-sm-8">
            <q-input v-model="email" label="Email" disable />
          </div>
        </div>
        <div class="row">
          <div class="col-3 col-sm-4">
            <div>Name</div>
          </div>
          <div class="col-9 col-sm-8">
            <q-input
              v-model="name"
              label="Name"
              maxlength="30"
              counter
              :rules="[
                val => !!val || '* Required',
                val => val.length < 30 || 'Max 30 characters'
              ]"
              lazy-rules
            />
          </div>
        </div>
        <div class="row">
          <div class="col-3 col-sm-4">
            <div>Mobile</div>
          </div>
          <div class="col-9 col-sm-8">
            <q-input
              v-model="mobile"
              label="Mobile Number"
              maxlength="10"
              counter
              :rules="[val => !val || val.length === 10 || 'Max 10 characters']"
              lazy-rules
            />
          </div>
        </div>
        <div class="row">
          <div class="col-3 col-sm-4">
            <div>Description</div>
          </div>
          <div class="col-9 col-sm-8">
            <q-input
              outlined
              filled
              type="textarea"
              v-model="description"
              label="Description"
              maxlength="150"
              counter
              :rules="[
                val => !!val || '* Required',
                val =>
                  (val.length > 10 && val.length < 150) ||
                  'Min 10 and Max 150 characters'
              ]"
              lazy-rules
            />
          </div>
        </div>
      </q-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserForm",
  components: {},
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  data: function() {
    return {
      name: "",
      email: "",
      mobile: "",
      description: "",
      updatingUser: false
    };
  },
  methods: {
    submit() {
      this.$refs.collectionDetailForm.validate().then(success => {
        if (success) {
          this.updateUser({
            id: this.user.id,
            name: this.name,
            description: this.description,
            phoneNumber: this.mobile,
            firebaseUid: this.user.firebaseUid,
            email: this.user.email
          });
        } else {
          return;
        }
      });
    },
    updateUser(val) {
      this.updatingUser = true;
      this.$store
        .dispatch("users/updateUser", val)
        .then(() => {
          this.updatingUser = false;
          this.closeBtnClick();
        })
        .catch(() => (this.updatingUser = false));
    },
    closeBtnClick() {
      this.$emit("close", {});
    }
  },
  created() {
    this.name = this.user.name;
    this.email = this.user.email;
    this.mobile = this.user.phoneNumber;
    this.description = this.user.description;
  },
  mounted() {}
};
</script>

<style scoped></style>
