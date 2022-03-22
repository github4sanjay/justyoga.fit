<template>
  <q-page padding>
    <div class="row justify-center" style="width:100%;">
      <div class="col-12 col-md-10 col-lg-8">
        <q-card flat square>
          <q-card-section v-html="text" />
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script>
import Cms from "src/constants/Cms";

export default {
  name: "TechHome",
  data: function() {
    return {
      text: Cms.TECH_STACK
    };
  }
};
</script>
