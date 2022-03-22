<template>
  <div>
    <q-card square>
      <q-img src="~assets/serenity-header-02.jpg" />

      <q-card-section>
        <q-btn
          :size="$q.screen.lt.md ? 'sm' : 'md'"
          color="positive"
          icon-right="send"
          :label="item.actionLabel"
          class="absolute"
          style="top: 0; left: 12px; transform:
        translateY(-50%);"
        />

        <div class="row no-wrap items-center q-mt-md">
          <div class="col text-h6 ellipsis">
            {{ item.title }}
          </div>
        </div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <div class="text-caption text-grey">
          {{ item.desc }}
        </div>
      </q-card-section>
    </q-card>
  </div>
</template>

<script>
export default {
  props: {
    item: {
      type: Object,
      required: true
    }
  }
};
</script>

<style></style>
