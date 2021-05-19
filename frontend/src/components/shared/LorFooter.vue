<template>
  <footer class="bg-dark text-center text-lg-start">
    <div class="text-center p-3 text-white">
      Do you have any suggestion/question?
      <a href="https://www.reddit.com/r/RuneterraVideos" target="_blank" class="text-white">
        <font-awesome-icon class="mx-1" size="lg" :icon="['fab', 'reddit']" />
      </a>
    </div>
    <div class="text-center p-1 text-white">
      We <span class="mx-1">❤</span> open-source
      <a href="https://github.com/brunosc/lor-gamewise-videos" target="_blank" class="text-white">
        <font-awesome-icon class="mx-1" size="lg" :icon="['fab', 'github']" />
      </a>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'LorFooter'
}
</script>

<style scoped>

</style>