<template>
  <div class="form-check form-switch mt-5">
    <input id="saveFilterSwitch" class="form-check-input" type="checkbox" v-model="saveFilterModel" @change="onSwitchChange">
    <label class="form-check-label text-white" for="saveFilterSwitch">
      Save filter
    </label>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'SaveFilterSwitch',

  created() {
    this.setInitialFilter();
  },

  methods: {
    ...mapActions({
      setSaveFilter: 'filter/setSaveFilter',
      setInitialFilter: 'filter/setInitialFilter',
    }),

    onSwitchChange() {
      console.log(this.saveFilter);
    }
  },

  computed: {
    ...mapGetters({
      saveFilter: 'filter/saveFilter'
    }),

    saveFilterModel: {
      get() {
        return this.saveFilter;
      },

      set(newValue) {
        this.setSaveFilter(newValue);
      }
    }
  }
}
</script>

<style scoped>

</style>