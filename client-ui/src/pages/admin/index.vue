<template>
  <q-page padding>
    <div>
      <q-card>
        <q-card-section>
          <div>Admin Section</div>
        </q-card-section>
        <q-card-section>
          <div>
            <span class="q-pa-sm">
              <q-btn size="md" @click="collectionDialogue = true">
                Create Collection
              </q-btn>
            </span>
          </div>
        </q-card-section>
      </q-card>
      <q-dialog v-model="collectionDialogue" maximized>
        <q-card
          :style="{
            'min-width': $q.screen.gt.sm ? '850px' : ''
          }"
        >
          <collection-detail-form @close="collectionDialogue = false" />
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import CollectionDetailForm from "components/collection/CollectionDetailForm";
export default {
  name: "AdminIndex",
  components: { CollectionDetailForm },
  data() {
    return {
      collectionDialogue: false
    };
  }
};
</script>

<style scoped></style>
