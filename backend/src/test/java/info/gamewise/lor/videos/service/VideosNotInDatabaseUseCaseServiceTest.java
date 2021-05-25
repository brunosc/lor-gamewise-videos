package info.gamewise.lor.videos.service;

import com.github.brunosc.fetcher.domain.VideoDetails;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.ThumbnailDetails;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.port.out.LatestYouTubeVideosUseCase;
import info.gamewise.lor.videos.port.out.VideoIsInDatabaseUseCase;
import info.gamewise.lor.videos.port.out.VideosNotInDatabaseUseCase.NewVideo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

class VideosNotInDatabaseUseCaseServiceTest {

    private final VideoIsInDatabaseUseCase videoIsInDatabaseUseCase =
            Mockito.mock(VideoIsInDatabaseUseCase.class);

    private final LatestYouTubeVideosUseCase latestVideosUseCase =
            Mockito.mock(LatestYouTubeVideosUseCase.class);

    private final VideosNotInDatabaseUseCaseService service =
            new VideosNotInDatabaseUseCaseService(videoIsInDatabaseUseCase, latestVideosUseCase);

    @Test
    void shouldFetchNormally() {
        givenVideos_ValidDeckCode();
        givenVideoIsInDatabase();

        List<NewVideo> videos = service.fetchNewVideos();

        NewVideo v123 = videos.stream().filter(v -> v.getDetails().getId().equals("123")).findFirst().orElse(null);
        NewVideo v789 = videos.stream().filter(v -> v.getDetails().getId().equals("789")).findFirst().orElse(null);

        assertNotNull(v123);
        assertNotNull(v789);
    }

    @Test
    void shouldNotFetchInvalidDeckCode() {
        givenVideos_InvalidDeckCode();
        givenVideoIsInDatabase();

        List<NewVideo> videos = service.fetchNewVideos();

        NewVideo v123 = videos.stream().filter(v -> v.getDetails().getId().equals("123")).findFirst().orElse(null);
        NewVideo v789 = videos.stream().filter(v -> v.getDetails().getId().equals("789")).findFirst().orElse(null);

        assertNull(v123);
        assertNotNull(v789);
    }

    private void givenVideos_ValidDeckCode() {
        String deckCode = "CICACAQDAMBQCBJHGU4AGAYFAMCAMBABAMBA6KBXAMAQCAZFAEBAGBABAMCQEAIBAEBS4";
        List<VideoDetails> latestVideos = List.of(createVideoDetails("123", deckCode), createVideoDetails("456", deckCode), createVideoDetails("789", deckCode));
        given(latestVideosUseCase.latestVideosByChannel(eq(Channel.MEGA_MOGWAI)))
                .willReturn(latestVideos);
    }

    private void givenVideos_InvalidDeckCode() {
        String deckCode = "CICACAQDAMBQCBJHGU4AGAYFAMCAMBABAMBA6KBXAMAQCAZFAEBAGBABAMCQEAIBAEBS4";
        List<VideoDetails> latestVideos = List.of(createVideoDetails("123", "invalid-deck-code"), createVideoDetails("456", deckCode), createVideoDetails("789", deckCode));
        given(latestVideosUseCase.latestVideosByChannel(eq(Channel.MEGA_MOGWAI)))
                .willReturn(latestVideos);
    }

    private void givenVideoIsInDatabase() {
        given(videoIsInDatabaseUseCase.isInDatabase(eq("123"))).willReturn(Boolean.FALSE);
        given(videoIsInDatabaseUseCase.isInDatabase(eq("456"))).willReturn(Boolean.TRUE);
        given(videoIsInDatabaseUseCase.isInDatabase(eq("789"))).willReturn(Boolean.FALSE);
    }

    private static VideoDetails createVideoDetails(String id, String deckCode) {
        return new VideoDetails(createPlaylistItem(id, deckCode));
    }

    private static PlaylistItem createPlaylistItem(String id, String deckCode) {
        PlaylistItem item = new PlaylistItem();
        ResourceId resourceId = new ResourceId().setVideoId(id);
        PlaylistItemSnippet snippet = new PlaylistItemSnippet()
                .setTitle("Title " + id)
                .setDescription(deckCode)
                .setResourceId(resourceId)
                .setThumbnails(new ThumbnailDetails())
                .setPublishedAt(new DateTime(new Date()));

        item.setSnippet(snippet);

        return item;
    }
}