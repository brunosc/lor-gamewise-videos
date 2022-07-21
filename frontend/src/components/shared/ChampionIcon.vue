<template>
  <img class="rounded-circle mx-1"
       :src="urlImg"
       width="24" height="24"
       :title="name"
       :alt="name">
</template>

<script>
export default {
  name: 'ChampionIcon',
  props: {
    urlImg: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      required: true,
    },
  },
}
</script>

<style scoped>

</style>