<template>
  <div class="row row-cols-1 row-cols-md-3">
    <video-card v-for="video in videos" :key="video.url" :video="video"/>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import VideoCard from '@/components/video/VideoCard';

export default {
  name: 'VideosContainer',

  components: {
    VideoCard,
  },

  created() {
    this.fetchVideos();
  },

  methods: {
    ...mapActions({
      fetchVideos: 'video/fetchVideos',
    }),
  },

  computed: {
    ...mapGetters({
      videos: 'video/videos'
    })
  },
}
</script>

<style scoped>

</style>