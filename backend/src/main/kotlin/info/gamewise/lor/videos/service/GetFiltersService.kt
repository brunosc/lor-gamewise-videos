package info.gamewise.lor.videos.service

import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.domain.LoRVideoFilter
import info.gamewise.lor.videos.domain.VideoChampion
import info.gamewise.lor.videos.domain.VideoChannel
import info.gamewise.lor.videos.domain.VideoRegion
import info.gamewise.lor.videos.port.`in`.GetFiltersUseCase
import info.gamewise.lor.videos.port.out.GetChampionsPort
import info.gamewise.lor.videos.port.out.GetChannelsPort
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class GetFiltersService(
    private val getChannelsPort: GetChannelsPort,
    private val getChampionsPort: GetChampionsPort
) : GetFiltersUseCase {

    override fun getFilters(): LoRVideoFilter {
        return LoRVideoFilter(allRegions(), allChannels(), allChampions())
    }

    private fun allChampions(): List<VideoChampion> {
        return getChampionsPort.getChampions()
            .map { VideoChampion(it) }
    }

    private fun allRegions(): List<VideoRegion> {
        return EnumSet.allOf(LoRRegion::class.java)
            .map { VideoRegion(it) }
    }

    private fun allChannels(): List<VideoChannel> {
        return getChannelsPort.getChannels()
            .map { VideoChannel(it) }
    }
}