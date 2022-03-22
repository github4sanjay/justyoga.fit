<template>
    <v-row>
      <v-col cols="12">
        <v-card flat>
          <v-card-text
            :class="{
              'justify-center': $vuetify.breakpoint.smAndDown,
              headline: $vuetify.breakpoint.mdAndUp,
              title: $vuetify.breakpoint.sm,
              'subtitle-2': $vuetify.breakpoint.xsOnly,
            }"
          >
            {{ headline }}
          </v-card-text>
          <v-combobox
            class="mx-3"
            label="Type to search options"
            multiple
            :small-chips="$vuetify.breakpoint.smAndDown"
            :chips="$vuetify.breakpoint.mdAndUp"
            outlined
            :dense="$vuetify.breakpoint.smAndDown"
            v-model="selections"
            :items="options"
            :search-input.sync="search"
            item-text="name"
          >
            <template v-slot:no-data>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>
                    No results matching "<strong>{{ search }}</strong
                  >". Press <kbd>enter</kbd> to create a new one
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-combobox>
        </v-card>
      </v-col>
    </v-row>
</template>

<script>
export default {
  name: "BaseCombobox",
  props: {
    options: {
      type: Array,
      required: false,
      default: () => [],
    },
    headline: {
      type: String,
      required: false,
      default: "Choose options below",
    },
  },
  data() {
    return {
      search: null,
      selections: [],
    };
  },
  watch: {
    selections(val) {
      this.$emit("change-selections", val);
    },
  },
};
</script>

<style scoped></style>
