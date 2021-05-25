import axios from 'axios';

export default class ChannelService {

  static getChannelStatistics(channel) {
    return axios.get(`/api/channel/${channel}/statistics`);
  }

}
