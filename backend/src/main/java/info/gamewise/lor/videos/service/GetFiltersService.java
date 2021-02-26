package info.gamewise.lor.videos.service;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.LoRVideoFilter;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoRegion;
import info.gamewise.lor.videos.usecase.GetFiltersUseCase;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
class GetFiltersService implements GetFiltersUseCase {

    @Override
    public LoRVideoFilter getFilters() {
        return new LoRVideoFilter(allChampions(), allRegions());
    }

    private List<VideoChampion> allChampions() {
        return EnumSet.allOf(LoRChampion.class)
                .stream()
                .map(this::mapChampion)
                .sorted(Comparator.comparing(VideoChampion::getName))
                .collect(toUnmodifiableList());
    }

    private List<VideoRegion> allRegions() {
        return EnumSet.allOf(LoRRegion.class)
                .stream()
                .map(this::mapRegion)
                .collect(toUnmodifiableList());
    }

    private VideoChampion mapChampion(LoRChampion champion) {
        return new VideoChampion(champion.getId(), champion.prettyName());
    }

    private VideoRegion mapRegion(LoRRegion region) {
        return new VideoRegion(region.getCode(), region.prettyName());
    }

}
