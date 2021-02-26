package info.gamewise.lor.videos.deckcodeextractor;

import java.util.Optional;

public class DescriptionWithDeckLinkExtractor extends AbstractDeckCodeExtractor implements DeckCodeExtractor {

    @Override
    public Optional<String> extract(String videoDescription) {
        return descriptionAsStream(videoDescription)
                .filter(this::filterMobalyticsLink)
                .findFirst()
                .map(this::deckCodeFromMobaLinkUrl);
    }

    private boolean filterMobalyticsLink(String word) {
        return word.contains("https://lor.mobalytics.gg/decks/code");
    }

    private String deckCodeFromMobaLinkUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
