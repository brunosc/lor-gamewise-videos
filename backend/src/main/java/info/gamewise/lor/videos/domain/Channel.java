package info.gamewise.lor.videos.domain;

import info.gamewise.lor.videos.deckcodeextractor.DeckCodeExtractor;
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckCodeExtractor;
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckLinkExtractor;

import java.util.Optional;

public enum Channel {

    HOOGLAND_HIGHLIGHTS("Hooglandia Highlights", "UUN2TT8LDsbYfqSqGiey1ADA", new DescriptionWithDeckCodeExtractor()),
    MEGA_MOGWAI("MegaMogwai", "UUvUZXLShMx-FZvoadtb8xBQ", new DescriptionWithDeckCodeExtractor()),
    SILVERFUSE("Silverfuse", "UUzxv-mX_xsiCzuwTrJWP5ww", new DescriptionWithDeckCodeExtractor()),
    HAWK_TIE("HawkTie", "UU8ngSK9Reia_LYtEYXMZejg", new DescriptionWithDeckCodeExtractor()),
    ALANZQ("Alanzq", "UUJOh07P-0inhO-RETLY0tuQ", new DescriptionWithDeckLinkExtractor()),
    BRUISED_BY_GOD("BruisedByGod", "UUMnBDEOjxCOhrFWz13RPSYg", new DescriptionWithDeckCodeExtractor()),
    GRAPP_LR_CODE("GrappLr", "UUq5ZYJax8VC580PAIU5xuvg", new DescriptionWithDeckCodeExtractor()),
    GRAPP_LR_LINK("GrappLr", "UUq5ZYJax8VC580PAIU5xuvg", new DescriptionWithDeckLinkExtractor());

    private final String name;
    private final String playlistId;
    private final DeckCodeExtractor deckCodeExtractor;

    Channel(String name, String playlistId, DeckCodeExtractor deckCodeExtractor) {
        this.name = name;
        this.playlistId = playlistId;
        this.deckCodeExtractor = deckCodeExtractor;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public Optional<String> extractDeckCode(String videoDescription) {
        return this.deckCodeExtractor
                .extract(videoDescription)
                .map(this::sanitizeDeckCode);
    }

    private String sanitizeDeckCode(String deckCode) {
        return deckCode.replace("#", "");
    }
}
