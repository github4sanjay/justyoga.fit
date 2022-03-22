<template>
  <v-container>
    <v-row align="center">
      <v-col cols="2" sm="1">
        <v-avatar size="50" color="primary">
          <v-img
            v-if="user.profilePic"
            :src="user.profilePic"
            :alt="user.name"
          />
          <span v-else-if="user.name" class="white--text headline">
            {{ user.name.charAt(0) }}
          </span>
          <span v-else class="white--text headline">JY</span>
        </v-avatar>
      </v-col>
      <v-col cols="6" sm="9" md="9">
        <div>
          <h4 class="mb-0">{{ user.name }}</h4>
          <!-- <div style="font-size: 12px">{{place.formattedAddress}}</div>-->
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "UserHeader",
  props: {
    user: {
      type: Object,
      required: true,
    },
  },
  data: () => ({
    school: null,
    loadingSchool: false,
  }),
  methods: {},
  created() {},
};
</script>

<style scoped></style>
