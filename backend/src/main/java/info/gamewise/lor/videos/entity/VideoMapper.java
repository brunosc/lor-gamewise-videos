package info.gamewise.lor.videos.entity;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoChannel;
import info.gamewise.lor.videos.domain.VideoRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Component
class VideoMapper {

    Page<LoRVideo> toDomainPage(Page<VideoJpaEntity> page, Pageable pageable) {
        final var videos = page.getContent()
                .stream()
                .map(this::mapVideo)
                .collect(toUnmodifiableList());

        return new PageImpl<>(videos, pageable, page.getTotalElements());
    }

    private LoRVideo mapVideo(VideoJpaEntity entity) {
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

    private Set<VideoChampion> mapChampions(Set<LoRChampion> champions) {
        return champions
                .stream()
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

    private VideoChannel mapChannel(Channel channel) {
        return new VideoChannel(channel);
    }

}
