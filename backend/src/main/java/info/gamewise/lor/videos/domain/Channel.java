package info.gamewise.lor.videos.domain;

import info.gamewise.lor.videos.deckcodeextractor.DeckCodeExtractor;
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckCodeExtractor;
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckLinkExtractor;
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithRegisteredDeckExtractor;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.List.of;

public enum Channel {

    MEGA_MOGWAI("MegaMogwai", "UUvUZXLShMx-FZvoadtb8xBQ", singletonList(new DescriptionWithDeckCodeExtractor())),
    SILVERFUSE("Silverfuse", "UUzxv-mX_xsiCzuwTrJWP5ww", singletonList(new DescriptionWithDeckCodeExtractor())),
    ALANZQ("Alanzq", "UUJOh07P-0inhO-RETLY0tuQ", singletonList(new DescriptionWithDeckLinkExtractor())),
    BRUISED_BY_GOD("BruisedByGod", "UUMnBDEOjxCOhrFWz13RPSYg", singletonList(new DescriptionWithDeckCodeExtractor())),
    GRAPP_LR("GrappLr", "UUq5ZYJax8VC580PAIU5xuvg", of(new DescriptionWithDeckCodeExtractor(), new DescriptionWithDeckLinkExtractor())),
    SAUCY("Saucy Mailman", "UUlAM1lW_FFVJRaxYXCIGhhQ", singletonList(new DescriptionWithRegisteredDeckExtractor())),
    NIC_MAKES_PLAYS("NicMakesPlays", "UU_mQ6OSK-frzbXNpFbwZJgg", singletonList(new DescriptionWithDeckCodeExtractor())),
    LUCKY_CAD("LuckyCAD", "UUa4tK4ry575cuJzayeaxmSQ", singletonList(new DescriptionWithDeckCodeExtractor())),
    LAN_UP("LAN UP", "UU54qt3GdtHThgAsPd_JZ0WQ", singletonList(new DescriptionWithDeckCodeExtractor())),
    HAPPY_DURIAN("Happy Durian", "UUphWqOWOslUD40sM26o8t4g", singletonList(new DescriptionWithRegisteredDeckExtractor()));

    private final String name;
    private final String playlistId;
    private final List<DeckCodeExtractor> deckCodeExtractors;

    Channel(String name, String playlistId, List<DeckCodeExtractor> deckCodeExtractors) {
        this.name = name;
        this.playlistId = playlistId;
        this.deckCodeExtractors = deckCodeExtractors;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public Optional<String> extractDeckCode(String videoDescription) {
        for (DeckCodeExtractor extractor : deckCodeExtractors) {
            Optional<String> deckCode = extractor.extract(videoDescription);
            if (deckCode.isPresent()) {
                return deckCode.map(this::sanitizeDeckCode);
            }
        }
        return Optional.empty();
    }

    private String sanitizeDeckCode(String deckCode) {
        return deckCode.replace("#", "");
    }
}
