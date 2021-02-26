package info.gamewise.lor.videos.deckcodeextractor;

import java.util.Optional;

public class DescriptionWithDeckCodeExtractor extends AbstractDeckCodeExtractor implements DeckCodeExtractor {

    private static final int MIN_CODE_LENGTH = 43;

    @Override
    public Optional<String> extract(String videoDescription) {
        return descriptionAsStream(videoDescription)
                .filter(this::filterDeckCode)
                .findFirst();
    }

    private boolean filterDeckCode(String word) {
        return word.length() >= MIN_CODE_LENGTH && !word.contains("http");
    }
}
