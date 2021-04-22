package info.gamewise.lor.videos.usecase;

import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.LoRVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetVideosUseCase {
    Page<LoRVideo> fetchByFilter(SearchParams params, Pageable pageable);

    class SearchParams {
        private final List<String> regions;
        private final List<String> champions;
        private final List<Channel> channels;

        public SearchParams(List<String> regions, List<String> champions, List<Channel> channels) {
            this.regions = regions;
            this.champions = champions;
            this.channels = channels;
        }

        public List<String> getChampions() {
            return champions;
        }

        public List<String> getRegions() {
            return regions;
        }

        public List<Channel> getChannels() {
            return channels;
        }
    }
}
