package info.gamewise.lor.videos.controller;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.AbstractIntegrationTest;
import info.gamewise.lor.videos.domain.json.Channel;
import info.gamewise.lor.videos.port.out.SaveVideoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;
import java.util.UUID;

import static com.github.brunosc.lor.domain.LoRChampion.ANIVIA;
import static com.github.brunosc.lor.domain.LoRChampion.AURELION_SOL;
import static com.github.brunosc.lor.domain.LoRChampion.AZIR;
import static com.github.brunosc.lor.domain.LoRChampion.ELISE;
import static com.github.brunosc.lor.domain.LoRChampion.EZREAL;
import static com.github.brunosc.lor.domain.LoRChampion.GAREN;
import static com.github.brunosc.lor.domain.LoRChampion.HECARIM;
import static com.github.brunosc.lor.domain.LoRChampion.IRELIA;
import static com.github.brunosc.lor.domain.LoRChampion.JARVAN_IV;
import static com.github.brunosc.lor.domain.LoRChampion.KALISTA;
import static com.github.brunosc.lor.domain.LoRChampion.LISSANDRA;
import static com.github.brunosc.lor.domain.LoRChampion.LUCIAN;
import static com.github.brunosc.lor.domain.LoRChampion.MALPHITE;
import static com.github.brunosc.lor.domain.LoRChampion.RENEKTON;
import static com.github.brunosc.lor.domain.LoRChampion.SHEN;
import static com.github.brunosc.lor.domain.LoRChampion.SWAIN;
import static com.github.brunosc.lor.domain.LoRChampion.TALIYAH;
import static com.github.brunosc.lor.domain.LoRChampion.TRUNDLE;
import static com.github.brunosc.lor.domain.LoRRegion.DEMACIA;
import static com.github.brunosc.lor.domain.LoRRegion.FRELJORD;
import static com.github.brunosc.lor.domain.LoRRegion.IONIA;
import static com.github.brunosc.lor.domain.LoRRegion.MOUNT_TARGON;
import static com.github.brunosc.lor.domain.LoRRegion.NOXUS;
import static com.github.brunosc.lor.domain.LoRRegion.PILTOVER_AND_ZAUN;
import static com.github.brunosc.lor.domain.LoRRegion.SHADOW_ILES;
import static com.github.brunosc.lor.domain.LoRRegion.SHURIMA;
import static info.gamewise.lor.videos.DataLoader.newVideo;
import static java.util.Set.of;

class ApiControllerTest extends AbstractIntegrationTest {

    private static final String API_VIDEOS = "/api/videos";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private SaveVideoUseCase saveVideoUseCase;

    private static final Channel MEGA_MOGWAI = new Channel("MEGA_MOGWAI", "MegaMogwai", "1");
    private static final Channel ALANZQ = new Channel("ALANZQ", "Alanzq", "2");
    private static final Channel SILVERFUSE = new Channel("SILVERFUSE", "Silverfuse", "2");

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
