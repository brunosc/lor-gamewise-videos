import axios from 'axios';

export default class VideoService {

  static getVideos(filter, pageNumber = 0) {
    const regions = filter.regions.map(region => `regions=${region}`).join('&');
    const champions = filter.champions.map(champion => `champions=${champion.code}`).join('&');
    const size = 'size=20';
    const page = `page=${pageNumber}`;
    return axios.get(`/api/videos?${regions}&${champions}&${size}&${page}`);
  }

  static getFilters() {
    return axios.get(`/api/filters`);
  }

}