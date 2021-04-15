<template>
  <div>
    <span class="text-filter text-white" @click="openNav()">&#9776; filter</span>
    <div id="mySidenav" class="sidenav">
      <a href="javascript:void(0)" class="close-btn" @click="closeNav()">&times;</a>
      <div class="container">
        <region-group-filter/>
        <champion-group-filter/>
        <channel-group-filter/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import RegionGroupFilter from './region/RegionGroupFilter';
import ChampionGroupFilter from './champion/ChampionGroupFilter';
import ChannelGroupFilter from './channel/ChannelGroupFilter';

export default {
  name: 'FilterPanel',
  components: {
    RegionGroupFilter,
    ChampionGroupFilter,
    ChannelGroupFilter,
  },

  created() {
    this.fetchFilters();
  },

  methods: {
    ...mapActions({
      fetchFilters: 'filter/fetchFilters',
    }),

    openNav() {
      document.getElementById("mySidenav").style.width = "250px";
    },

    closeNav() {
      document.getElementById("mySidenav").style.width = "0";
    }
  },
}
</script>

<style scoped>
.text-filter {
  font-size:30px;
  cursor:pointer
}
.sidenav {
  height: 100%;
  width: 0;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: #111;
  overflow-x: hidden;
  transition: 0.5s;
  padding-top: 60px;
}

.sidenav a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 25px;
  color: #818181;
  display: block;
  transition: 0.3s;
}

.sidenav a:hover {
  color: #f1f1f1;
}

.sidenav .close-btn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}

@media screen and (max-height: 450px) {
  .sidenav {padding-top: 15px;}
  .sidenav a {font-size: 18px;}
}
</style>