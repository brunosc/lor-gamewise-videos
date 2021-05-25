<template>
  <div>
    <h4 class="text-white">{{ title }}</h4>
    <table class="table text-white">
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Name</th>
        <th scope="col">Count</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(statistic, index) in data" :key="statistic.name">
        <th scope="row">{{ index + 1 }}</th>
        <td>{{ statistic.name }}</td>
        <td>{{ statistic.count }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'ChannelStatisticsTable',
  props: [ 'title', 'data' ]
}
</script>

<style scoped>

</style>