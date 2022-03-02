package info.gamewise.lor.videos;

import com.github.brunosc.fetcher.domain.VideoDetails;
import com.github.brunosc.lor.domain.LoRRegion;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.ThumbnailDetails;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoChannel;
import info.gamewise.lor.videos.domain.VideoRegion;
import info.gamewise.lor.videos.domain.json.Champion;
import info.gamewise.lor.videos.domain.json.Channel;
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort.NewVideo;

import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

public class DataLoader {

    public static final Channel MEGA_MOGWAI = new Channel("MEGA_MOGWAI", "MegaMogwai", "1");
    public static final Channel ALANZQ = new Channel("ALANZQ", "Alanzq", "2");
    public static final Channel SILVERFUSE = new Channel("SILVERFUSE", "Silverfuse", "2");

    public static final Champion EZREAL = new Champion("01PZ036", "EZREAL", "EZREAL");
    public static final Champion RENEKTON = new Champion("04SH067", "RENEKTON", "RENEKTON");
    public static final Champion LUCIAN = new Champion("01DE022", "LUCIAN", "LUCIAN");
    public static final Champion HECARIM = new Champion("01SI042", "HECARIM", "HECARIM");
    public static final Champion SWAIN = new Champion("02NX007", "SWAIN", "SWAIN");
    public static final Champion LISSANDRA = new Champion("04FR005", "LISSANDRA", "LISSANDRA");
    public static final Champion SHEN = new Champion("01IO032", "SHEN", "SHEN");
    public static final Champion JARVAN_IV = new Champion("04DE008", "JARVAN_IV", "JARVAN_IV");
    public static final Champion TRUNDLE = new Champion("03FR006", "TRUNDLE", "TRUNDLE");
    public static final Champion IRELIA = new Champion("04IO005", "IRELIA", "IRELIA");
    public static final Champion AZIR = new Champion("04SH003", "AZIR", "AZIR");
    public static final Champion ANIVIA = new Champion("01FR024", "ANIVIA", "ANIVIA");
    public static final Champion ELISE = new Champion("01SI053", "ELISE", "ELISE");
    public static final Champion KALISTA = new Champion("01SI030", "KALISTA", "KALISTA");
    public static final Champion TALIYAH = new Champion("04SH073", "TALIYAH", "TALIYAH");
    public static final Champion MALPHITE = new Champion("04MT008", "MALPHITE", "MALPHITE");
    public static final Champion AURELION_SOL = new Champion("03MT087", "AURELION_SOL", "AURELION_SOL");
    public static final Champion GAREN = new Champion("01DE012", "GAREN", "GAREN");
    
    public DataLoader(){}

    public static VideoDetails videoDetails(String id, String deckCode) {
        return new VideoDetails(playlistItem(id, deckCode, LocalDate.now().toEpochDay()));
    }

    public static VideoDetails videoDetails(String id, String deckCode, long publishedAt) {
        return new VideoDetails(playlistItem(id, deckCode, publishedAt));
    }

    public static NewVideo newVideo(int day, String id, Channel channel, Set<LoRRegion> regions, Set<Champion> champions) {
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
