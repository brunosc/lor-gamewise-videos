import VideoService from '@/service/VideoService';

const KEY_FILTER = 'filter';

const hasSavedFilter = () => {
  const localStorageFilter = localStorage.getItem(KEY_FILTER);
  return localStorageFilter !== undefined && localStorageFilter !== null;
}

const updateLocalStorageFilter = (field, value) => {
  let filter;

  if (hasSavedFilter()) {
    filter = JSON.parse(localStorage.getItem(KEY_FILTER));
    filter[field] = value;
  } else {
    filter = { [field]: value };
  }

  localStorage.setItem(KEY_FILTER, JSON.stringify(filter));
}

export default {
  namespaced: true,

  state: {
    filter: {},
    selectedFilter: {
      regions: [],
      channels: [],
      champions: [],
    },
    saveFilter: false,
  },

  mutations: {
    setFilter(state, filter) {
      state.filter = filter;
    },

    setSelectedFilter(state, selectedFilter) {
      state.selectedFilter = selectedFilter;
    },

    setSaveFilter(state, saveFilter) {
      state.saveFilter = saveFilter;
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

    setSelectedChannels(state, channels) {
      state.selectedFilter.channels = channels;
    },
  },

  actions: {
    fetchFilters({ commit }) {
      VideoService.getFilters()
        .then(res => commit('setFilter', res.data))
    },

    updateSelectedRegions({ commit, getters }, values) {
      if (values.isAdd) {
        commit('pushSelectedRegion', values.regionCode);
      } else {
        commit('removeSelectedRegion', values.regionCode);
      }

      if (getters.saveFilter) {
        updateLocalStorageFilter('regions', getters.selectedFilter.regions);
      }
    },

    updateSelectedChampions({ commit, getters }, champions) {
      commit('setSelectedChampions', champions);
      if (getters.saveFilter) {
        updateLocalStorageFilter('champions', champions);
      }
    },

    updateSelectedChannels({ commit, getters }, channels) {
      commit('setSelectedChannels', channels);
      if (getters.saveFilter) {
        updateLocalStorageFilter('channels', channels);
      }
    },

    setInitialFilter({ commit }) {
      const savedFilter = hasSavedFilter();
      commit('setSaveFilter', savedFilter);

      if (savedFilter) {
        commit('setSelectedFilter', JSON.parse(localStorage.getItem(KEY_FILTER)));
      }
    },

    setSaveFilter({ commit, getters }, saveFilter) {
      commit('setSaveFilter', saveFilter);
      if (saveFilter) {
        localStorage.setItem(KEY_FILTER, JSON.stringify(getters.selectedFilter));
      } else {
        localStorage.removeItem(KEY_FILTER);
      }
    }
  },

  getters: {
    filter: state => state.filter,
    saveFilter: state => state.saveFilter,
    selectedFilter: state => state.selectedFilter,
  }
}