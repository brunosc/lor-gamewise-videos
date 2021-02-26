package info.gamewise.lor.videos.deckcodeextractor;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DescriptionWithDeckLinkExtractorTest {

    private final DeckCodeExtractor extractor = new DescriptionWithDeckLinkExtractor();

    @Test
    void mobaDecksCode() {
        final var deckCode = "CEBQCAYFCAFAGCICBEISGOCLJRHVIYABAECTCAICAMEVKWYA";

        final var description = """
            A brief title about the video
            
            https://lor.mobalytics.gg/decks/code/%s
            
            Thank you!
            Have a nice day.
        """.formatted(deckCode);

        Optional<String> deckCodeOpt = extractor.extract(description);

        assertTrue(deckCodeOpt.isPresent());
        assertEquals(deckCodeOpt.get(), deckCode);
    }

    @Test
    void mobaInvalidDecksCodeUrl() {
        final var deckCode = "CEBQCAYFCAFAGCICBEISGOCLJRHVIYABAECTCAICAMEVKWYA";

        final var description = """
            A brief title about the video
            
            https://lor.mobalytics.gg/decks/%s
            
            Thank you!
            Have a nice day.
        """.formatted(deckCode);

        Optional<String> deckCodeOpt = extractor.extract(description);

        assertTrue(deckCodeOpt.isEmpty());
    }

    @Test
    void onlyDeckCode() {
        final var deckCode = "CEBQCAYFCAFAGCICBEISGOCLJRHVIYABAECTCAICAMEVKWYA";

        final var description = """
            A brief title about the video
            
            Deck code: %s
            
            Thank you!
            Have a nice day.
        """.formatted(deckCode);

        Optional<String> deckCodeOpt = extractor.extract(description);

        assertTrue(deckCodeOpt.isEmpty());
    }

}