package info.gamewise.lor.videos.port.in;

import info.gamewise.lor.videos.domain.LoRVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetVideosUseCase {
    Page<LoRVideo> fetchByFilter(SearchParams params, Pageable pageable);

    record SearchParams(List<String> regions, List<String> champions,
                        List<String> channels) {
    }
}
