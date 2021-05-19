<template>
  <div class="region-item">
    <input type="checkbox" v-model="selectedRegion" class="btn-check btn-region" :id="region.code" autocomplete="off" @change="onChange">
    <label class="btn btn-outline-secondary btn-region btn-sm" :for="region.code">
      <img :src="regionUrl()" width="24" height="24" :alt="region.description"/>
      <span class="region-name">{{ region.description }}</span>
    </label>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'RegionFilterInput',

  props: {
    region: {
      type: Object,
      required: true,
    }
  },

  methods: {
    ...mapActions({
      updateSelectedRegions: 'filter/updateSelectedRegions',
      fetchVideos: 'video/fetchVideos',
    }),

    regionUrl() {
      return `https://cdn-lor.mobalytics.gg/production/images/svg/region/${this.region.code}.svg`;
    },

    onChange() {
      this.fetchVideos();
    },
  },

  computed: {
    ...mapGetters({
      selectedFilter: 'filter/selectedFilter',
    }),

    selectedRegion: {
      get() {
        return this.selectedFilter.regions.includes(this.region.code);
      },
      set(newValue) {
        this.updateSelectedRegions({ isAdd: newValue, regionCode: this.region.code });
      }
    }
  }
}
</script>

<style scoped>
  .region-name {
    font-size: 12px;
    line-height: 20px;
    font-weight: 400;
  }

  .btn-region {
    width: 100%;
  }

  .region-item {
    display: flex;
    -webkit-box-align: center;
    align-items: center;
    cursor: pointer;
  }
</style>