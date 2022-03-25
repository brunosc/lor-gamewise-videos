package info.gamewise.lor.videos.service

import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.domain.ChannelStatistics
import info.gamewise.lor.videos.domain.LoRVideo
import info.gamewise.lor.videos.domain.NameCount
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.port.`in`.GetChannelStatisticsUseCase
import info.gamewise.lor.videos.port.out.GetChampionsPort
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.function.Predicate

@Service
internal class ChannelStatisticsService(
    private val getVideosByChannelPort: GetVideosByChannelPort,
    private val getChampionsPort: GetChampionsPort
) : GetChannelStatisticsUseCase {

    override fun channelStatistics(channel: String): ChannelStatistics {
        val videos: List<LoRVideo> = getVideosByChannelPort.videosByChannel(channel)

        val startedAt: LocalDateTime = videos.minOf { it.publishedAt }

        val championsCount = champions()
            .map { NameCount(it.name, countVideosByChampion(videos, it)) }
            .sortedBy { it.name }
            .sortedBy { it.count }
            .reversed()

        val regionsCount = regions()
            .map { NameCount(it.prettyName(), countVideosByRegion(videos, it)) }
            .sortedBy { it.name }
            .sortedBy { it.count }
            .reversed()

        return ChannelStatistics(startedAt.toLocalDate(), championsCount, regionsCount)
    }

    private fun countVideosByChampion(videos: List<LoRVideo>, champion: Champion): Int {
        val predicate = Predicate<LoRVideo> {
            it.champions
                .any { videoChampion -> videoChampion.code == champion.code }
        }
        return countVideos(videos, predicate)
    }

    private fun countVideosByRegion(videos: List<LoRVideo>, region: LoRRegion): Int {
        val predicate = Predicate<LoRVideo> {
            it.regions
                .any { videoRegion -> videoRegion.code == region.code }
        }
        return countVideos(videos, predicate)
    }

    private fun countVideos(videos: List<LoRVideo>, predicate: Predicate<LoRVideo>): Int {
        return videos.count { predicate.test(it) }
    }

    private fun champions(): List<Champion> {
        return getChampionsPort.getChampions()
    }

    private fun regions(): List<LoRRegion> {
        return enumValues<LoRRegion>().toList()
    }
}