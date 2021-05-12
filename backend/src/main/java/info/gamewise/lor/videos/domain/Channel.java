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

    MEGA_MOGWAI("MegaMogwai", "UUvUZXLShMx-FZvoadtb8xBQ"),
    SILVERFUSE("Silverfuse", "UUzxv-mX_xsiCzuwTrJWP5ww"),
    ALANZQ("Alanzq", "UUJOh07P-0inhO-RETLY0tuQ"),
    BRUISED_BY_GOD("BruisedByGod", "UUMnBDEOjxCOhrFWz13RPSYg"),
    GRAPP_LR("GrappLr", "UUq5ZYJax8VC580PAIU5xuvg"),
    SAUCY("Saucy Mailman", "UUlAM1lW_FFVJRaxYXCIGhhQ"),
    NIC_MAKES_PLAYS("NicMakesPlays", "UU_mQ6OSK-frzbXNpFbwZJgg"),
    LUCKY_CAD("LuckyCAD", "UUa4tK4ry575cuJzayeaxmSQ"),
    LAN_UP("LAN UP", "UU54qt3GdtHThgAsPd_JZ0WQ"),
    HAPPY_DURIAN("Happy Durian", "UUphWqOWOslUD40sM26o8t4g"),
    SNNUY("Snnuy", "UUrMr5Wc0Cn5AGINmUEquzdA"),
    SQUIRRELS("Squirrels", "UUb42Ogf65ddvkCuPFIw57UQ"),
    GRIMSET("Grimset", "UUh-rIrNS9D0_MDKx-dM2YTA"),
    HOBBITSIZZ("hobbitsizz", "UUk_QEZixG7d5yblvsNjYomg"),
    ELITE4IN1("Elite4in1", "UUSLgEtVzKSnav4XcxOjcT7g");

    private final String name;
    private final String playlistId;

    private static final List<DeckCodeExtractor> EXTRACTORS =
            of(new DescriptionWithDeckCodeExtractor(), new DescriptionWithDeckLinkExtractor(), new DescriptionWithRegisteredDeckExtractor());

    Channel(String name, String playlistId) {
        this.name = name;
        this.playlistId = playlistId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public Optional<String> extractDeckCode(String videoDescription) {
        for (DeckCodeExtractor extractor : EXTRACTORS) {
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
