<template>
  <v-select multiple
            v-model="selectedValue"
            :options="channels"
            label="name"
            class="style-chooser"
            placeholder="Filter by channel"
            @input="onSelectChange">
    <template #selected-option="{ name }">
      <span> {{ name }} </span>
    </template>

    <template v-slot:option="option">
      <span class="text-white">{{ option.name }}</span>
    </template>
  </v-select>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import vSelect from 'vue-select';

export default {
  name: 'ChannelFilterInput',

  components: {
    vSelect
  },

  data() {
    return {
      selectedValue: [],
    }
  },

  methods: {
    ...mapActions({
      updateSelectedChannels: 'filter/updateSelectedChannels',
      fetchVideos: 'video/fetchVideos',
    }),

    onSelectChange() {
      this.updateSelectedChannels(this.selectedValue);
      this.fetchVideos();
    }
  },

  computed: {
    ...mapGetters({
      filter: 'filter/filter'
    }),

    channels() {
      return this.filter.channels ? this.filter.channels : [];
    }
  }
}
</script>

<style scoped>

</style>