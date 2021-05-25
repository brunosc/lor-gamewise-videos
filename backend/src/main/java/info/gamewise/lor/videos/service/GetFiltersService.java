package info.gamewise.lor.videos.service;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.LoRVideoFilter;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoChannel;
import info.gamewise.lor.videos.domain.VideoRegion;
import info.gamewise.lor.videos.port.in.GetFiltersUseCase;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
class GetFiltersService implements GetFiltersUseCase {

    @Override
    public LoRVideoFilter getFilters() {
        return new LoRVideoFilter(allRegions(), allChannels(), allChampions());
    }

    private List<VideoChampion> allChampions() {
        return EnumSet.allOf(LoRChampion.class)
                .stream()
                .map(VideoChampion::new)
                .sorted(Comparator.comparing(VideoChampion::getName))
                .collect(toUnmodifiableList());
    }

    private List<VideoRegion> allRegions() {
        return EnumSet.allOf(LoRRegion.class)
                .stream()
                .map(VideoRegion::new)
                .collect(toUnmodifiableList());
    }

    private List<VideoChannel> allChannels() {
        return EnumSet.allOf(Channel.class)
                .stream()
                .map(VideoChannel::new)
                .collect(toUnmodifiableList());
    }

}
