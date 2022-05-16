package info.gamewise.lor.videos.service

import info.gamewise.lor.videos.port.out.*
import org.springframework.stereotype.Service

@Service
internal class VideosNotInDatabasePortService(
    private val videoIsInDatabaseUseCase: VideoIsInDatabaseUseCase,
    private val latestVideosUseCase: LatestYouTubeVideosUseCase,
    private val newVideosMapper: NewVideoMapperUseCase
) : VideosNotInDatabasePort {

    override fun fetchNewVideos(): List<NewVideo> {
        return latestNewVideos()
            .filter { isNotInDatabase(it) }
    }

    private fun isNotInDatabase(video: NewVideo): Boolean {
        return !videoIsInDatabaseUseCase.isInDatabase(video.details.id)
    }

    private fun latestNewVideos(): List<NewVideo> {
        return latestVideosUseCase.latestVideos()
            .map { newVideosMapper.mapNewVideos(it) }
            .flatten()
    }

}