package info.gamewise.lor.videos.service;

import com.github.brunosc.fetcher.domain.VideoDetails;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.LoRChannel;
import info.gamewise.lor.videos.port.out.GetChannelsPort;
import info.gamewise.lor.videos.port.out.LatestYouTubeVideosUseCase;
import info.gamewise.lor.videos.port.out.VideoIsInDatabaseUseCase;
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort.NewVideo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static info.gamewise.lor.videos.DataLoader.videoDetails;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VideosNotInDatabasePortServiceTest {

    private static final String ID_NOT_IN_DB_123 = "123";
    private static final String ID_IN_DB_456 = "456";
    private static final String ID_NOT_IN_DB_789 = "789";

    private static final LoRChannel MEGA_MOGWAI = new LoRChannel("MEGA_MOGWAI", "MegaMogwai", "1");

    private final VideoIsInDatabaseUseCase videoIsInDatabaseUseCase =
            mock(VideoIsInDatabaseUseCase.class);

    private final LatestYouTubeVideosUseCase latestVideosUseCase =
            mock(LatestYouTubeVideosUseCase.class);

    private final GetChannelsPort getChannelsPort =
            mock(GetChannelsPort.class);

    private final VideosNotInDatabasePortService service =
            new VideosNotInDatabasePortService(videoIsInDatabaseUseCase, latestVideosUseCase, getChannelsPort);

    @Test
    void shouldFetchNormally() {
        givenVideos_ValidDeckCode();
        givenVideoIsInDatabase();

        List<NewVideo> videos = service.fetchNewVideos();

        NewVideo v123 = videos.stream().filter(v -> v.details().getId().equals(ID_NOT_IN_DB_123)).findFirst().orElse(null);
        NewVideo v789 = videos.stream().filter(v -> v.details().getId().equals(ID_NOT_IN_DB_789)).findFirst().orElse(null);

        assertNotNull(v123);
        assertNotNull(v789);
    }

    @Test
    void shouldNotFetchInvalidDeckCode() {
        givenVideos_InvalidDeckCode();
        givenVideoIsInDatabase();

        List<NewVideo> videos = service.fetchNewVideos();

        NewVideo v123 = videos.stream().filter(v -> v.details().getId().equals(ID_NOT_IN_DB_123)).findFirst().orElse(null);
        NewVideo v789 = videos.stream().filter(v -> v.details().getId().equals(ID_NOT_IN_DB_789)).findFirst().orElse(null);

        assertNull(v123);
        assertNotNull(v789);
    }

    private void givenVideos_ValidDeckCode() {
        String deckCode = "CICACAQDAMBQCBJHGU4AGAYFAMCAMBABAMBA6KBXAMAQCAZFAEBAGBABAMCQEAIBAEBS4";
        List<VideoDetails> latestVideos = List.of(videoDetails(ID_NOT_IN_DB_123, deckCode), videoDetails(ID_IN_DB_456, deckCode), videoDetails(ID_NOT_IN_DB_789, deckCode));
        given(latestVideosUseCase.latestVideosByChannel(eq(MEGA_MOGWAI)))
                .willReturn(latestVideos);
        given(getChannelsPort.getChannels())
                .willReturn(List.of(MEGA_MOGWAI));
    }

    private void givenVideos_InvalidDeckCode() {
        String deckCode = "CICACAQDAMBQCBJHGU4AGAYFAMCAMBABAMBA6KBXAMAQCAZFAEBAGBABAMCQEAIBAEBS4";
        List<VideoDetails> latestVideos = List.of(videoDetails(ID_NOT_IN_DB_123, "invalid-deck-code"), videoDetails(ID_IN_DB_456, deckCode), videoDetails(ID_NOT_IN_DB_789, deckCode));
        given(latestVideosUseCase.latestVideosByChannel(eq(MEGA_MOGWAI)))
                .willReturn(latestVideos);
        given(getChannelsPort.getChannels())
                .willReturn(List.of(MEGA_MOGWAI));
    }

    private void givenVideoIsInDatabase() {
        given(videoIsInDatabaseUseCase.isInDatabase(eq(ID_NOT_IN_DB_123))).willReturn(Boolean.FALSE);
        given(videoIsInDatabaseUseCase.isInDatabase(eq(ID_IN_DB_456))).willReturn(Boolean.TRUE);
        given(videoIsInDatabaseUseCase.isInDatabase(eq(ID_NOT_IN_DB_789))).willReturn(Boolean.FALSE);
    }

}