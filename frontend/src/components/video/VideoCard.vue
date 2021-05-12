<template>
  <div class="col mb-4">
    <div class="card text-white bg-dark">
      <a :href="video.url" target="_blank">
        <img :src="video.thumbnail.url" class="card-img-top" :alt="video.title">
      </a>
      <div class="card-body">
        <h6 class="card-title">{{ video.title }}</h6>
        <video-card-subtitle :channel-name="video.channel.name" :published-at="video.publishedAt"/>
        <regions-champions-container :regions="video.regions" :champions="video.champions"/>
        <video-card-buttons :deck-code="video.deckCode"/>
      </div>
    </div>
  </div>
</template>

<script>
  import VideoCardButtons from '@/components/video/VideoCardButtons';
  import VideoCardSubtitle from '@/components/video/VideoCardSubtitle';
  import RegionsChampionsContainer from '@/components/video/RegionsChampionsContainer';

  export default {
    name: 'VideoCard',

    components: {
      VideoCardButtons,
      VideoCardSubtitle,
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