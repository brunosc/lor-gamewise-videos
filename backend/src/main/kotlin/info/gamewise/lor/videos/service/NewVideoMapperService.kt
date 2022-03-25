package info.gamewise.lor.videos.service

import com.github.brunosc.fetcher.domain.VideoDetails
import com.github.brunosc.lor.DeckCodeParser
import com.github.brunosc.lor.domain.LoRDeck
import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.DeckCodeExtractorUseCase
import info.gamewise.lor.videos.port.out.GetChampionsPort
import info.gamewise.lor.videos.port.out.NewVideo
import info.gamewise.lor.videos.port.out.NewVideoMapperUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NewVideoMapperService(private val championsPort: GetChampionsPort,
                            private val deckCodeExtractor: DeckCodeExtractorUseCase) : NewVideoMapperUseCase {

    private val log = LoggerFactory.getLogger(NewVideoMapperService::class.java)

    override fun mapNewVideos(channelVideos: Map.Entry<Channel, List<VideoDetails>>): List<NewVideo> {
        return channelVideos.value
            .mapNotNull { mapVideosByChannel(channelVideos.key, it) }
    }

    private fun mapVideosByChannel(channel: Channel, details: VideoDetails): NewVideo? {
        val deckCode: String? = deckCodeExtractor.extractDeckCode(details.description)
        if (deckCode == null) {
            log.error("There is no deck code for the video {} from Channel {}", details.title, channel)
            return null
        }

        return try {
            val deck = DeckCodeParser.decode(deckCode)
            val regions = extractRegions(deck)
            val champions = extractChampionsFromDeck(deck)
            NewVideo(deckCode, details, channel, regions, champions)
        } catch (e: Exception) {
            log.error("There was an error to map the deck: {}", e.message)
            null
        }
    }

    private fun extractChampionsFromDeck(deck: LoRDeck): Set<Champion> {
        return deck.cards.keys
            .mapNotNull { it.cardCode }
            .mapNotNull { mapChampion(it) }
            .toSet()
    }

    private fun mapChampion(cardCode: String): Champion? {
        return championsPort
            .getChampions()
            .firstOrNull{ cardCode == it.id }
    }

    private fun extractRegions(deck: LoRDeck): Set<LoRRegion> {
        return deck.cards.keys
            .map { it.region }
            .toSet()
    }

}