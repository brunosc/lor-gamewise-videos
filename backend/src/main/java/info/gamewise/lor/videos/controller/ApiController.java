package info.gamewise.lor.videos.controller;

import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.domain.LoRVideoFilter;
import info.gamewise.lor.videos.usecase.GetFiltersUseCase;
import info.gamewise.lor.videos.usecase.GetVideosUseCase;
import info.gamewise.lor.videos.usecase.GetVideosUseCase.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
class ApiController {

    private final GetVideosUseCase videosUseCase;
    private final GetFiltersUseCase filtersUseCase;

    ApiController(GetVideosUseCase videosUseCase, GetFiltersUseCase filtersUseCase) {
        this.videosUseCase = videosUseCase;
        this.filtersUseCase = filtersUseCase;
    }

    @GetMapping("filters")
    ResponseEntity<LoRVideoFilter> getFilters() {
        return ResponseEntity.ok(filtersUseCase.getFilters());
    }

    @GetMapping("videos")
    ResponseEntity<Page<LoRVideo>> getVideos(SearchParams params, Pageable pageable) {
        return ResponseEntity.ok(videosUseCase.fetchByFilter(params, pageable));
    }
}
