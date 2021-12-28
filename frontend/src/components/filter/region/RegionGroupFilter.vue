<template>
  <div class="group-regions">
    <region-filter-input v-for="region in filter.regions" :key="region.code" :region="region"/>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import RegionFilterInput from './RegionFilterInput';

export default {
  name: 'RegionGroupFilter',

  components: {
    RegionFilterInput,
  },

  computed: {
    ...mapGetters({
      filter: 'filter/filter'
    }),
  },
}
</script>

<style scoped>
  .group-regions {
    position: relative;
    display: grid;
    grid-template-rows: repeat(5, auto);
    grid-auto-flow: column;
    gap: 24px 12px;
  }
</style>