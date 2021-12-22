package info.gamewise.lor.videos.port.out;

import info.gamewise.lor.videos.domain.ChampionRecord;

import java.util.List;

public interface GetChampionsPort {
    List<ChampionRecord> getChampions();
}
