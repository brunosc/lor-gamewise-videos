<template>
  <div id="app" class="container-fluid mt-3">
    <filter-panel/>
    <div class="row">
      <videos-container class="col mt-3 mt-lg-0 mt-sm-0 mt-md-0"/>
    </div>
    <video-pagination v-if="hasVideos"/>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
  import VideosContainer from '@/components/video/VideosContainer';
  import VideoPagination from '@/components/video/VideoPagination';
  import FilterPanel from './components/filter/FilterPanel';

  export default {
    name: 'App',

    components: {
      VideosContainer,
      VideoPagination,
      FilterPanel,
    },

    computed: {
      ...mapGetters({
        hasVideos: 'video/hasVideos'
      }),
    }
  }
</script>

<style>
  #body {
    background-color: #2f2f2f;
  }
</style>
