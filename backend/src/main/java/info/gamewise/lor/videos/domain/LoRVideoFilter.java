package info.gamewise.lor.videos.domain;

import java.util.List;

public final class LoRVideoFilter {
    private final List<VideoRegion> regions;
    private final List<VideoChannel> channels;
    private final List<VideoChampion> champions;

    public LoRVideoFilter(List<VideoRegion> regions, List<VideoChannel> channels, List<VideoChampion> champions) {
        this.regions = regions;
        this.channels = channels;
        this.champions = champions;
    }

    public List<VideoChampion> getChampions() {
        return champions;
    }

    public List<VideoChannel> getChannels() {
        return channels;
    }

    public List<VideoRegion> getRegions() {
        return regions;
    }
}
