package info.gamewise.lor.videos.entity;

import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoChannel;
import info.gamewise.lor.videos.domain.VideoRegion;
import info.gamewise.lor.videos.port.out.GetChampionsPort;
import info.gamewise.lor.videos.port.out.GetChannelsPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toUnmodifiableSet;

@Component
class VideoMapper {

    private final GetChannelsPort getChannelsPort;
    private final GetChampionsPort getChampionsPort;

    VideoMapper(GetChannelsPort getChannelsPort, GetChampionsPort getChampionsPort) {
        this.getChannelsPort = getChannelsPort;
        this.getChampionsPort = getChampionsPort;
    }

    Page<LoRVideo> toDomainPage(Page<VideoJpaEntity> page, Pageable pageable) {
        final var videos = page.getContent()
                .stream()
                .map(this::toDomain).toList();

        return new PageImpl<>(videos, pageable, page.getTotalElements());
    }

    LoRVideo toDomain(VideoJpaEntity entity) {
        return new LoRVideo(
                entity.getUrl(),
                entity.getTitle(),
                entity.getDeckCode(),
                mapChannel(entity.getChannel()),
                mapChampions(entity.getChampions()),
                domainRegions(entity.getRegions()),
                entity.getPublishedAt(),
                entity.getThumbnails().getHigh()
        );
    }

    private Set<VideoChampion> mapChampions(Set<String> champions) {
        return champions
                .stream()
                .map(champion -> getChampionsPort.getChampionByName(champion).orElse(null))
                .filter(Objects::nonNull)
                .map(VideoChampion::new)
                .collect(toUnmodifiableSet());
    }

    private Set<VideoRegion> domainRegions(Set<LoRRegion> regions) {
        Set<VideoRegion> domainRegions = regions
                .stream()
                .map(VideoRegion::new)
                .collect(toUnmodifiableSet());

        return new TreeSet<>(domainRegions);
    }

    private VideoChannel mapChannel(String channel) {
        return getChannelsPort.getChannelByCode(channel)
                .map(VideoChannel::new)
                .orElse(null);
    }

}
