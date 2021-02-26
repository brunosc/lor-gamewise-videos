<template>
  <nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
      <li class="page-item" :class="{'disabled': page.first}">
        <a class="page-link bg-dark" href="#" tabindex="-1" aria-disabled="true" @click="setPreviousPage">Previous</a>
      </li>

      <li v-for="pageNumber in pageNumbers" :key="pageNumber" class="page-item" :class="{'active': page.number === pageNumber}">
        <a class="page-link bg-dark" href="#" @click="updateCurrentPageNumber(pageNumber)">{{ pageNumber + 1 }}</a>
      </li>

      <li class="page-item" v-bind:class="{'disabled': page.last}">
        <a class="page-link bg-dark" href="#" @click="setNextPage">Next</a>
      </li>
    </ul>
  </nav>
</template>

<script>
  import { mapActions, mapGetters } from 'vuex';

  export default {
    name: 'VideoPagination',

    data() {
      return {
        currentPageNumber: 0,
        totalPagesToShow: 3,
      }
    },

    methods: {
      ...mapActions({
        fetchVideos: 'video/fetchVideos',
      }),

      updateCurrentPageNumber(pageNumber) {
        this.currentPageNumber = pageNumber;
      },

      setPreviousPage() {
        this.currentPageNumber--;
      },

      setNextPage() {
        this.currentPageNumber++;
      },

      intToArray(value) {
        return Array.from({length: value}, (v, k) => k);
      }
    },

    computed: {
      ...mapGetters({
        page: 'video/page'
      }),

      pageNumbers() {
        if (this.page.totalPages > this.totalPagesToShow) {

          if (this.page.first) {
            return this.intToArray(this.totalPagesToShow);
          }

          if (this.page.last) {
            return [this.currentPageNumber - 2, this.currentPageNumber - 1, this.currentPageNumber];
          }

          return [this.currentPageNumber - 1, this.currentPageNumber, this.currentPageNumber + 1]

        }
        return this.intToArray(this.page.totalPages);
      }
    },

    watch: {
      currentPageNumber: function (val) {
        this.fetchVideos(val);
      }
    }
  }
</script>

<style scoped>
  .pagination > li > a {
    color: white;
  }

  .pagination > .active > a {
    color: white;
    background-color: #2f2f2f !Important;
    border: solid 2px white !Important;
  }
</style>