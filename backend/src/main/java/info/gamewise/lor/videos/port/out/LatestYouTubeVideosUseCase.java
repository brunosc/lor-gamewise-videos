package info.gamewise.lor.videos.port.out;

import com.github.brunosc.fetcher.domain.VideoDetails;
import info.gamewise.lor.videos.domain.json.Channel;

import java.util.List;

public interface LatestYouTubeVideosUseCase {
    List<VideoDetails> latestVideosByChannel(Channel channel);
}
