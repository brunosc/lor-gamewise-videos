package info.gamewise.lor.videos.domain;

import com.github.brunosc.fetcher.domain.VideoThumbnails.ThumbnailItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public final class LoRVideo {

    private final String url;
    private final String title;
    private final String deckCode;
    private final VideoChannel channel;
    private final Set<VideoChampion> champions;
    private final Set<VideoRegion> regions;
    private final LocalDateTime publishedAt;
    private final ThumbnailItem thumbnail;

    public LoRVideo(String url, String title, String deckCode, VideoChannel channel, Set<VideoChampion> champions, Set<VideoRegion> regions, LocalDateTime publishedAt, ThumbnailItem thumbnail) {
        this.url = url;
        this.title = title;
        this.deckCode = deckCode;
        this.channel = channel;
        this.champions = champions;
        this.regions = regions;
        this.publishedAt = publishedAt;
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDeckCode() {
        return deckCode;
    }

    public VideoChannel getChannel() {
        return channel;
    }

    public Set<VideoChampion> getChampions() {
        return champions;
    }

    public Set<VideoRegion> getRegions() {
        return regions;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public ThumbnailItem getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return "LoRVideo{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", deckCode='" + deckCode + '\'' +
                ", channel=" + channel +
                '}';
    }
}
