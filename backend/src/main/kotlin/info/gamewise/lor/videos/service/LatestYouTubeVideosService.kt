package info.gamewise.lor.videos.service

import com.github.brunosc.fetcher.YouTubeFetcher
import com.github.brunosc.fetcher.domain.VideoDetails
import com.github.brunosc.fetcher.domain.YouTubeFetcherParams
import info.gamewise.lor.videos.config.LocalServerProperties
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.LatestYouTubeVideosUseCase
import info.gamewise.lor.videos.service.LatestYouTubeVideosService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.security.GeneralSecurityException

@Service
internal class LatestYouTubeVideosService(private val localServerProperties: LocalServerProperties) :
    LatestYouTubeVideosUseCase {

    override fun latestVideosByChannel(channel: Channel): List<VideoDetails> {
        return try {
            val youTubeFetcher = buildYouTubeFetcher(localServerProperties)
            youTubeFetcher.fetchByPlaylistId(channel.playlistId, VIDEOS_BY_CHANNEL)
        } catch (e: IOException) {
            LOG.error("There was an error to fetch the latest videos.", e)
            emptyList()
        } catch (e: GeneralSecurityException) {
            LOG.error("Security Exception fetching YouTube videos", e)
            emptyList()
        }
    }

    @Throws(GeneralSecurityException::class, IOException::class)
    private fun buildYouTubeFetcher(localServerProperties: LocalServerProperties): YouTubeFetcher {
        val credentials = LatestYouTubeVideosService::class.java.getResourceAsStream(CLIENT_SECRETS)
        val params = YouTubeFetcherParams.Builder(credentials)
            .withHost(localServerProperties.host)
            .withPort(localServerProperties.port.toInt()) // TODO refactor
            .build()
        return YouTubeFetcher(params)
    }

    companion object {
        private const val VIDEOS_BY_CHANNEL = 3L
        private const val CLIENT_SECRETS = "/client_secret.json"
        private val LOG = LoggerFactory.getLogger(LatestYouTubeVideosService::class.java)
    }
}