<template>
  <div class="row justify-center q-pa-sm">
    <div :class="look" v-for="(item, i) in cta" :key="i">
      <cta-card :item="item" />
    </div>
  </div>
</template>

<script>
import CtaCard from "components/base/CtaCard";

export default {
  name: "PageIndex",
  components: { CtaCard },
  props: {
    look: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      cta: [
        {
          title: "Beginners Yoga",
          desc:
            "Do you want to boost your immunity during this corona time? We have some good trainers who have made a course which can be directed online.",
          actionLabel: "Find Trainers"
        },
        {
          title: "Intermediate Yoga",
          desc:
            "Kept in sent gave feel will oh it we. Has pleasure procured me laughing shutters nay. Old insipidity motionless continuing law shy partiality.",
          actionLabel: "Join Now"
        },
        // {
        //   title: "Teacher training Course",
        //   desc:
        //     "If you are looking for some good yoga teacher training class to learn in-depth knowledge of yoga, we will help you in this journey.",
        //   actionLabel: "Join Now"
        // },
        {
          title: "Individual Classes",
          desc:
            "Are you looking for a personal trainer or customized classe that fits you? Please call us to better understand your requirement. ",
          actionLabel: "Call us"
        }
      ]
    };
  }
};
</script>

<style></style>
