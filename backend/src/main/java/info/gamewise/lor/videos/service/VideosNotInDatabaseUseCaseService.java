package info.gamewise.lor.videos.service;

import com.github.brunosc.fetcher.domain.VideoDetails;
import com.github.brunosc.lor.DeckCodeParser;
import com.github.brunosc.lor.domain.LoRCard;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRDeck;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.usecase.LatestYouTubeVideosUseCase;
import info.gamewise.lor.videos.usecase.VideoIsInDatabaseUseCase;
import info.gamewise.lor.videos.usecase.VideosNotInDatabaseUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Service
class VideosNotInDatabaseUseCaseService implements VideosNotInDatabaseUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(VideosNotInDatabaseUseCaseService.class);

    private final VideoIsInDatabaseUseCase videoIsInDatabaseUseCase;
    private final LatestYouTubeVideosUseCase latestVideosUseCase;

    VideosNotInDatabaseUseCaseService(VideoIsInDatabaseUseCase videoIsInDatabaseUseCase, LatestYouTubeVideosUseCase latestVideosUseCase) {
        this.videoIsInDatabaseUseCase = videoIsInDatabaseUseCase;
        this.latestVideosUseCase = latestVideosUseCase;
    }

    @Override
    public List<NewVideo> fetch() {
        return latestVideos()
                .stream()
                .filter(this::isNotInDatabase)
                .collect(toUnmodifiableList());
    }

    private List<NewVideo> latestVideos() {
        return channelsStream()
                .map(this::latestVideosByChannel)
                .collect(toUnmodifiableList())
                .stream()
                .flatMap(Collection::stream)
                .collect(toUnmodifiableList());
    }

    private Stream<Channel> channelsStream() {
        return EnumSet.allOf(Channel.class).stream();
    }

    private List<NewVideo> latestVideosByChannel(Channel channel) {
        List<VideoDetails> latestVideos = latestVideosUseCase.latestVideosByChannel(channel);
        return latestVideos
                .stream()
                .map(video -> mapNewVideo(video, channel))
                .filter(Objects::nonNull)
                .collect(toUnmodifiableList());
    }

    private NewVideo mapNewVideo(VideoDetails details, Channel channel) {
        Optional<String> deckCode = channel.extractDeckCode(details.getDescription());

        if (deckCode.isEmpty()) {
            LOG.error("There is no deck code for the video {} from Channel {}", details.getTitle(), channel);
            return null;
        }

        try {
            Optional<LoRDeck> deck = deckCode.map(DeckCodeParser::decode);
            Set<LoRRegion> regions = deck.map(this::extractFaction).orElse(emptySet());
            Set<LoRChampion> champions = deck.map(LoRDeck::getChampions).orElse(emptySet());

            return new NewVideo(deckCode.get(), details, channel, regions, champions);
        } catch (Exception e) {
            LOG.error("There was an error to map the deck", e);
            return null;
        }
    }

    private boolean isNotInDatabase(NewVideo video) {
        return !videoIsInDatabaseUseCase.isInDatabase(video.getDetails().getId());
    }

    private Set<LoRRegion> extractFaction(LoRDeck deck) {
        return deck.getCards().keySet()
                .stream()
                .map(LoRCard::getRegion)
                .collect(toUnmodifiableSet());
    }

}
