package info.gamewise.lor.videos.port.out

import com.github.brunosc.fetcher.domain.VideoDetails
import info.gamewise.lor.videos.domain.json.Channel

interface LatestYouTubeVideosUseCase {
    fun latestVideosByChannel(channel: Channel): List<VideoDetails>
}