package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.deckcodeextractor.DeckCodeExtractor;
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckCodeExtractor;
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckLinkExtractor;
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithRegisteredDeckExtractor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.List.of;

public class DeckCodeExtractorService {

    private DeckCodeExtractorService() {}

    private static final List<DeckCodeExtractor> EXTRACTORS =
            of(new DescriptionWithDeckCodeExtractor(), new DescriptionWithDeckLinkExtractor(), new DescriptionWithRegisteredDeckExtractor());

    public static Optional<String> extractDeckCode(String videoDescription) {
        for (DeckCodeExtractor extractor : EXTRACTORS) {
            Optional<String> deckCode = extractor.extract(videoDescription);
            if (deckCode.isPresent()) {
                return deckCode.map(DeckCodeExtractorService::sanitizeDeckCode);
            }
        }
        return Optional.empty();
    }

    private static String sanitizeDeckCode(String deckCode) {
        return deckCode.replace("#", "");
    }

}
