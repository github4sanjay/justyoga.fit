<template>
  <v-card hover class="mx-auto" max-width="600" @click="onClickCard" flat router :to="`/images/${this.image.id}`">
    <v-img
      :aspect-ratio="16 / 9"
      :src="image.url"
      gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
    >
      <template v-slot:placeholder>
        <image-loader/>
      </template>
      <v-container fill-height class="align-end pa-0 white--text">
        <v-card-title class="subtitle-2"> {{ image.title }}</v-card-title>
       <!-- <div class="subtitle-2">
          {{ image.title }}
        </div>-->
        <v-spacer />
        <v-menu bottom left v-if="editable">
          <template v-slot:activator="{ on }">
            <v-btn icon v-on="on" dark>
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(item, i) in items"
              :key="i"
              @click="onMenuClick(item)"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-container>
    </v-img>
    <!--<v-card-text class="px-2 py-1">
      <v-card-actions>
        <div class="subtitle-2">
          {{ image.title }}
        </div>
        <v-spacer />
        <v-menu bottom left v-if="editable">
          <template v-slot:activator="{ on }">
            <v-btn icon v-on="on">
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(item, i) in items"
              :key="i"
              @click="onMenuClick(item)"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-card-actions>
    </v-card-text>-->
  </v-card>
</template>

<script>
import ImageLoader from "./ImageLoader";
export default {
  name: "ImageCard",
  components: {ImageLoader},
  props: {
    image: {
      type: Object,
      required: true,
    },
    editable: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      items: [{ title: "Edit" }, { title: "Delete" }],
    };
  },
  methods: {
    onMenuClick(item){
      this.$emit("on-menu-click", {item :item.title, data: this.image});
    },
    onClickCard() {
      //this.$router.push(`/images/${this.image.id}`);
    },
  }
};
</script>

<style scoped></style>
