import axios from 'axios';

export default class VideoService {

  static getVideos(filter, pageNumber = 0) {
    const filterValues = VideoService._buildFilter(filter);
    const pagination = VideoService._buildPagination(pageNumber);
    return axios.get(`/api/videos?${filterValues}&${pagination}`);
  }

  static _buildFilter(filter) {
    const regions = filter.regions.map(region => `regions=${region}`).join('&');
    const champions = filter.champions.map(champion => `champions=${champion.code}`).join('&');
    const channels = filter.channels.map(channel => `channels=${channel.code}`).join('&');
    return `${regions}&${champions}&${channels}`;
  }

  static _buildPagination(pageNumber) {
    const size = 'size=20';
    const page = `page=${pageNumber}`;
    return `${size}&${page}`;
  }

  static getFilters() {
    return axios.get(`/api/filters`);
  }

}