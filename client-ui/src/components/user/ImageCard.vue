<template>
  <div class="q-pa-sm row items-start" clickable>
    <q-card class="my-card" style="width: 100%" square>
      <q-img :src="image.url" :ratio="16 / 9" style="width: 100%">
        <div class="absolute-bottom" v-if="$q.screen.gt.sm">
          {{ image.title }}
        </div>
        <q-btn
          v-if="editable"
          class="absolute all-pointer-events"
          size="sm"
          name="info"
          color="white"
          style="top: 8px; right: 8px"
          dense
          flat
          round
          icon="fas fa-ellipsis-v"
        >
          <q-menu transition-show="jump-down" transition-hide="jump-up">
            <q-list style="min-width: 100px">
              <q-item
                clickable
                v-for="(item, i) in items"
                :key="i"
                @click="onMenuClick(item)"
              >
                <q-item-section>{{ item.title }}</q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-btn>
      </q-img>
      <q-card-actions :class="{ 'q-pa-sm': $q.screen.lt.md }">
        <q-btn
          flat
          label="Detail"
          :to="`/images/${this.image.id}`"
          :size="$q.screen.lt.md ? 'sm' : ''"
        />
      </q-card-actions>
    </q-card>
  </div>
</template>

<script>
export default {
  name: "ImageCard",
  props: {
    image: {
      type: Object,
      required: true
    },
    editable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      items: [{ title: "Edit" }, { title: "Delete" }]
    };
  },
  methods: {
    onMenuClick(item) {
      this.$emit("on-menu-click", { item: item.title, data: this.image });
    },
    onClickCard() {
      //this.$router.push(`/images/${this.image.id}`);
    }
  }
};
</script>

<style scoped></style>
