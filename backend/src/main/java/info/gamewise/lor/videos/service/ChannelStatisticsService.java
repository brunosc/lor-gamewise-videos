package info.gamewise.lor.videos.service;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.ChannelStatistics;
import info.gamewise.lor.videos.domain.ChannelStatistics.NameCount;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.port.in.GetChannelStatisticsUseCase;
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
class ChannelStatisticsService implements GetChannelStatisticsUseCase {

    private final GetVideosByChannelPort port;

    ChannelStatisticsService(GetVideosByChannelPort port) {
        this.port = port;
    }

    @Override
    public ChannelStatistics channelStatistics(String channel) {
        List<LoRVideo> videos = port.videosByChannel(channel);

        LocalDateTime startedAt = videos.stream()
                .map(LoRVideo::publishedAt)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        List<NameCount> championsCount = champions()
                .stream()
                .map(champion -> new NameCount(champion.prettyName(), countVideosByChampion(videos, champion)))
                .sorted(Comparator.comparing(NameCount::name))
                .sorted(Comparator.comparing(NameCount::count).reversed())
                .toList();

        List<NameCount> regionsCount = regions()
                .stream()
                .map(region -> new NameCount(region.prettyName(), countVideosByRegion(videos, region)))
                .sorted(Comparator.comparing(NameCount::name))
                .sorted(Comparator.comparing(NameCount::count).reversed())
                .toList();

        return new ChannelStatistics(startedAt.toLocalDate(), championsCount, regionsCount);
    }

    private long countVideosByChampion(List<LoRVideo> videos, LoRChampion champion) {
        Predicate<LoRVideo> predicate = video -> video.champions()
                .stream()
                .anyMatch(videoChampion -> videoChampion.getCode().equals(champion.getId()));

        return countVideos(videos, predicate);
    }

    private long countVideosByRegion(List<LoRVideo> videos, LoRRegion region) {
        Predicate<LoRVideo> predicate = video -> video.regions()
                .stream()
                .anyMatch(videoRegion -> videoRegion.getCode().equals(region.getCode()));

        return countVideos(videos, predicate);
    }

    private long countVideos(List<LoRVideo> videos, Predicate<LoRVideo> predicate) {
        return videos.stream().filter(predicate).count();
    }

    private List<LoRChampion> champions() {
        return EnumSet.allOf(LoRChampion.class)
                .stream()
                .toList();
    }

    private List<LoRRegion> regions() {
        return EnumSet.allOf(LoRRegion.class)
                .stream()
                .toList();
    }
}
