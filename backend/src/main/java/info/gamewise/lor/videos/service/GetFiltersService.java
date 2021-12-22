package info.gamewise.lor.videos.service;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.LoRVideoFilter;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoChannel;
import info.gamewise.lor.videos.domain.VideoRegion;
import info.gamewise.lor.videos.port.in.GetFiltersUseCase;
import info.gamewise.lor.videos.port.out.GetChannelsPort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

@Service
class GetFiltersService implements GetFiltersUseCase {

    private final GetChannelsPort getChannelsPort;

    GetFiltersService(GetChannelsPort getChannelsPort) {
        this.getChannelsPort = getChannelsPort;
    }

    @Override
    public LoRVideoFilter getFilters() {
        return new LoRVideoFilter(allRegions(), allChannels(), allChampions());
    }

    private List<VideoChampion> allChampions() {
        return EnumSet.allOf(LoRChampion.class)
                .stream()
                .map(VideoChampion::new)
                .sorted(Comparator.comparing(VideoChampion::getName))
                .toList();
    }

    private List<VideoRegion> allRegions() {
        return EnumSet.allOf(LoRRegion.class)
                .stream()
                .map(VideoRegion::new).toList();
    }

    private List<VideoChannel> allChannels() {
        return getChannelsPort.getChannels()
                .stream()
                .map(VideoChannel::new).toList();
    }

}
