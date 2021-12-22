package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.domain.ChannelStatistics;
import info.gamewise.lor.videos.domain.ChannelStatistics.NameCount;
import info.gamewise.lor.videos.domain.LoRChannel;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.github.brunosc.lor.domain.LoRChampion.ASHE;
import static com.github.brunosc.lor.domain.LoRChampion.ELISE;
import static com.github.brunosc.lor.domain.LoRChampion.EZREAL;
import static com.github.brunosc.lor.domain.LoRChampion.KINDRED;
import static com.github.brunosc.lor.domain.LoRChampion.LEE_SIN;
import static com.github.brunosc.lor.domain.LoRChampion.MALPHITE;
import static com.github.brunosc.lor.domain.LoRChampion.RENEKTON;
import static com.github.brunosc.lor.domain.LoRChampion.RIVEN;
import static com.github.brunosc.lor.domain.LoRChampion.SIVIR;
import static com.github.brunosc.lor.domain.LoRChampion.SWAIN;
import static com.github.brunosc.lor.domain.LoRChampion.YASUO;
import static com.github.brunosc.lor.domain.LoRChampion.ZED;
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

class ChannelStatisticsServiceTest {

    private static final LoRChannel MEGA_MOGWAI = new LoRChannel("MEGA_MOGWAI", "MegaMogwai", "1");

    private final GetVideosByChannelPort port =
            Mockito.mock(GetVideosByChannelPort.class);

    private final ChannelStatisticsService cut =
            new ChannelStatisticsService(port);

    @Test
    void shouldCalculateStatisticsByChannel() {
        LoRChannel channel = MEGA_MOGWAI;
        given(port.videosByChannel(eq(channel.code()))).willReturn(videos(channel));

        ChannelStatistics channelStatistics = cut.channelStatistics(channel.code());

        assertEquals(channelStatistics.startedAt(), LocalDate.of(2020, 1, 1));
        assertThat(channelStatistics.champions()).isNotEmpty();
        assertThat(channelStatistics.regions()).isNotEmpty();

        NameCount mostPopularChampion = channelStatistics.champions().get(0);
        assertEquals(mostPopularChampion.name(), YASUO.prettyName());
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

}
