<template>
  <div>
    <q-card
      :class="{ 'q-pa-none': true, 'q-mx-md': $q.screen.gt.xs }"
      square
      flat
      bordered
      v-if="!hidePagination"
    >
      <q-card-actions class="q-pa-none">
        <q-card-section>
          <div>
            Videos
            <q-badge
              color="accent"
              text-color="white"
              :label="model.data.totalElements"
            />
          </div>
        </q-card-section>
        <q-space />
        <q-btn
          :disable="!model.data.hasPrevious"
          flat
          round
          color="primary"
          icon="fas fa-arrow-circle-left"
          :to="`${+model.page - 1}`"
        />
        <q-separator vertical />
        <q-btn
          :disable="!model.data.hasNext"
          flat
          round
          color="primary"
          icon="fas fa-arrow-circle-right"
          :to="`${+model.page + 1}`"
        />
      </q-card-actions>
    </q-card>
    <div class="row">
      <div
        :class="{
          'col-12': true,
          'col-sm-6': true,
          'col-md-4': true,
          'col-lg-3': true,
          'q-pa-md': $q.screen.gt.xs,
          'q-py-sm': $q.screen.xs
        }"
        v-for="(data, i) in model.data.content"
        :key="i"
      >
        <directory-video-card :video-info="data" />
      </div>
    </div>
  </div>
</template>

<script>
import DirectoryVideoCard from "components/directory/DirectoryVideoCard";

export default {
  name: "DirectoryVideoSection",
  components: { DirectoryVideoCard },
  props: {
    model: {
      type: Object,
      required: true
    },
    hidePagination: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {};
  }
};
</script>
