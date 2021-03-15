<template>
  <div id="app" class="mt-3">
    <div class="container-fluid">
      <filter-panel/>
      <div class="row">
        <videos-container class="col mt-3 mt-lg-0 mt-sm-0 mt-md-0"/>
      </div>
      <video-pagination v-if="hasVideos"/>
    </div>
    <lor-footer/>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import VideosContainer from '@/components/video/VideosContainer';
import VideoPagination from '@/components/video/VideoPagination';
import FilterPanel from './components/filter/FilterPanel';
import LorFooter from '@/components/shared/LorFooter';

  export default {
    name: 'App',

    components: {
      VideosContainer,
      VideoPagination,
      FilterPanel,
      LorFooter,
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
