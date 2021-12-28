package info.gamewise.lor.videos.domain;

import java.util.List;

public record LoRVideoFilter(List<VideoRegion> regions,
                             List<VideoChannel> channels,
                             List<VideoChampion> champions) {
}
