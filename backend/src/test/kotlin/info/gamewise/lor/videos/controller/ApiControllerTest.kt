package info.gamewise.lor.videos.controller

import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.AbstractIntegrationTest
import info.gamewise.lor.videos.DataLoader
import info.gamewise.lor.videos.DataLoader.newVideo
import info.gamewise.lor.videos.port.out.SaveVideoUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

private const val API_VIDEOS = "/api/videos"

internal class ApiControllerTest : AbstractIntegrationTest() {
    
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var saveVideoUseCase: SaveVideoUseCase
    
    @BeforeEach
    fun initVideos() {
        val videos = listOf(
            newVideo(
                1,
                UUID.randomUUID().toString(),
                DataLoader.MEGA_MOGWAI,
                setOf(LoRRegion.PILTOVER_AND_ZAUN, LoRRegion.SHURIMA),
                setOf(DataLoader.EZREAL, DataLoader.RENEKTON)
            ),
            newVideo(
                2,
                UUID.randomUUID().toString(),
                DataLoader.MEGA_MOGWAI,
                setOf(LoRRegion.DEMACIA, LoRRegion.SHADOW_ILES),
                setOf(DataLoader.LUCIAN, DataLoader.HECARIM)
            ),
            newVideo(
                3,
                UUID.randomUUID().toString(),
                DataLoader.MEGA_MOGWAI,
                setOf(LoRRegion.FRELJORD, LoRRegion.NOXUS),
                setOf(DataLoader.SWAIN, DataLoader.LISSANDRA)
            ),
            newVideo(
                4,
                UUID.randomUUID().toString(),
                DataLoader.MEGA_MOGWAI,
                setOf(LoRRegion.DEMACIA, LoRRegion.IONIA),
                setOf(DataLoader.SHEN, DataLoader.JARVAN_IV)
            ),
            newVideo(
                5,
                UUID.randomUUID().toString(),
                DataLoader.ALANZQ,
                setOf(LoRRegion.FRELJORD, LoRRegion.SHADOW_ILES),
                setOf(DataLoader.TRUNDLE, DataLoader.LISSANDRA)
            ),
            newVideo(
                6,
                UUID.randomUUID().toString(),
                DataLoader.ALANZQ,
                setOf(LoRRegion.IONIA, LoRRegion.SHURIMA),
                setOf(DataLoader.IRELIA, DataLoader.AZIR)
            ),
            newVideo(
                7,
                UUID.randomUUID().toString(),
                DataLoader.SILVERFUSE,
                setOf(LoRRegion.FRELJORD, LoRRegion.SHADOW_ILES),
                setOf(DataLoader.ANIVIA)
            ),
            newVideo(
                8,
                UUID.randomUUID().toString(),
                DataLoader.SILVERFUSE,
                setOf(LoRRegion.SHURIMA, LoRRegion.SHADOW_ILES),
                setOf(DataLoader.ELISE, DataLoader.KALISTA)
            ),
            newVideo(
                9,
                UUID.randomUUID().toString(),
                DataLoader.SILVERFUSE,
                setOf(LoRRegion.FRELJORD, LoRRegion.PILTOVER_AND_ZAUN),
                setOf(DataLoader.TRUNDLE)
            ),
            newVideo(
                10,
                UUID.randomUUID().toString(),
                DataLoader.SILVERFUSE,
                setOf(LoRRegion.FRELJORD, LoRRegion.SHURIMA),
                setOf(DataLoader.TALIYAH, DataLoader.LISSANDRA)
            ),
            newVideo(
                11,
                UUID.randomUUID().toString(),
                DataLoader.SILVERFUSE,
                setOf(LoRRegion.DEMACIA, LoRRegion.MOUNT_TARGON),
                setOf(DataLoader.MALPHITE, DataLoader.AURELION_SOL, DataLoader.GAREN)
            )
        )
        saveVideoUseCase.save(videos)
    }

    @Test
    fun shouldFetchAllVideosWithNoFilter() {
        webTestClient
            .get()
            .uri(API_VIDEOS)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.content.size()").isEqualTo(11)
            .jsonPath("$.content[0].channel.code").isEqualTo(DataLoader.SILVERFUSE.code)
            .jsonPath("$.content[5].channel.code").isEqualTo(DataLoader.ALANZQ.code)
            .jsonPath("$.content[7].channel.code").isEqualTo(DataLoader.MEGA_MOGWAI.code)
    }

    @Test
    fun shouldFilterByChannels() {
        webTestClient
            .get()
            .uri(
                "$API_VIDEOS?channels={channelId}&channels={channelId}",
                DataLoader.MEGA_MOGWAI.code,
                DataLoader.SILVERFUSE.code
            )
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.content.size()").isEqualTo(9)
            .jsonPath("$.content[0].channel.code").isEqualTo(DataLoader.SILVERFUSE.code)
            .jsonPath("$.content[5].channel.code").isEqualTo(DataLoader.MEGA_MOGWAI.code)
    }

    @Test
    fun shouldFilterByChampions() {
        webTestClient
            .get()
            .uri(
                "$API_VIDEOS?champions={championId}&champions={championId}",
                DataLoader.LISSANDRA.name,
                DataLoader.TRUNDLE.name
            )
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.content.size()").isEqualTo(1)
            .jsonPath("$.content[0].channel.code").isEqualTo(DataLoader.ALANZQ.code)
    }

    @Test
    fun shouldFilterByRegions() {
        webTestClient
            .get()
            .uri(
                "$API_VIDEOS?regions={regionId}&regions={regionId}",
                LoRRegion.SHADOW_ILES.code,
                LoRRegion.FRELJORD.code
            )
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.content.size()").isEqualTo(2)
            .jsonPath("$.content[0].channel.code").isEqualTo(DataLoader.SILVERFUSE.code)
            .jsonPath("$.content[1].channel.code").isEqualTo(DataLoader.ALANZQ.code)
    }

    @Test
    fun shouldFilterByChannelsRegionsAndChampions() {
        webTestClient
            .get()
            .uri(
                "$API_VIDEOS?channels={channelId}&channels={channelId}&champions={championId}&regions={regionId}",
                DataLoader.MEGA_MOGWAI.code,
                DataLoader.ALANZQ.code,
                DataLoader.LISSANDRA.name,
                LoRRegion.FRELJORD.code
            )
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.content.size()").isEqualTo(2)
            .jsonPath("$.content[0].channel.code").isEqualTo(DataLoader.ALANZQ.code)
            .jsonPath("$.content[1].channel.code").isEqualTo(DataLoader.MEGA_MOGWAI.code)
    }

    @Test
    fun shouldFetchTheFilters() {
        webTestClient
            .get()
            .uri("/api/filters")
            .exchange()
            .expectStatus()
            .isOk
    }

}