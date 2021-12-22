package info.gamewise.lor.videos.entity;

import com.github.brunosc.fetcher.domain.VideoThumbnails;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort.NewVideo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "videos")
class VideoJpaEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String videoId;

    private String url;
    private String title;
    private String deckCode;
    private String channel;
    private Set<LoRRegion> regions;
    private Set<LoRChampion> champions;
    private LocalDateTime publishedAt;
    private VideoThumbnails thumbnails;

    VideoJpaEntity() {

    }

    VideoJpaEntity(NewVideo newVideo) {
        this.videoId = newVideo.details().getId();
        this.url = newVideo.details().getUrl();
        this.title = newVideo.details().getTitle();
        this.deckCode = newVideo.deckCode();
        this.publishedAt = newVideo.details().getPublishedAt();
        this.thumbnails = newVideo.details().getThumbnails();
        this.channel = newVideo.channel().code();
        this.regions = newVideo.regions();
        this.champions = newVideo.champions();
    }

    public String getId() {
        return id;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getChannel() {
        return channel;
    }

    public Set<LoRRegion> getRegions() {
        return regions;
    }

    public Set<LoRChampion> getChampions() {
        return champions;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public VideoThumbnails getThumbnails() {
        return thumbnails;
    }

    public String getDeckCode() {
        return deckCode;
    }
}
