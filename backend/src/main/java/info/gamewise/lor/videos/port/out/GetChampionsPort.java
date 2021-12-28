package info.gamewise.lor.videos.port.out;

import info.gamewise.lor.videos.domain.json.Champion;

import java.util.List;
import java.util.Optional;

public interface GetChampionsPort {
    List<Champion> getChampions();
    Optional<Champion> getChampionByName(String champion);
}