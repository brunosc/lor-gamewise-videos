package info.gamewise.lor.videos.controller;

import com.github.brunosc.fetcher.domain.VideoDetails;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.ThumbnailDetails;
import info.gamewise.lor.videos.AbstractIntegrationTest;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.port.out.SaveVideoUseCase;
import info.gamewise.lor.videos.port.out.VideosNotInDatabaseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static com.github.brunosc.lor.domain.LoRChampion.*;
import static com.github.brunosc.lor.domain.LoRRegion.*;
import static info.gamewise.lor.videos.domain.Channel.*;
import static java.util.Set.of;
import static java.util.stream.Collectors.joining;

class ApiControllerTest extends AbstractIntegrationTest {

    private static final String API_VIDEOS = "/api/videos";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private SaveVideoUseCase saveVideoUseCase;

    @BeforeEach
    void init() {
        saveVideoUseCase.save(newVideo(1, UUID.randomUUID().toString(), MEGA_MOGWAI, of(PILTOVER_AND_ZAUN, SHURIMA), of(EZREAL, RENEKTON)));
        saveVideoUseCase.save(newVideo(2, UUID.randomUUID().toString(), MEGA_MOGWAI, of(DEMACIA, SHADOW_ILES), of(LUCIAN, HECARIM)));
        saveVideoUseCase.save(newVideo(3, UUID.randomUUID().toString(), MEGA_MOGWAI, of(FRELJORD, NOXUS), of(SWAIN, LISSANDRA)));
        saveVideoUseCase.save(newVideo(4, UUID.randomUUID().toString(), MEGA_MOGWAI, of(DEMACIA, IONIA), of(SHEN, JARVAN_IV)));

        saveVideoUseCase.save(newVideo(5, UUID.randomUUID().toString(), ALANZQ, of(FRELJORD, SHADOW_ILES), of(TRUNDLE, LISSANDRA)));
        saveVideoUseCase.save(newVideo(6, UUID.randomUUID().toString(), ALANZQ, of(IONIA, SHURIMA), of(IRELIA, AZIR)));

        saveVideoUseCase.save(newVideo(7, UUID.randomUUID().toString(), SILVERFUSE, of(FRELJORD, SHADOW_ILES), of(ANIVIA)));
        saveVideoUseCase.save(newVideo(8, UUID.randomUUID().toString(), SILVERFUSE, of(SHURIMA, SHADOW_ILES), of(ELISE, KALISTA)));
        saveVideoUseCase.save(newVideo(9, UUID.randomUUID().toString(), SILVERFUSE, of(FRELJORD, PILTOVER_AND_ZAUN), of(TRUNDLE)));
        saveVideoUseCase.save(newVideo(10, UUID.randomUUID().toString(), SILVERFUSE, of(FRELJORD, SHURIMA), of(TALIYAH, LISSANDRA)));
        saveVideoUseCase.save(newVideo(11, UUID.randomUUID().toString(), SILVERFUSE, of(DEMACIA, MOUNT_TARGON), of(MALPHITE, AURELION_SOL, GAREN)));
    }

    @Test
    void shouldFetchAllVideosWithNoFilter() {
        webTestClient
                .get()
                .uri(API_VIDEOS)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(11)
                .jsonPath("$.content[0].channel.code").isEqualTo(SILVERFUSE.name())
                .jsonPath("$.content[5].channel.code").isEqualTo(ALANZQ.name())
                .jsonPath("$.content[7].channel.code").isEqualTo(MEGA_MOGWAI.name());

    }

    @Test
    void shouldFilterByChannels() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?channels={channelId}&channels={channelId}", MEGA_MOGWAI, SILVERFUSE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(9)
                .jsonPath("$.content[0].channel.code").isEqualTo(SILVERFUSE.name())
                .jsonPath("$.content[5].channel.code").isEqualTo(MEGA_MOGWAI.name());
    }

    @Test
    void shouldFilterByChampions() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?champions={championId}&champions={championId}", LISSANDRA.getId(), TRUNDLE.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(1);
    }

    @Test
    void shouldFilterByRegions() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?regions={regionId}&regions={regionId}", SHADOW_ILES.getCode(), FRELJORD.getCode())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(2);
    }

    @Test
    void shouldFilterByChannelsRegionsAndChampions() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?channels={channelId}&channels={channelId}&champions={championId}&regions={regionId}", MEGA_MOGWAI, ALANZQ, LISSANDRA.getId(), FRELJORD.getCode())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(2);
    }

    @Test
    void shouldFetchTheFilters() {
        webTestClient
                .get()
                .uri("/api/filters")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.regions.size()").isEqualTo(LoRRegion.values().length)
                .jsonPath("$.channels.size()").isEqualTo(Channel.values().length)
                .jsonPath("$.champions.size()").isEqualTo(LoRChampion.values().length);
    }

    private VideosNotInDatabaseUseCase.NewVideo newVideo(int day, String id, Channel channel, Set<LoRRegion> regions, Set<LoRChampion> champions) {
        PlaylistItemSnippet snippet = new PlaylistItemSnippet()
                .setTitle(champions.stream().map(LoRChampion::prettyName).collect(joining(", ")))
                .setDescription("Video " + id)
                .setResourceId(new ResourceId().setVideoId(id))
                .setThumbnails(new ThumbnailDetails())
                .setPublishedAt(new DateTime(LocalDate.of(2021, 1, day).toEpochDay()));

        PlaylistItem item = new PlaylistItem().setSnippet(snippet);

        return new VideosNotInDatabaseUseCase.NewVideo("DECK_CODE", new VideoDetails(item), channel, regions, champions);
    }

}
