package info.gamewise.lor.videos.port.out;

import info.gamewise.lor.videos.domain.ChampionRecord;

import java.util.List;
import java.util.Optional;

public interface GetChampionsPort {
    List<ChampionRecord> getChampions();
    Optional<ChampionRecord> getChampionByName(String champion);
}
