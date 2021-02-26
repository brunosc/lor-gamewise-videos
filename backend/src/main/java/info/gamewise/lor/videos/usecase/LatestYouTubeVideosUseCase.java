package info.gamewise.lor.videos.usecase;

import com.github.brunosc.fetcher.domain.VideoDetails;
import info.gamewise.lor.videos.domain.Channel;

import java.util.List;

public interface LatestYouTubeVideosUseCase {

    List<VideoDetails> latestVideosByChannel(Channel channel);
}
