package info.gamewise.lor.videos.entity;

import com.github.brunosc.fetcher.domain.VideoThumbnails;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.usecase.VideosNotInDatabaseUseCase.NewVideo;
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
    private Channel channel;
    private Set<LoRRegion> regions;
    private Set<LoRChampion> champions;
    private LocalDateTime publishedAt;
    private VideoThumbnails thumbnails;

    VideoJpaEntity() {

    }

    VideoJpaEntity(NewVideo newVideo) {
        this.videoId = newVideo.getDetails().getId();
        this.url = newVideo.getDetails().getUrl();
        this.title = newVideo.getDetails().getTitle();
        this.deckCode = newVideo.getDeckCode();
        this.publishedAt = newVideo.getDetails().getPublishedAt();
        this.thumbnails = newVideo.getDetails().getThumbnails();
        this.channel = newVideo.getChannel();
        this.regions = newVideo.getRegions();
        this.champions = newVideo.getChampions();
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

    public Channel getChannel() {
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
