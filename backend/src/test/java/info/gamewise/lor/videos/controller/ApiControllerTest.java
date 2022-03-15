package info.gamewise.lor.videos.controller;

import info.gamewise.lor.videos.AbstractIntegrationTest;
import info.gamewise.lor.videos.port.out.SaveVideoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.github.brunosc.lor.domain.LoRRegion.DEMACIA;
import static com.github.brunosc.lor.domain.LoRRegion.FRELJORD;
import static com.github.brunosc.lor.domain.LoRRegion.IONIA;
import static com.github.brunosc.lor.domain.LoRRegion.MOUNT_TARGON;
import static com.github.brunosc.lor.domain.LoRRegion.NOXUS;
import static com.github.brunosc.lor.domain.LoRRegion.PILTOVER_AND_ZAUN;
import static com.github.brunosc.lor.domain.LoRRegion.SHADOW_ILES;
import static com.github.brunosc.lor.domain.LoRRegion.SHURIMA;
import static info.gamewise.lor.videos.DataLoader.ALANZQ;
import static info.gamewise.lor.videos.DataLoader.ANIVIA;
import static info.gamewise.lor.videos.DataLoader.AURELION_SOL;
import static info.gamewise.lor.videos.DataLoader.AZIR;
import static info.gamewise.lor.videos.DataLoader.ELISE;
import static info.gamewise.lor.videos.DataLoader.EZREAL;
import static info.gamewise.lor.videos.DataLoader.GAREN;
import static info.gamewise.lor.videos.DataLoader.HECARIM;
import static info.gamewise.lor.videos.DataLoader.IRELIA;
import static info.gamewise.lor.videos.DataLoader.JARVAN_IV;
import static info.gamewise.lor.videos.DataLoader.KALISTA;
import static info.gamewise.lor.videos.DataLoader.LISSANDRA;
import static info.gamewise.lor.videos.DataLoader.LUCIAN;
import static info.gamewise.lor.videos.DataLoader.MALPHITE;
import static info.gamewise.lor.videos.DataLoader.MEGA_MOGWAI;
import static info.gamewise.lor.videos.DataLoader.RENEKTON;
import static info.gamewise.lor.videos.DataLoader.SHEN;
import static info.gamewise.lor.videos.DataLoader.SILVERFUSE;
import static info.gamewise.lor.videos.DataLoader.SWAIN;
import static info.gamewise.lor.videos.DataLoader.TALIYAH;
import static info.gamewise.lor.videos.DataLoader.TRUNDLE;
import static info.gamewise.lor.videos.DataLoader.newVideo;
import static java.util.Set.of;
import static java.util.UUID.randomUUID;

class ApiControllerTest extends AbstractIntegrationTest {

    private static final String API_VIDEOS = "/api/videos";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private SaveVideoUseCase saveVideoUseCase;

    @BeforeEach
    void init() {
        final var videos = List.of(
                newVideo(1, randomUUID().toString(), MEGA_MOGWAI, of(PILTOVER_AND_ZAUN, SHURIMA), of(EZREAL, RENEKTON)),
                newVideo(2, randomUUID().toString(), MEGA_MOGWAI, of(DEMACIA, SHADOW_ILES), of(LUCIAN, HECARIM)),
                newVideo(3, randomUUID().toString(), MEGA_MOGWAI, of(FRELJORD, NOXUS), of(SWAIN, LISSANDRA)),
                newVideo(4, randomUUID().toString(), MEGA_MOGWAI, of(DEMACIA, IONIA), of(SHEN, JARVAN_IV)),

                newVideo(5, randomUUID().toString(), ALANZQ, of(FRELJORD, SHADOW_ILES), of(TRUNDLE, LISSANDRA)),
                newVideo(6, randomUUID().toString(), ALANZQ, of(IONIA, SHURIMA), of(IRELIA, AZIR)),

                newVideo(7, randomUUID().toString(), SILVERFUSE, of(FRELJORD, SHADOW_ILES), of(ANIVIA)),
                newVideo(8, randomUUID().toString(), SILVERFUSE, of(SHURIMA, SHADOW_ILES), of(ELISE, KALISTA)),
                newVideo(9, randomUUID().toString(), SILVERFUSE, of(FRELJORD, PILTOVER_AND_ZAUN), of(TRUNDLE)),
                newVideo(10, randomUUID().toString(), SILVERFUSE, of(FRELJORD, SHURIMA), of(TALIYAH, LISSANDRA)),
                newVideo(11, randomUUID().toString(), SILVERFUSE, of(DEMACIA, MOUNT_TARGON), of(MALPHITE, AURELION_SOL, GAREN))
        );

        saveVideoUseCase.save(videos);
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
                .jsonPath("$.content[0].channel.code").isEqualTo(SILVERFUSE.code())
                .jsonPath("$.content[5].channel.code").isEqualTo(ALANZQ.code())
                .jsonPath("$.content[7].channel.code").isEqualTo(MEGA_MOGWAI.code());
    }

    @Test
    void shouldFilterByChannels() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?channels={channelId}&channels={channelId}", MEGA_MOGWAI.code(), SILVERFUSE.code())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(9)
                .jsonPath("$.content[0].channel.code").isEqualTo(SILVERFUSE.code())
                .jsonPath("$.content[5].channel.code").isEqualTo(MEGA_MOGWAI.code());
    }

    @Test
    void shouldFilterByChampions() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?champions={championId}&champions={championId}", LISSANDRA.name(), TRUNDLE.name())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(1)
                .jsonPath("$.content[0].channel.code").isEqualTo(ALANZQ.code());
    }

    @Test
    void shouldFilterByRegions() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?regions={regionId}&regions={regionId}", SHADOW_ILES.getCode(), FRELJORD.getCode())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(2)
                .jsonPath("$.content[0].channel.code").isEqualTo(SILVERFUSE.code())
                .jsonPath("$.content[1].channel.code").isEqualTo(ALANZQ.code());
    }

    @Test
    void shouldFilterByChannelsRegionsAndChampions() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?channels={channelId}&channels={channelId}&champions={championId}&regions={regionId}", MEGA_MOGWAI.code(), ALANZQ.code(), LISSANDRA.name(), FRELJORD.getCode())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(2)
                .jsonPath("$.content[0].channel.code").isEqualTo(ALANZQ.code())
                .jsonPath("$.content[1].channel.code").isEqualTo(MEGA_MOGWAI.code());
    }

    @Test
    void shouldFetchTheFilters() {
        webTestClient
                .get()
                .uri("/api/filters")
                .exchange()
                .expectStatus()
                .isOk();
    }

}
