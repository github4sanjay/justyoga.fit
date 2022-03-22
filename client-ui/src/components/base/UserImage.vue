<template>
  <img :src="user.profilePic" v-if="user.profilePic" />
  <div v-else-if="user.name">{{ user.name.charAt(0) }}</div>
  <div v-else>JY</div>
</template>

<script>
export default {
  name: "UserImage",
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  data() {
    return {};
  }
};
</script>
