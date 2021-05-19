<template>
  <div id="app" class="mt-3">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-9 col-sm-6">
          <filter-panel/>
        </div>
        <div class="col-md-3 col-sm-6">
          <p class="text-white-50"><small>Last update: {{ updatedAt }}</small></p>
        </div>
      </div>
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
import VideoService from '@/service/VideoService';
import moment from 'moment';

  export default {
    name: 'App',

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
    },

    created() {
      VideoService.getAppSettings().then(result => this.appSettings = result.data);
    },

    computed: {
      ...mapGetters({
        hasVideos: 'video/hasVideos'
      }),

      updatedAt() {
        return `${moment(this.appSettings.updatedAt).format('MMMM Do YYYY, h:mm:ss')} (UTC)`;
      }
    }
  }
</script>

<style>
  #body {
    background-color: #2f2f2f;
  }
</style>
