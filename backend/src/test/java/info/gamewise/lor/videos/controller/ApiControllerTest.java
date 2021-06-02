package info.gamewise.lor.videos.controller;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.AbstractIntegrationTest;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.port.out.SaveVideoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;
import java.util.UUID;

import static com.github.brunosc.lor.domain.LoRChampion.*;
import static com.github.brunosc.lor.domain.LoRRegion.*;
import static info.gamewise.lor.videos.DataLoader.newVideo;
import static info.gamewise.lor.videos.domain.Channel.*;
import static java.util.Set.of;

class ApiControllerTest extends AbstractIntegrationTest {

    private static final String API_VIDEOS = "/api/videos";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private SaveVideoUseCase saveVideoUseCase;

    @BeforeEach
    void init() {
        saveNewVideo(1, MEGA_MOGWAI, of(PILTOVER_AND_ZAUN, SHURIMA), of(EZREAL, RENEKTON));
        saveNewVideo(2, MEGA_MOGWAI, of(DEMACIA, SHADOW_ILES), of(LUCIAN, HECARIM));
        saveNewVideo(3, MEGA_MOGWAI, of(FRELJORD, NOXUS), of(SWAIN, LISSANDRA));
        saveNewVideo(4, MEGA_MOGWAI, of(DEMACIA, IONIA), of(SHEN, JARVAN_IV));

        saveNewVideo(5, ALANZQ, of(FRELJORD, SHADOW_ILES), of(TRUNDLE, LISSANDRA));
        saveNewVideo(6, ALANZQ, of(IONIA, SHURIMA), of(IRELIA, AZIR));

        saveNewVideo(7, SILVERFUSE, of(FRELJORD, SHADOW_ILES), of(ANIVIA));
        saveNewVideo(8, SILVERFUSE, of(SHURIMA, SHADOW_ILES), of(ELISE, KALISTA));
        saveNewVideo(9, SILVERFUSE, of(FRELJORD, PILTOVER_AND_ZAUN), of(TRUNDLE));
        saveNewVideo(10, SILVERFUSE, of(FRELJORD, SHURIMA), of(TALIYAH, LISSANDRA));
        saveNewVideo(11, SILVERFUSE, of(DEMACIA, MOUNT_TARGON), of(MALPHITE, AURELION_SOL, GAREN));
    }

    private void saveNewVideo(int day, Channel channel, Set<LoRRegion> regions, Set<LoRChampion> champions) {
        saveVideoUseCase.save(newVideo(day, UUID.randomUUID().toString(), channel, regions, champions));
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
                .jsonPath("$.content.size()").isEqualTo(1)
                .jsonPath("$.content[0].channel.code").isEqualTo(ALANZQ.name());
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
                .jsonPath("$.content[0].channel.code").isEqualTo(SILVERFUSE.name())
                .jsonPath("$.content[1].channel.code").isEqualTo(ALANZQ.name());
    }

    @Test
    void shouldFilterByChannelsRegionsAndChampions() {
        webTestClient
                .get()
                .uri(API_VIDEOS + "?channels={channelId}&channels={channelId}&champions={championId}&regions={regionId}", MEGA_MOGWAI, ALANZQ, LISSANDRA.getId(), FRELJORD.getCode())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(2)
                .jsonPath("$.content[0].channel.code").isEqualTo(ALANZQ.name())
                .jsonPath("$.content[1].channel.code").isEqualTo(MEGA_MOGWAI.name());
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

}
