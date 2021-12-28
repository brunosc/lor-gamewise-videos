package info.gamewise.lor.videos.port.out;

import com.github.brunosc.fetcher.domain.VideoDetails;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.json.Channel;

import java.util.List;
import java.util.Set;

public interface VideosNotInDatabasePort {
    List<NewVideo> fetchNewVideos();

    record NewVideo(String deckCode, VideoDetails details,
                    Channel channel,
                    Set<LoRRegion> regions,
                    Set<LoRChampion> champions) {
    }
}
