<template>
  <img class="rounded-circle mx-1"
       :src="championImgUrl()"
       width="24" height="24"
       :alt="urlImgName">
</template>

<script>
export default {
  name: 'ChampionIcon',
  props: {
    urlImgName: {
      type: String,
      required: true,
    }
  },

  methods: {
    championImgUrl() {
      return `https://cdn-lor.mobalytics.gg/production/images/champions/${this.urlImgName}.webp`;
    }
  }
}
</script>

<style scoped>

</style>