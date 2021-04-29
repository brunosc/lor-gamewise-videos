<template>
  <div class="row">
    <div class="col">
      <h6 class="card-subtitle mb-2 text-muted">{{ channelName }}</h6>
    </div>
    <div class="col">
      <h6 class="card-subtitle mb-2 text-muted"><small>{{ publishedAtFormatted }}</small></h6>
    </div>
  </div>
</template>

<script>
import moment from 'moment';

export default {
  name: 'VideoCardSubtitle',
  props: [ 'channelName', 'publishedAt' ],
  computed: {
    publishedAtFormatted() {
      return moment(this.publishedAt).format('MMM Do YYYY');
    }
  }
}
</script>