package info.gamewise.lor.videos.controller;

import info.gamewise.lor.videos.domain.AppSettings;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.ChannelStatistics;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.domain.LoRVideoFilter;
import info.gamewise.lor.videos.port.in.GetAppSettingsUseCase;
import info.gamewise.lor.videos.port.in.GetChannelStatisticsUseCase;
import info.gamewise.lor.videos.port.in.GetFiltersUseCase;
import info.gamewise.lor.videos.port.in.GetVideosUseCase;
import info.gamewise.lor.videos.port.in.GetVideosUseCase.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
class ApiController {

    private final GetVideosUseCase videosUseCase;
    private final GetFiltersUseCase filtersUseCase;
    private final GetAppSettingsUseCase appSettingsUseCase;
    private final GetChannelStatisticsUseCase channelStatisticsUseCase;

    ApiController(GetVideosUseCase videosUseCase, GetFiltersUseCase filtersUseCase, GetAppSettingsUseCase appSettingsUseCase, GetChannelStatisticsUseCase channelStatisticsUseCase) {
        this.videosUseCase = videosUseCase;
        this.filtersUseCase = filtersUseCase;
        this.appSettingsUseCase = appSettingsUseCase;
        this.channelStatisticsUseCase = channelStatisticsUseCase;
    }

    @GetMapping("filters")
    ResponseEntity<LoRVideoFilter> getFilters() {
        return ResponseEntity.ok(filtersUseCase.getFilters());
    }

    @GetMapping("videos")
    ResponseEntity<Page<LoRVideo>> getVideos(SearchParams params, Pageable pageable) {
        return ResponseEntity.ok(videosUseCase.fetchByFilter(params, pageable));
    }

    @GetMapping("settings")
    ResponseEntity<AppSettings> getAppSettings() {
        return ResponseEntity.ok(appSettingsUseCase.getAppSettings());
    }

    @GetMapping("/channel/{channel}/statistics")
    ResponseEntity<ChannelStatistics> getChannelStatistics(@PathVariable Channel channel) {
        return ResponseEntity.ok(channelStatisticsUseCase.channelStatistics(channel));
    }
}
