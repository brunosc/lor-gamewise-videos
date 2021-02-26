<template>
  <div class="row">
    <div class="col">
      <video-regions :regions="regions"/>
    </div>
    <div class="col">
      <video-champions :champions="champions"/>
    </div>
  </div>
</template>

<script>
import VideoRegions from '@/components/video/VideoRegions';
import VideoChampions from '@/components/video/VideoChampions';

export default {
  name: 'RegionsChampionsContainer',
  props: {
    regions: {
      type: Array,
      required: true,
    },
    champions: {
      type: Array,
      required: true,
    }
  },
  components: {
    VideoRegions,
    VideoChampions,
  }
}
</script>

<style scoped>

</style>