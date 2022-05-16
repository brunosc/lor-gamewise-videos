package info.gamewise.lor.videos.service

import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.DataLoader.newLoRVideo
import info.gamewise.lor.videos.domain.LoRVideo
import info.gamewise.lor.videos.domain.NameCount
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.GetChampionsPort
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

private val MEGA_MOGWAI = Channel("MEGA_MOGWAI", "MegaMogwai", "1")
private val ELISE: Champion = Champion("1", "ELISE", "Elise")
private val LEE_SIN: Champion = Champion("2", "LEE_SIN", "Lee Sin")
private val RIVEN: Champion = Champion("3", "RIVEN", "Riven")
private val YASUO: Champion = Champion("4", "YASUO", "Yasuo")
private val SWAIN: Champion = Champion("5", "SWAIN", "Swain")
private val ZED: Champion = Champion("6", "ZED", "Zed")
private val SIVIR: Champion = Champion("7", "SIVIR", "Sivir")
private val RENEKTON: Champion = Champion("8", "RENEKTON", "Renekton")
private val KINDRED: Champion = Champion("9", "KINDRED", "Kindred")
private val ASHE: Champion = Champion("10", "ASHE", "Ashe")
private val EZREAL: Champion = Champion("11", "EZREAL", "Ezreal")
private val MALPHITE: Champion = Champion("12", "MALPHITE", "Malphite")

@ExtendWith(MockitoExtension::class)
internal class ChannelStatisticsServiceTest(@Mock val port: GetVideosByChannelPort,
                                            @Mock val getChampionsPort: GetChampionsPort) {

    private val service = ChannelStatisticsService(port, getChampionsPort)

    @Test
    fun shouldCalculateStatisticsByChannel() {
        val channel = MEGA_MOGWAI
        given(port.videosByChannel(channel.code)).willReturn(videos(channel))
        given(getChampionsPort.getChampions()).willReturn(champions())

        val channelStatistics = service.channelStatistics(channel.code)

        assertEquals(channelStatistics.startedAt, LocalDate.of(2020, 1, 1))
        assertThat(channelStatistics.champions).isNotEmpty
        assertThat(channelStatistics.regions).isNotEmpty

        val mostPopularChampion: NameCount = channelStatistics.champions[0]
        assertEquals(mostPopularChampion.name, YASUO.name)
        assertEquals(mostPopularChampion.count, 2)
        val mostPopularRegion: NameCount = channelStatistics.regions[0]
        assertEquals(mostPopularRegion.name, LoRRegion.IONIA.prettyName())
        assertEquals(mostPopularRegion.count, 4)
    }

    private fun videos(channel: Channel): List<LoRVideo> {
        return listOf(
            newLoRVideo(channel, setOf(ELISE), setOf(LoRRegion.NOXUS, LoRRegion.SHADOW_ILES), 1),
            newLoRVideo(
                channel,
                setOf(LEE_SIN, RIVEN),
                setOf(LoRRegion.NOXUS, LoRRegion.IONIA),
                2
            ),
            newLoRVideo(
                channel,
                setOf(YASUO, SWAIN),
                setOf(LoRRegion.NOXUS, LoRRegion.IONIA),
                3
            ),
            newLoRVideo(channel, setOf(ZED), setOf(LoRRegion.DEMACIA, LoRRegion.IONIA), 4),
            newLoRVideo(
                channel,
                setOf(SIVIR, RENEKTON),
                setOf(LoRRegion.DEMACIA, LoRRegion.SHURIMA),
                5
            ),
            newLoRVideo(
                channel,
                setOf(KINDRED, ASHE),
                setOf(LoRRegion.FRELJORD, LoRRegion.SHADOW_ILES),
                6
            ),
            newLoRVideo(
                channel,
                setOf(EZREAL),
                setOf(LoRRegion.PILTOVER_AND_ZAUN, LoRRegion.SHURIMA),
                7
            ),
            newLoRVideo(
                channel,
                setOf(YASUO, MALPHITE),
                setOf(LoRRegion.IONIA, LoRRegion.MOUNT_TARGON),
                8
            )
        )
    }

    private fun champions(): List<Champion> {
        return listOf(
            ELISE,
            LEE_SIN,
            RIVEN,
            YASUO,
            SWAIN,
            ZED,
            SIVIR,
            RENEKTON,
            KINDRED,
            ASHE,
            EZREAL,
            MALPHITE
        )
    }
}