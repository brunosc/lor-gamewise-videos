import VideoService from '@/service/VideoService';

export default {
  namespaced: true,

  state: {
    filter: {},
    selectedFilter: {
      regions: [],
      champions: [],
    }
  },

  mutations: {
    setFilter(state, filter) {
      state.filter = filter;
    },

    pushSelectedRegion(state, regionCode) {
      state.selectedFilter.regions.push(regionCode);
    },

    removeSelectedRegion(state, regionCode) {
      const index = state.selectedFilter.regions.indexOf(regionCode);
      if (index >= 0) {
        state.selectedFilter.regions.splice(index, 1);
      }
    },

    setSelectedChampions(state, champions) {
      state.selectedFilter.champions = champions;
    },
  },

  actions: {
    fetchFilters({ commit }) {
      VideoService.getFilters()
        .then(res => commit('setFilter', res.data))
    },

    updateSelectedRegions({ commit }, values) {
      if (values.isAdd) {
        commit('pushSelectedRegion', values.regionCode);
      } else {
        commit('removeSelectedRegion', values.regionCode);
      }
    },

    updateSelectedChampions({ commit }, champions) {
      commit('setSelectedChampions', champions);
    }
  },

  getters: {
    filter: state => state.filter,
  }
}