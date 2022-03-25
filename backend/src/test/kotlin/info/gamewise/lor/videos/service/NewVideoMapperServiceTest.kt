package info.gamewise.lor.videos.service

import com.github.brunosc.lor.domain.LoRRegion
import com.github.brunosc.lor.domain.LoRRegion.*
import info.gamewise.lor.videos.DataLoader.ELISE
import info.gamewise.lor.videos.DataLoader.NAMI
import info.gamewise.lor.videos.DataLoader.NASUS
import info.gamewise.lor.videos.DataLoader.SILVERFUSE
import info.gamewise.lor.videos.DataLoader.TEEMO
import info.gamewise.lor.videos.DataLoader.THRESH
import info.gamewise.lor.videos.DataLoader.TWISTED_FATE
import info.gamewise.lor.videos.DataLoader.videoDetails
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.port.out.DeckCodeExtractorUseCase
import info.gamewise.lor.videos.port.out.GetChampionsPort
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.argThat
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
internal class NewVideoMapperServiceTest(@Mock val championsPort: GetChampionsPort,
                                         @Mock val deckCodeExtractor: DeckCodeExtractorUseCase) {

    private val service = NewVideoMapperService(championsPort, deckCodeExtractor)
    private val deckCodes = listOf(
        "CECQCAQEBIAQGBAUAIAQKKBVAIBQKBQQAQAQICBHGQ5AGAIBAQYQCAYEBUBACBIBFUBACAIEDMAQCBJR",
        "CMBQCBAFAMBAIBZPGYDACBILEIUDAMJUAQAQCBIPAEBAKBABAQDQEAIEAUIAGAIEAUCAEAIFCMMQEBAHHN4Q",
        "CICACBIGAUBAGBQHCEBQGCJDGPLQCBACAYERQGRLAMAQIBQOAECQMCYCAMEWFXIBAIAQEBRKAEBQSEY"
    )
    private val decks = listOf(
        Decks(deckCodes[0], setOf(TEEMO, ELISE), setOf(PILTOVER_AND_ZAUN, SHADOW_ILES)),
        Decks(deckCodes[1], setOf(NASUS, THRESH), setOf(SHURIMA, SHADOW_ILES)),
        Decks(deckCodes[2], setOf(NAMI, TWISTED_FATE), setOf(MOUNT_TARGON, BILGEWATER)),
    )

    data class Decks(val deckCode: String, val champions: Set<Champion>, val regions: Set<LoRRegion>)

    @Test
    fun shouldMapNewVideos() {
        // given
        val videoDetails = (1..3).map { videoDetails(it.toString(), deckCodes[it - 1]) }
        val channelVideos = mapOf(SILVERFUSE to videoDetails)
        givenExtractDeckCode()
        givenChampions()

        // when
        val newVideos = service.mapNewVideos(channelVideos.entries.first())

        // then
        assertEquals(3, newVideos.size)

        val softAssertions = SoftAssertions()

        (0..2).forEach {
            val newVideo = newVideos[it]
            val deck = decks[it]
            softAssertions.assertThat(newVideo.channel).isEqualTo(SILVERFUSE)
            softAssertions.assertThat(newVideo.deckCode).isEqualTo(deck.deckCode)
            softAssertions.assertThat(newVideo.regions).isEqualTo(deck.regions)
            softAssertions.assertThat(newVideo.champions).isEqualTo(deck.champions)
        }

        softAssertions.assertAll()
    }

    private fun givenExtractDeckCode() {
        (0..2)
            .forEach { `when`(deckCodeExtractor.extractDeckCode(argThat { arg -> arg == deckCodes[it] }))
                .thenReturn(deckCodes[it]) }
    }

    private fun givenChampions() {
        given(championsPort.getChampions())
            .willReturn(listOf(TEEMO, ELISE, NASUS, THRESH, NAMI, TWISTED_FATE))
    }

}