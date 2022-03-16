package info.gamewise.lor.videos.service

import com.github.brunosc.fetcher.domain.VideoDetails
import com.github.brunosc.lor.DeckCodeParser
import com.github.brunosc.lor.domain.LoRCard
import com.github.brunosc.lor.domain.LoRDeck
import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.*
import info.gamewise.lor.videos.service.DeckCodeExtractorService.extractDeckCode
import info.gamewise.lor.videos.service.VideosNotInDatabasePortService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

@Service
internal class VideosNotInDatabasePortService(
    private val videoIsInDatabaseUseCase: VideoIsInDatabaseUseCase,
    private val latestVideosUseCase: LatestYouTubeVideosUseCase,
    private val getChannelsPort: GetChannelsPort,
    private val getChampionsPort: GetChampionsPort
) : VideosNotInDatabasePort {

    override fun fetchNewVideos(): List<NewVideo> {
        val latestVideos = latestVideos()
        return latestVideos
            .stream()
            .filter { video: NewVideo -> isNotInDatabase(video) }
            .toList()
    }

    private fun latestVideos(): List<NewVideo> {
        return channelsStream()
            .map { channel: Channel -> latestVideosByChannel(channel) }
            .toList()
            .stream()
            .flatMap<NewVideo> { obj: List<NewVideo?> -> obj.stream() }
            .toList()
    }

    private fun channelsStream(): Stream<Channel> {
        val channels = getChannelsPort.getChannels()
        return channels.stream()
    }

    private fun latestVideosByChannel(channel: Channel): List<NewVideo> {
        val latestVideos = latestVideosUseCase.latestVideosByChannel(channel)
        return latestVideos.mapNotNull { video: VideoDetails -> mapNewVideo(video, channel) }
    }

    private fun mapNewVideo(details: VideoDetails, channel: Channel): NewVideo? {
        val deckCode: String? = extractDeckCode(details.description)
        if (deckCode == null) {
            LOG.error("There is no deck code for the video {} from Channel {}", details.title, channel)
            return null
        }
        return try {
            val deck = DeckCodeParser.decode(deckCode)

            // TODO refactor if else
            val regions = if (deck == null)  emptySet() else extractFaction(deck)
            val champions = if (deck == null)  emptySet() else extractChampionsFromDeck(deck)
            NewVideo(deckCode, details, channel, regions, champions)
        } catch (e: Exception) {
            LOG.error("There was an error to map the deck: {}", e.message)
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
        return getChampionsPort.getChampions().firstOrNull{ cardCode == it.id }
    }

    private fun isNotInDatabase(video: NewVideo): Boolean {
        return !videoIsInDatabaseUseCase.isInDatabase(video.details.id)
    }

    private fun extractFaction(deck: LoRDeck): Set<LoRRegion> {
        return deck.cards.keys
            .stream()
            .map { obj: LoRCard -> obj.region }
            .collect(Collectors.toUnmodifiableSet())
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(VideosNotInDatabasePortService::class.java)
    }
}