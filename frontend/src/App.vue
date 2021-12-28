<template>
  <div id="app" class="mt-3">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-10 col-sm-6">
          <filter-panel/>
        </div>
        <div class="col-md-2 col-sm-6" @click="closeNav">
          <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
            Channel Statistics
          </button>
          <channel-statistics-modal/>
        </div>
      </div>
      <div class="row" @click="closeNav">
        <videos-container class="col mt-3 mt-lg-0 mt-sm-0 mt-md-0"/>
      </div>
      <video-pagination v-if="hasVideos"/>
    </div>
    <lor-footer/>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { closeNavFilter } from '@/mixins/closeNavFilter';
import VideosContainer from '@/components/video/VideosContainer';
import VideoPagination from '@/components/video/VideoPagination';
import FilterPanel from './components/filter/FilterPanel';
import LorFooter from '@/components/shared/LorFooter';
import ChannelStatisticsModal from '@/components/channel/ChannelStatisticsModal';

  export default {
    name: 'App',
    mixins: [ closeNavFilter ],

    data() {
      return {
        appSettings: {}
      }
    },

    components: {
      VideosContainer,
      VideoPagination,
      FilterPanel,
      LorFooter,
      ChannelStatisticsModal,
    },

    computed: {
      ...mapGetters({
        hasVideos: 'video/hasVideos'
      })
    }
  }
</script>

<style>
  #body {
    background-color: #2f2f2f;
  }
</style>
