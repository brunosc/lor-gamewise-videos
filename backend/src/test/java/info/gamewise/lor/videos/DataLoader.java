package info.gamewise.lor.videos;

import com.github.brunosc.fetcher.domain.VideoDetails;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.ThumbnailDetails;
import info.gamewise.lor.videos.domain.json.Champion;
import info.gamewise.lor.videos.domain.json.Channel;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoChannel;
import info.gamewise.lor.videos.domain.VideoRegion;
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort.NewVideo;

import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

public class DataLoader {

    private DataLoader(){}

    public static VideoDetails videoDetails(String id, String deckCode) {
        return new VideoDetails(playlistItem(id, deckCode, LocalDate.now().toEpochDay()));
    }

    public static VideoDetails videoDetails(String id, String deckCode, long publishedAt) {
        return new VideoDetails(playlistItem(id, deckCode, publishedAt));
    }

    public static NewVideo newVideo(int day, String id, Channel channel, Set<LoRRegion> regions, Set<LoRChampion> champions) {
        return new NewVideo("DECK_CODE", videoDetails(id, "DECK_CODE", LocalDate.of(2021, 1, day).toEpochDay()), channel, regions, champions);
    }

    public static LoRVideo newLoRVideo(Channel channel, Set<Champion> champions, Set<LoRRegion> regions, int day) {
        return new LoRVideo(
                "url " + day,
                "title " + day,
                "CODE" + day,
                new VideoChannel(channel),
                champions.stream().map(VideoChampion::new).collect(toUnmodifiableSet()),
                regions.stream().map(VideoRegion::new).collect(toUnmodifiableSet())        ,
                LocalDate.of(2020, 1, day).atStartOfDay(),
                null
        );
    }

    private static PlaylistItem playlistItem(String id, String deckCode, long publishedAt) {
        PlaylistItemSnippet snippet = new PlaylistItemSnippet()
                .setTitle("Title " + id)
                .setDescription(deckCode)
                .setResourceId(new ResourceId().setVideoId(id))
                .setThumbnails(new ThumbnailDetails())
                .setPublishedAt(new DateTime(publishedAt));

        return new PlaylistItem().setSnippet(snippet);
    }

}
