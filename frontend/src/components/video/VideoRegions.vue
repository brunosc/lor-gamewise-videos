<template>
  <div class="mb-2">
    <img v-for="region in regions" :key="region.code" :src="regionImgUrl(region.code)" width="24" height="24" :alt="region.description">
  </div>
</template>

<script>
export default {
  name: 'VideoRegions',
  props: {
    regions: {
      type: Array,
      required: true,
    }
  },

  methods: {
    regionImgUrl(regionCode) {
      return `https://cdn-lor.mobalytics.gg/production/images/svg/region/${regionCode}.svg`
    }
  }
}
</script>

<style scoped>

</style>