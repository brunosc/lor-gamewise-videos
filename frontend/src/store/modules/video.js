import VideoService from '@/service/VideoService';

export default {
  namespaced: true,

  state: {
    videos: [],
    page: {},
  },

  mutations: {
    setVideos(state, videos) {
      state.videos = videos;
    },
    setPage(state, page) {
      const pageWithoutContent = Object.assign({}, page);
      delete pageWithoutContent.content;

      state.page = pageWithoutContent;
    }
  },

  actions: {
    fetchVideos({ commit, rootState }, pageNumber = 0) {
      VideoService.getVideos(rootState.filter.selectedFilter, pageNumber)
        .then(res => {
          commit('setPage', res.data);
          commit('setVideos', res.data.content);
        });
    }
  },

  getters: {
    videos: state => state.videos,
    hasVideos: state => state.videos.length,
    page: state => state.page,
  }
}