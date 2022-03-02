package info.gamewise.lor.videos.service;

import com.github.brunosc.fetcher.domain.VideoDetails;
import com.github.brunosc.lor.DeckCodeParser;
import com.github.brunosc.lor.domain.LoRCard;
import com.github.brunosc.lor.domain.LoRDeck;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.json.Champion;
import info.gamewise.lor.videos.domain.json.Channel;
import info.gamewise.lor.videos.port.out.GetChampionsPort;
import info.gamewise.lor.videos.port.out.GetChannelsPort;
import info.gamewise.lor.videos.port.out.LatestYouTubeVideosUseCase;
import info.gamewise.lor.videos.port.out.VideoIsInDatabaseUseCase;
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Service
class VideosNotInDatabasePortService implements VideosNotInDatabasePort {

    private static final Logger LOG = LoggerFactory.getLogger(VideosNotInDatabasePortService.class);

    private final VideoIsInDatabaseUseCase videoIsInDatabaseUseCase;
    private final LatestYouTubeVideosUseCase latestVideosUseCase;
    private final GetChannelsPort getChannelsPort;
    private final GetChampionsPort getChampionsPort;

    VideosNotInDatabasePortService(VideoIsInDatabaseUseCase videoIsInDatabaseUseCase, LatestYouTubeVideosUseCase latestVideosUseCase, GetChannelsPort getChannelsPort, GetChampionsPort getChampionsPort) {
        this.videoIsInDatabaseUseCase = videoIsInDatabaseUseCase;
        this.latestVideosUseCase = latestVideosUseCase;
        this.getChannelsPort = getChannelsPort;
        this.getChampionsPort = getChampionsPort;
    }

    @Override
    public List<NewVideo> fetchNewVideos() {
        return latestVideos()
                .stream()
                .filter(this::isNotInDatabase)
                .toList();
    }

    private List<NewVideo> latestVideos() {
        return channelsStream()
                .map(this::latestVideosByChannel)
                .toList()
                .stream()
                .flatMap(Collection::stream)
                .toList();
    }

    private Stream<Channel> channelsStream() {
        final var channels = getChannelsPort.getChannels();
        return channels.stream();
    }

    private List<NewVideo> latestVideosByChannel(Channel channel) {
        List<VideoDetails> latestVideos = latestVideosUseCase.latestVideosByChannel(channel);
        return latestVideos
                .stream()
                .map(video -> mapNewVideo(video, channel))
                .filter(Objects::nonNull).toList();
    }

    private NewVideo mapNewVideo(VideoDetails details, Channel channel) {
        Optional<String> deckCode = DeckCodeExtractorService.extractDeckCode(details.getDescription());

        if (deckCode.isEmpty()) {
            LOG.error("There is no deck code for the video {} from Channel {}", details.getTitle(), channel);
            return null;
        }

        try {
            Optional<LoRDeck> deck = deckCode.map(DeckCodeParser::decode);
            Set<LoRRegion> regions = deck.map(this::extractFaction).orElse(emptySet());
            Set<Champion> champions = deck.map(this::extractChampionsFromDeck).orElse(emptySet());

            return new NewVideo(deckCode.get(), details, channel, regions, champions);
        } catch (Exception e) {
            LOG.error("There was an error to map the deck: {}", e.getMessage());
            return null;
        }
    }

    private Set<Champion> extractChampionsFromDeck(LoRDeck deck) {
        return deck.getCards().keySet()
                .stream()
                .filter(Objects::nonNull)
                .map(LoRCard::getCardCode)
                .map(this::mapChampion)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    private Champion mapChampion(String cardCode) {
        return getChampionsPort
                .getChampions()
                .stream()
                .filter(champion -> cardCode.equals(champion.id()))
                .findFirst()
                .orElse(null);
    }

    private boolean isNotInDatabase(NewVideo video) {
        return !videoIsInDatabaseUseCase.isInDatabase(video.details().getId());
    }

    private Set<LoRRegion> extractFaction(LoRDeck deck) {
        return deck.getCards().keySet()
                .stream()
                .map(LoRCard::getRegion)
                .collect(toUnmodifiableSet());
    }

}
