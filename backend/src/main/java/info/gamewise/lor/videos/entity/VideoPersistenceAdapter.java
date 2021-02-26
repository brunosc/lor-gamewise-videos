package info.gamewise.lor.videos.entity;

import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.usecase.GetVideosUseCase;
import info.gamewise.lor.videos.usecase.SaveVideoUseCase;
import info.gamewise.lor.videos.usecase.VideoIsInDatabaseUseCase;
import info.gamewise.lor.videos.usecase.VideosNotInDatabaseUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class VideoPersistenceAdapter implements GetVideosUseCase, VideoIsInDatabaseUseCase, SaveVideoUseCase {

    private final VideoRepository repository;
    private final VideoMapper mapper;

    VideoPersistenceAdapter(VideoRepository repository, VideoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<LoRVideo> fetchByFilter(SearchParams params, Pageable pageable) {
        final var predicate = new VideoPredicate(params);
        final var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), predicate.sort());
        final var page = repository.findAll(predicate.predicate(), pageRequest);
        return mapper.toDomainPage(page, pageable);
    }

    @Override
    public boolean isInDatabase(String videoId) {
        return repository.existsByVideoId(videoId);
    }

    @Override
    public void save(VideosNotInDatabaseUseCase.NewVideo newVideo) {
        repository.save(new VideoJpaEntity(newVideo));
    }
}
