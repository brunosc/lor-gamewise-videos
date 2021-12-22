package info.gamewise.lor.videos.port.out;

import com.github.brunosc.fetcher.domain.VideoDetails;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.LoRChannel;

import java.util.List;

public interface LatestYouTubeVideosUseCase {
    List<VideoDetails> latestVideosByChannel(LoRChannel channel);
}
