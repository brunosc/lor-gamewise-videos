package info.gamewise.lor.videos.deckcodeextractor;

import java.util.Optional;

public interface DeckCodeExtractor {
    Optional<String> extract(String videoDescription);
}
