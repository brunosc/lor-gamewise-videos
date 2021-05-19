<template>
  <v-select multiple
            v-model="selectedChampions"
            :options="champions"
            label="name"
            class="style-chooser"
            placeholder="Filter by champion"
            @input="onSelectChange">
    <template #selected-option="{ name, urlImgName }">
      <champion-icon :url-img-name="urlImgName"/>
      <span> {{ name }} </span>
    </template>

    <template v-slot:option="option">
      <champion-icon :url-img-name="option.urlImgName"/>
      <span class="text-white">{{ option.name }}</span>
    </template>
  </v-select>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import vSelect from 'vue-select';
import ChampionIcon from '../../shared/ChampionIcon';

export default {
  name: 'ChampionsFilterInput',

  components: {
    vSelect,
    ChampionIcon,
  },

  methods: {
    ...mapActions({
      updateSelectedChampions: 'filter/updateSelectedChampions',
      fetchVideos: 'video/fetchVideos',
    }),

    onSelectChange() {
      this.fetchVideos();
    }
  },

  computed: {
    ...mapGetters({
      filter: 'filter/filter',
      selectedFilter: 'filter/selectedFilter',
    }),

    champions() {
      return this.filter.champions ? this.filter.champions : [];
    },

    selectedChampions: {
      get() {
        return this.selectedFilter.champions;
      },

      set(newValue) {
        this.updateSelectedChampions(newValue);
      }
    }
  },
}
</script>

<style src="../../../../node_modules/vue-select/dist/vue-select.css"></style>

<style>
.style-chooser .vs__search::placeholder,
.style-chooser .vs__dropdown-toggle,
.style-chooser .vs__dropdown-menu {
  background: #212529;
  color: white;
  border: none;
  text-transform: lowercase;
  font-variant: small-caps;
  font-weight: bold;
}

.style-chooser .vs__selected {
  background-color: #6c757d;
  color: white;
  font-weight: bold;
}
</style>