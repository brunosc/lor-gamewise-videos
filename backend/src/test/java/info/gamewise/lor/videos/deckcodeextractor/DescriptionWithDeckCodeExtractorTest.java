package info.gamewise.lor.videos.deckcodeextractor;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DescriptionWithDeckCodeExtractorTest {

    private final DeckCodeExtractor extractor = new DescriptionWithDeckCodeExtractor();

    @Test
    void deckCodeAndUrl() {
        final var deckCode = "CEBQCAYFCAFAGCICBEISGOCLJRHVIYABAECTCAICAMEVKWYA";

        final var description = """
            A brief title about the video
            
            https://lor.mobalytics.gg/decks/code/ANY-DECK-CODE
            
            Deck code: %s
            
            Thank you!
            Have a nice day.
        """.formatted(deckCode);

        Optional<String> deckCodeOpt = extractor.extract(description);

        assertTrue(deckCodeOpt.isPresent());
        assertEquals(deckCodeOpt.get(), deckCode);
    }

    @Test
    void onlyUrlShouldBeEmpty() {
        final var description = """
            A brief title about the video
            
            https://lor.mobalytics.gg/decks/code/ANY-DECK-CODE
            
            Thank you!
            Have a nice day.
        """;

        Optional<String> deckCodeOpt = extractor.extract(description);

        assertTrue(deckCodeOpt.isEmpty());
    }

}