import Vue from 'vue'
import Vuex from 'vuex'
import video from './modules/video'
import filter from './modules/filter'

Vue.use(Vuex)

export default new Vuex.Store({

  modules: {
    video,
    filter,
  },

})