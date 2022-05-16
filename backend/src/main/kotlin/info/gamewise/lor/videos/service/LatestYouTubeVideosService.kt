package info.gamewise.lor.videos.service

import com.github.brunosc.fetcher.YouTubeFetcher
import com.github.brunosc.fetcher.domain.VideoDetails
import com.github.brunosc.fetcher.domain.YouTubeFetcherParams
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.GetChannelsPort
import info.gamewise.lor.videos.port.out.LatestYouTubeVideosUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.security.GeneralSecurityException

private const val VIDEOS_BY_CHANNEL = 3L
private const val CLIENT_SECRETS = "/client_secret.json"

@Service
internal class LatestYouTubeVideosService(private val channelsPort: GetChannelsPort) : LatestYouTubeVideosUseCase {

    private val log = LoggerFactory.getLogger(LatestYouTubeVideosService::class.java)

    override fun latestVideos(): Map<Channel, List<VideoDetails>> {
        return channelsPort
            .getChannels()
            .associateWith { videosByChannel(it) }
    }

    private fun videosByChannel(channel: Channel): List<VideoDetails> {
        return try {
            val youTubeFetcher = buildYouTubeFetcher()
            youTubeFetcher.fetchByPlaylistId(channel.playlistId, VIDEOS_BY_CHANNEL)
        } catch (e: IOException) {
            log.error("There was an error to fetch the latest videos.", e)
            emptyList()
        } catch (e: GeneralSecurityException) {
            log.error("Security Exception fetching YouTube videos", e)
            emptyList()
        }
    }

    @Throws(GeneralSecurityException::class, IOException::class)
    private fun buildYouTubeFetcher(): YouTubeFetcher {
        val credentials = LatestYouTubeVideosService::class.java.getResourceAsStream(CLIENT_SECRETS)
        val params = YouTubeFetcherParams.Builder(credentials).build()
        return YouTubeFetcher(params)
    }

}