package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.domain.json.Champion;
import info.gamewise.lor.videos.domain.ChannelStatistics;
import info.gamewise.lor.videos.domain.ChannelStatistics.NameCount;
import info.gamewise.lor.videos.domain.json.Channel;
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

    private static final Channel MEGA_MOGWAI = new Channel("MEGA_MOGWAI", "MegaMogwai", "1");
    private static final Champion ELISE = new Champion("1", "ELISE", "Elise");
    private static final Champion LEE_SIN = new Champion("2", "LEE_SIN", "Lee Sin");
    private static final Champion RIVEN = new Champion("3", "RIVEN", "Riven");
    private static final Champion YASUO = new Champion("4", "YASUO", "Yasuo");
    private static final Champion SWAIN = new Champion("5", "SWAIN", "Swain");
    private static final Champion ZED = new Champion("6", "ZED", "Zed");
    private static final Champion SIVIR = new Champion("7", "SIVIR", "Sivir");
    private static final Champion RENEKTON = new Champion("8", "RENEKTON", "Renekton");
    private static final Champion KINDRED = new Champion("9", "KINDRED", "Kindred");
    private static final Champion ASHE = new Champion("10", "ASHE", "Ashe");
    private static final Champion EZREAL = new Champion("11", "EZREAL", "Ezreal");
    private static final Champion MALPHITE = new Champion("12", "MALPHITE", "Malphite");

    private final GetVideosByChannelPort port =
            mock(GetVideosByChannelPort.class);

    private final GetChampionsPort getChampionsPort =
            mock(GetChampionsPort.class);

    private final ChannelStatisticsService cut =
            new ChannelStatisticsService(port, getChampionsPort);

    @Test
    void shouldCalculateStatisticsByChannel() {
        Channel channel = MEGA_MOGWAI;
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

    private List<LoRVideo> videos(Channel channel) {
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

    private List<Champion> champions() {
        return List.of(ELISE, LEE_SIN, RIVEN, YASUO, SWAIN, ZED, SIVIR, RENEKTON, KINDRED, ASHE, EZREAL, MALPHITE);
    }

}
