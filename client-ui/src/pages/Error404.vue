<template>
  <q-page>
    <div class="fixed-center text-center">
      <p>
        <img src="~assets/sad.svg" style="width:30vw;max-width:150px;" />
      </p>
      <p class="text-faded">Sorry, nothing here...<strong>(404)</strong></p>
      <q-btn color="secondary" style="width:200px;" to="/" label="Go back" />
    </div>
  </q-page>
</template>

<script>
export default {
  name: "Error404",
  methods: {
    myTweak(offset) {
      // "offset" is a Number (pixels) that refers to the total
      // height of header + footer that occupies on screen,
      // based on the QLayout "view" prop configuration
      // this is actually what the default style-fn does in Quasar
      return { minHeight: offset ? `calc(83.8vh - ${offset}px)` : "100vh" };
    }
  }
};
</script>
