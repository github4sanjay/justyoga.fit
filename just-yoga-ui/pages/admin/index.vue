<template>
  <v-container>
    <v-card>
      <v-card-title>
        Admin Dashboard
      </v-card-title>
      <v-card-text class="py-0">
        <v-chip-group column>
          <v-chip small :x-small="$vuetify.breakpoint.xsOnly" label @click="collectionDialogue = true">
            Create Collection
          </v-chip>
        </v-chip-group>
      </v-card-text>
    </v-card>
    <v-dialog width="800" v-model="collectionDialogue">
     <collection-operation @close="collectionDialogue =false"/>
    </v-dialog>
  </v-container>
</template>

<script>
import CollectionOperation from "@/components/collection/CollectionOperation";
export default {
  name: "AdminIndex",
  components: {CollectionOperation},
  data(){
    return {
      collectionDialogue: false
    }
  }
};
</script>

<style scoped></style>
