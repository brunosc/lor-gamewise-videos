package info.gamewise.lor.videos.usecase;

import info.gamewise.lor.videos.domain.LoRVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetVideosUseCase {

    Page<LoRVideo> fetchByFilter(SearchParams params, Pageable pageable);

    class SearchParams {
        private final List<String> regions;
        private final List<String> champions;

        public SearchParams(List<String> regions, List<String> champions) {
            this.regions = regions;
            this.champions = champions;
        }

        public List<String> getChampions() {
            return champions;
        }

        public List<String> getRegions() {
            return regions;
        }
    }
}
