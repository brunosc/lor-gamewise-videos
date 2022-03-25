package info.gamewise.lor.videos.controller

import info.gamewise.lor.videos.domain.ChannelStatistics
import info.gamewise.lor.videos.domain.LoRVideo
import info.gamewise.lor.videos.domain.LoRVideoFilter
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.`in`.*
import info.gamewise.lor.videos.port.out.ClearCacheUseCase
import info.gamewise.lor.videos.port.out.GetChampionsPort
import info.gamewise.lor.videos.port.out.GetChannelsPort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
internal class ApiController(
    private val videosUseCase: GetVideosUseCase,
    private val filtersUseCase: GetFiltersUseCase,
    private val appSettingsUseCase: GetAppSettingsUseCase,
    private val channelStatisticsUseCase: GetChannelStatisticsUseCase,
    private val getChannelsPort: GetChannelsPort,
    private val getChampionsPort: GetChampionsPort,
    private val clearCacheUseCase: ClearCacheUseCase
) {

    @GetMapping("filters")
    fun getFilters(): ResponseEntity<LoRVideoFilter> {
        return ResponseEntity.ok(filtersUseCase.getFilters())
    }

    @GetMapping("videos")
    fun getVideos(params: SearchParams, pageable: Pageable): ResponseEntity<Page<LoRVideo>> {
        return ResponseEntity.ok(videosUseCase.fetchByFilter(params, pageable))
    }

    @GetMapping("settings")
    fun getAppSettings(): ResponseEntity<AppSettings> {
        return ResponseEntity.ok(appSettingsUseCase.getAppSettings())
    }

    @GetMapping("/channel/{channel}/statistics")
    fun getChannelStatistics(@PathVariable channel: String): ResponseEntity<ChannelStatistics> {
        return ResponseEntity.ok(channelStatisticsUseCase.channelStatistics(channel))
    }

    @GetMapping("channels")
    fun getChannels(): ResponseEntity<List<Channel>> {
        return ResponseEntity.ok(getChannelsPort.getChannels())
    }

    @GetMapping("champions")
    fun getChampions(): ResponseEntity<List<Champion>> {
        return ResponseEntity.ok(getChampionsPort.getChampions())
    }

    @GetMapping("clear-cache")
    fun clearCache(): ResponseEntity<Void> {
        clearCacheUseCase.clearCache()
        return ResponseEntity.noContent().build()
    }

}