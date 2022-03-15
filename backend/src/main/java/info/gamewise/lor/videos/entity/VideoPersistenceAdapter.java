package info.gamewise.lor.videos.entity;

import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.port.in.GetVideosUseCase;
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort;
import info.gamewise.lor.videos.port.out.SaveVideoUseCase;
import info.gamewise.lor.videos.port.out.VideoIsInDatabaseUseCase;
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort;
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort.NewVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class VideoPersistenceAdapter implements GetVideosUseCase, VideoIsInDatabaseUseCase, SaveVideoUseCase, GetVideosByChannelPort {

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
    public void save(List<NewVideo> newVideos) {
        final var videoEntities = newVideos.stream().map(VideoJpaEntity::new).toList();
        repository.saveAll(videoEntities);
    }

    @Override
    public List<LoRVideo> videosByChannel(String channel) {
        return repository.findByChannel(channel)
                .stream()
                .map(mapper::toDomain).toList();
    }
}
