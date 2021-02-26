<template>
  <div class="region-item">
    <input type="checkbox" v-model="checked" class="btn-check btn-region" :id="region.code" autocomplete="off" @change="onChange(region.code)">
    <label class="btn btn-outline-secondary btn-region btn-sm" :for="region.code">
      <img :src="regionUrl(region.code)" width="24" height="24" :alt="region.description"/>
      <span class="region-name">{{ region.description }}</span>
    </label>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'RegionFilterInput',

  props: {
    region: {
      type: Object,
      required: true,
    }
  },

  data() {
    return {
      checked: false,
    }
  },

  methods: {
    ...mapActions({
      updateSelectedRegions: 'filter/updateSelectedRegions',
      fetchVideos: 'video/fetchVideos',
    }),

    regionUrl(regionCode) {
      return `https://cdn-lor.mobalytics.gg/production/images/svg/region/${regionCode}.svg`;
    },

    onChange(regionCode) {
      this.updateSelectedRegions({isAdd: this.checked, regionCode});
      this.fetchVideos();
    },

  },
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