package info.gamewise.lor.videos.service

import com.github.brunosc.fetcher.domain.VideoDetails
import info.gamewise.lor.videos.DataLoader
import info.gamewise.lor.videos.DataLoader.videoDetails
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

private const val ID_NOT_IN_DB_123 = "123"
private const val ID_IN_DB_456 = "456"
private const val ID_NOT_IN_DB_789 = "789"
private val MEGA_MOGWAI = Channel("MEGA_MOGWAI", "MegaMogwai", "1")

internal class VideosNotInDatabasePortServiceTest {

    private val videoIsInDatabaseUseCase: VideoIsInDatabaseUseCase = mock(VideoIsInDatabaseUseCase::class.java)
    private val latestVideosUseCase: LatestYouTubeVideosUseCase = mock(LatestYouTubeVideosUseCase::class.java)
    private val getChannelsPort: GetChannelsPort = mock(GetChannelsPort::class.java)
    private val getChampionsPort: GetChampionsPort = mock(GetChampionsPort::class.java)

    private val service =
        VideosNotInDatabasePortService(videoIsInDatabaseUseCase, latestVideosUseCase, getChannelsPort, getChampionsPort)

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
        val deckCode = "CICACAQDAMBQCBJHGU4AGAYFAMCAMBABAMBA6KBXAMAQCAZFAEBAGBABAMCQEAIBAEBS4"
        val latestVideos: List<VideoDetails> = listOf(
            videoDetails(ID_NOT_IN_DB_123, deckCode),
            videoDetails(ID_IN_DB_456, deckCode),
            videoDetails(ID_NOT_IN_DB_789, deckCode)
        )
        given(latestVideosUseCase.latestVideosByChannel(MEGA_MOGWAI))
            .willReturn(latestVideos)

        given(getChannelsPort.getChannels())
            .willReturn(listOf(MEGA_MOGWAI))
    }

    private fun givenVideos_InvalidDeckCode() {
        val deckCode = "CICACAQDAMBQCBJHGU4AGAYFAMCAMBABAMBA6KBXAMAQCAZFAEBAGBABAMCQEAIBAEBS4"
        val latestVideos: List<VideoDetails> = listOf(
            videoDetails(ID_NOT_IN_DB_123, "invalid-deck-code"),
            videoDetails(ID_IN_DB_456, deckCode),
            videoDetails(ID_NOT_IN_DB_789, deckCode)
        )

        given(latestVideosUseCase.latestVideosByChannel(MEGA_MOGWAI))
            .willReturn(latestVideos)
        given(getChannelsPort.getChannels())
            .willReturn(listOf(MEGA_MOGWAI))
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