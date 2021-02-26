package info.gamewise.lor.videos.domain;

import java.util.List;

public final class LoRVideoFilter {
    private final List<VideoChampion> champions;
    private final List<VideoRegion> regions;

    public LoRVideoFilter(List<VideoChampion> champions, List<VideoRegion> regions) {
        this.champions = champions;
        this.regions = regions;
    }

    public List<VideoChampion> getChampions() {
        return champions;
    }

    public List<VideoRegion> getRegions() {
        return regions;
    }
}
