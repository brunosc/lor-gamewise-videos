package info.gamewise.lor.videos.service;

import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.LoRVideoFilter;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoChannel;
import info.gamewise.lor.videos.domain.VideoRegion;
import info.gamewise.lor.videos.port.in.GetFiltersUseCase;
import info.gamewise.lor.videos.port.out.GetChampionsPort;
import info.gamewise.lor.videos.port.out.GetChannelsPort;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;

@Service
class GetFiltersService implements GetFiltersUseCase {

    private final GetChannelsPort getChannelsPort;
    private final GetChampionsPort getChampionsPort;

    GetFiltersService(GetChannelsPort getChannelsPort, GetChampionsPort getChampionsPort) {
        this.getChannelsPort = getChannelsPort;
        this.getChampionsPort = getChampionsPort;
    }

    @Override
    public LoRVideoFilter getFilters() {
        return new LoRVideoFilter(allRegions(), allChannels(), allChampions());
    }

    private List<VideoChampion> allChampions() {
        return getChampionsPort.getChampions()
                .stream()
                .map(VideoChampion::new)
                .toList();
    }

    private List<VideoRegion> allRegions() {
        return EnumSet.allOf(LoRRegion.class)
                .stream()
                .map(VideoRegion::new)
                .toList();
    }

    private List<VideoChannel> allChannels() {
        return getChannelsPort.getChannels()
                .stream()
                .map(VideoChannel::new)
                .toList();
    }

}
