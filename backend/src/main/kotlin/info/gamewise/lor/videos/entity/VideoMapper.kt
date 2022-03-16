package info.gamewise.lor.videos.entity

import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.domain.LoRVideo
import info.gamewise.lor.videos.domain.VideoChampion
import info.gamewise.lor.videos.domain.VideoChannel
import info.gamewise.lor.videos.domain.VideoRegion
import info.gamewise.lor.videos.port.out.GetChampionsPort
import info.gamewise.lor.videos.port.out.GetChannelsPort
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
internal class VideoMapper(
    private val getChannelsPort: GetChannelsPort,
    private val getChampionsPort: GetChampionsPort
) {

    fun toDomainPage(page: Page<VideoJpaEntity>, pageable: Pageable?): Page<LoRVideo> {
        val videos = page.content.map { entity: VideoJpaEntity -> toDomain(entity) }
        return PageImpl(videos, pageable!!, page.totalElements)
    }

    fun toDomain(entity: VideoJpaEntity): LoRVideo {
        return LoRVideo(
            url = entity.url!!,
            title = entity.title!!,
            deckCode = entity.deckCode!!,
            channel = mapChannel(entity.channel!!)!!,
            champions = mapChampions(entity.champions!!),
            regions = domainRegions(entity.regions),
            publishedAt = entity.publishedAt!!,
            thumbnail = entity.thumbnails!!.high
        )
    }

    private fun mapChampions(champions: Set<String>): Set<VideoChampion> {
        return champions
            .mapNotNull { getChampionsPort.getChampionByName(it) }
            .map { VideoChampion(it) }
            .toSet()
    }

    private fun domainRegions(regions: Set<LoRRegion>?): Set<VideoRegion> {
        val domainRegions = regions!!
            .stream()
            .map { region: LoRRegion? -> VideoRegion(region!!) }
            .collect(Collectors.toUnmodifiableSet())
        return TreeSet(domainRegions)
    }

    private fun mapChannel(channelCode: String): VideoChannel? {
        val channel = getChannelsPort.getChannelByCode(channelCode)
        return if (channel != null) VideoChannel(channel) else null
    }
}