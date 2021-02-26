<template>
  <div class="col mb-4">
    <div class="card text-white bg-dark">
      <a :href="video.url" target="_blank">
        <img :src="video.thumbnail.url" class="card-img-top" :alt="video.title">
      </a>
      <div class="card-body">
        <h5 class="card-title">{{ video.title }}</h5>
        <h6 class="card-subtitle mb-2 text-muted">{{ video.channel.name }}</h6>

        <regions-champions-container :regions="video.regions" :champions="video.champions"/>
        <video-card-buttons :deck-code="video.deckCode"/>
      </div>
    </div>

  </div>
</template>

<script>
  import VideoCardButtons from '@/components/video/VideoCardButtons';
  import RegionsChampionsContainer from '@/components/video/RegionsChampionsContainer';

  export default {
    name: 'VideoCard',

    components: {
      VideoCardButtons,
      RegionsChampionsContainer,
    },

    props: {
      video: {
        type: Object,
        required: true,
      }
    },


  }
</script>

<style scoped>

</style>