package info.gamewise.lor.videos.domain;

import com.github.brunosc.fetcher.domain.VideoThumbnails.ThumbnailItem;

import java.time.LocalDateTime;
import java.util.Set;

public record LoRVideo(String url,
                       String title,
                       String deckCode,
                       VideoChannel channel,
                       Set<VideoChampion> champions,
                       Set<VideoRegion> regions,
                       LocalDateTime publishedAt,
                       ThumbnailItem thumbnail) {

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
