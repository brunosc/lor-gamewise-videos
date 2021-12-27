package info.gamewise.lor.videos.service;

import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.ChampionRecord;
import info.gamewise.lor.videos.domain.ChannelStatistics;
import info.gamewise.lor.videos.domain.ChannelStatistics.NameCount;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.port.in.GetChannelStatisticsUseCase;
import info.gamewise.lor.videos.port.out.GetChampionsPort;
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

@Service
class ChannelStatisticsService implements GetChannelStatisticsUseCase {

    private final GetVideosByChannelPort getVideosByChannelPort;
    private final GetChampionsPort getChampionsPort;

    ChannelStatisticsService(GetVideosByChannelPort getVideosByChannelPort, GetChampionsPort getChampionsPort) {
        this.getVideosByChannelPort = getVideosByChannelPort;
        this.getChampionsPort = getChampionsPort;
    }

    @Override
    public ChannelStatistics channelStatistics(String channel) {
        List<LoRVideo> videos = getVideosByChannelPort.videosByChannel(channel);

        LocalDateTime startedAt = videos.stream()
                .map(LoRVideo::publishedAt)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        List<NameCount> championsCount = champions()
                .stream()
                .map(champion -> new NameCount(champion.name(), countVideosByChampion(videos, champion)))
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

    private long countVideosByChampion(List<LoRVideo> videos, ChampionRecord champion) {
        Predicate<LoRVideo> predicate = video -> video.champions()
                .stream()
                .anyMatch(videoChampion -> videoChampion.getCode().equals(champion.code()));

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

    private List<ChampionRecord> champions() {
        return getChampionsPort.getChampions();
    }

    private List<LoRRegion> regions() {
        return EnumSet.allOf(LoRRegion.class)
                .stream()
                .toList();
    }
}
