package info.gamewise.lor.videos.service

import com.github.brunosc.fetcher.domain.VideoDetails
import info.gamewise.lor.videos.DataLoader
import info.gamewise.lor.videos.DataLoader.DECK_CODE
import info.gamewise.lor.videos.DataLoader.videoDetails
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

private const val ID_NOT_IN_DB_123 = "123"
private const val ID_IN_DB_456 = "456"
private const val ID_NOT_IN_DB_789 = "789"
private val MEGA_MOGWAI = Channel("MEGA_MOGWAI", "MegaMogwai", "1")

@ExtendWith(MockitoExtension::class)
internal class VideosNotInDatabasePortServiceTest(@Mock private val videoIsInDatabaseUseCase: VideoIsInDatabaseUseCase,
                                                  @Mock private val latestVideosUseCase: LatestYouTubeVideosUseCase,
                                                  @Mock private val getChannelsPort: GetChannelsPort,
                                                  @Mock private val getChampionsPort: GetChampionsPort,
                                                  @Mock private val mapper: NewVideoMapperUseCase) {

    private val service =
        VideosNotInDatabasePortService(videoIsInDatabaseUseCase, latestVideosUseCase, mapper)

    @Test
    fun shouldFetchNormally() {
        givenChampions()
        givenVideos_ValidDeckCode()
        givenVideoIsInDatabase()

        val videos: List<NewVideo> = service.fetchNewVideos()

        val v123: NewVideo? = videos.firstOrNull { it.details.id == ID_NOT_IN_DB_123 }
        val v789: NewVideo? = videos.firstOrNull { it.details.id == ID_NOT_IN_DB_789 }

        assertNotNull(v123)
        assertNotNull(v789)
    }

    @Test
    fun shouldNotFetchInvalidDeckCode() {
        givenChampions()
        givenVideos_InvalidDeckCode()
        givenVideoIsInDatabase()

        val videos: List<NewVideo> = service.fetchNewVideos()

        val v123: NewVideo? = videos.firstOrNull { it.details.id == ID_NOT_IN_DB_123 }
        val v789: NewVideo? = videos.firstOrNull { it.details.id == ID_NOT_IN_DB_789 }

        assertNull(v123)
        assertNotNull(v789)
    }

    private fun givenVideos_ValidDeckCode() {
        val videoDetails: List<VideoDetails> = listOf(
            videoDetails(ID_NOT_IN_DB_123, DECK_CODE),
            videoDetails(ID_IN_DB_456, DECK_CODE),
            videoDetails(ID_NOT_IN_DB_789, DECK_CODE)
        )
        val latestVideos = mapOf(MEGA_MOGWAI to videoDetails)

        given(latestVideosUseCase.latestVideos())
            .willReturn(latestVideos)
        given(getChannelsPort.getChannels())
            .willReturn(listOf(MEGA_MOGWAI))
        given(mapper.mapNewVideos(latestVideos.entries.first()))
            .willReturn(videoDetails.map { NewVideo(DECK_CODE, it, MEGA_MOGWAI, emptySet(), emptySet()) })
    }

    private fun givenVideos_InvalidDeckCode() {
        val videoDetails: List<VideoDetails> = listOf(
            videoDetails(ID_NOT_IN_DB_123, "invalid-deck-code"),
            videoDetails(ID_IN_DB_456, DECK_CODE),
            videoDetails(ID_NOT_IN_DB_789, DECK_CODE)
        )
        val latestVideos = mapOf(MEGA_MOGWAI to videoDetails)

        given(latestVideosUseCase.latestVideos())
            .willReturn(latestVideos)
        given(getChannelsPort.getChannels())
            .willReturn(listOf(MEGA_MOGWAI))
        given(mapper.mapNewVideos(latestVideos.entries.first()))
            .willReturn(videoDetails.mapNotNull { if (it.id == ID_NOT_IN_DB_123) null else NewVideo(DECK_CODE, it, MEGA_MOGWAI, emptySet(), emptySet()) })
    }

    private fun givenVideoIsInDatabase() {
        given(videoIsInDatabaseUseCase.isInDatabase(ID_NOT_IN_DB_123))
            .willReturn(false)
        given(videoIsInDatabaseUseCase.isInDatabase(ID_IN_DB_456))
            .willReturn(true)
        given(videoIsInDatabaseUseCase.isInDatabase(ID_NOT_IN_DB_789))
            .willReturn(false)
    }

    private fun givenChampions() {
        given(getChampionsPort.getChampions())
            .willReturn(listOf(DataLoader.ELISE))
    }

}