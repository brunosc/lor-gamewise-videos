package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.domain.ChampionRecord;
import info.gamewise.lor.videos.domain.ChannelStatistics;
import info.gamewise.lor.videos.domain.ChannelStatistics.NameCount;
import info.gamewise.lor.videos.domain.LoRChannel;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.port.out.GetChampionsPort;
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.github.brunosc.lor.domain.LoRRegion.DEMACIA;
import static com.github.brunosc.lor.domain.LoRRegion.FRELJORD;
import static com.github.brunosc.lor.domain.LoRRegion.IONIA;
import static com.github.brunosc.lor.domain.LoRRegion.MOUNT_TARGON;
import static com.github.brunosc.lor.domain.LoRRegion.NOXUS;
import static com.github.brunosc.lor.domain.LoRRegion.PILTOVER_AND_ZAUN;
import static com.github.brunosc.lor.domain.LoRRegion.SHADOW_ILES;
import static com.github.brunosc.lor.domain.LoRRegion.SHURIMA;
import static info.gamewise.lor.videos.DataLoader.newLoRVideo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ChannelStatisticsServiceTest {

    private static final LoRChannel MEGA_MOGWAI = new LoRChannel("MEGA_MOGWAI", "MegaMogwai", "1");
    private static final ChampionRecord ELISE = new ChampionRecord("1", "ELISE", "Elise");
    private static final ChampionRecord LEE_SIN = new ChampionRecord("2", "LEE_SIN", "Lee Sin");
    private static final ChampionRecord RIVEN = new ChampionRecord("3", "RIVEN", "Riven");
    private static final ChampionRecord YASUO = new ChampionRecord("4", "YASUO", "Yasuo");
    private static final ChampionRecord SWAIN = new ChampionRecord("5", "SWAIN", "Swain");
    private static final ChampionRecord ZED = new ChampionRecord("6", "ZED", "Zed");
    private static final ChampionRecord SIVIR = new ChampionRecord("7", "SIVIR", "Sivir");
    private static final ChampionRecord RENEKTON = new ChampionRecord("8", "RENEKTON", "Renekton");
    private static final ChampionRecord KINDRED = new ChampionRecord("9", "KINDRED", "Kindred");
    private static final ChampionRecord ASHE = new ChampionRecord("10", "ASHE", "Ashe");
    private static final ChampionRecord EZREAL = new ChampionRecord("11", "EZREAL", "Ezreal");
    private static final ChampionRecord MALPHITE = new ChampionRecord("12", "MALPHITE", "Malphite");

    private final GetVideosByChannelPort port =
            mock(GetVideosByChannelPort.class);

    private final GetChampionsPort getChampionsPort =
            mock(GetChampionsPort.class);

    private final ChannelStatisticsService cut =
            new ChannelStatisticsService(port, getChampionsPort);

    @Test
    void shouldCalculateStatisticsByChannel() {
        LoRChannel channel = MEGA_MOGWAI;
        given(port.videosByChannel(eq(channel.code()))).willReturn(videos(channel));
        given(getChampionsPort.getChampions()).willReturn(champions());

        ChannelStatistics channelStatistics = cut.channelStatistics(channel.code());

        assertEquals(channelStatistics.startedAt(), LocalDate.of(2020, 1, 1));
        assertThat(channelStatistics.champions()).isNotEmpty();
        assertThat(channelStatistics.regions()).isNotEmpty();

        NameCount mostPopularChampion = channelStatistics.champions().get(0);
        assertEquals(mostPopularChampion.name(), YASUO.name());
        assertEquals(mostPopularChampion.count(), 2L);

        NameCount mostPopularRegion = channelStatistics.regions().get(0);
        assertEquals(mostPopularRegion.name(), IONIA.prettyName());
        assertEquals(mostPopularRegion.count(), 4L);
    }

    private List<LoRVideo> videos(LoRChannel channel) {
        return List.of(
                newLoRVideo(channel, Set.of(ELISE), Set.of(NOXUS, SHADOW_ILES), 1),
                newLoRVideo(channel, Set.of(LEE_SIN, RIVEN), Set.of(NOXUS, IONIA), 2),
                newLoRVideo(channel, Set.of(YASUO, SWAIN), Set.of(NOXUS, IONIA), 3),
                newLoRVideo(channel, Set.of(ZED), Set.of(DEMACIA, IONIA), 4),
                newLoRVideo(channel, Set.of(SIVIR, RENEKTON), Set.of(DEMACIA, SHURIMA), 5),
                newLoRVideo(channel, Set.of(KINDRED, ASHE), Set.of(FRELJORD, SHADOW_ILES), 6),
                newLoRVideo(channel, Set.of(EZREAL), Set.of(PILTOVER_AND_ZAUN, SHURIMA), 7),
                newLoRVideo(channel, Set.of(YASUO, MALPHITE), Set.of(IONIA, MOUNT_TARGON), 8)
        );
    }

    private List<ChampionRecord> champions() {
        return List.of(ELISE, LEE_SIN, RIVEN, YASUO, SWAIN, ZED, SIVIR, RENEKTON, KINDRED, ASHE, EZREAL, MALPHITE);
    }

}
