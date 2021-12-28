<template>
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-scrollable">
      <div class="modal-content bg-dark">
        <div class="modal-header">
          <h5 class="modal-title text-white" id="staticBackdropLabel">Channel Statistics</h5>
          <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>

        <div class="modal-body">
          <div>
            <label for="channelSelectInput" class="form-label text-white">Select a channel</label>
            <select id="channelSelectInput" v-model="selectedChannel" class="form-select bg-dark text-white" aria-label="Channel select" @change="onSelectChannel">
              <option class="text-white" v-for="channel in channels" :key="channel.code" :value="channel.code">{{ channel.name }}</option>
            </select>
          </div>

          <div class="row" v-if="statistics">
            <p class="text-white mt-3"><small>Data since: {{ startedSyncAt }}</small></p>
            <div class="col">
              <channel-statistics-table title="Champions" :data="championsStatistics"/>
            </div>
            <div class="col">
              <channel-statistics-table title="Regions" :data="regionsStatistics"/>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment';
import { mapGetters } from 'vuex';
import ChannelService from '@/service/ChannelService';
import ChannelStatisticsTable from '@/components/channel/ChannelStatisticsTable';

export default {
  name: 'ChannelStatisticsModal',

  components: {
    ChannelStatisticsTable
  },

  data() {
    return {
      selectedChannel: null,
      statistics: null,
    }
  },

  methods: {
    async onSelectChannel() {
      const response = await ChannelService.getChannelStatistics(this.selectedChannel);
      this.statistics = response.data;
    }
  },

  computed: {
    ...mapGetters({
      filter: 'filter/filter',
    }),

    channels() {
      return this.filter.channels ? this.filter.channels : [];
    },

    championsStatistics() {
      return this.statistics ? this.statistics.champions : [];
    },

    regionsStatistics() {
      return this.statistics ? this.statistics.regions : [];
    },

    startedSyncAt() {
      return moment(this.statistics.startedAt).format('MMM Do YYYY');
    }
  }
}
</script>

<style scoped>

</style>