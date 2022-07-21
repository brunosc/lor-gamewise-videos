<template>
  <div>
    <champion-icon v-for="champion in champions"
                   :key="champion.code" :url-img="champion.urlImg" :name="champion.name"/>
  </div>
</template>

<script>
import ChampionIcon from '../shared/ChampionIcon';
export default {
  name: 'VideoChampions',

  props: {
    champions: {
      type: Array,
      required: true,
    }
  },

  components: {
    ChampionIcon,
  },
}
</script>

<style scoped>

</style>