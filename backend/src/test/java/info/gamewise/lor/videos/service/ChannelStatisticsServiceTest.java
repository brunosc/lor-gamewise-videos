package info.gamewise.lor.videos.service;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.ChannelStatistics;
import info.gamewise.lor.videos.domain.ChannelStatistics.NameCount;
import info.gamewise.lor.videos.domain.LoRVideo;
import info.gamewise.lor.videos.domain.VideoChampion;
import info.gamewise.lor.videos.domain.VideoChannel;
import info.gamewise.lor.videos.domain.VideoRegion;
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.github.brunosc.lor.domain.LoRChampion.*;
import static com.github.brunosc.lor.domain.LoRRegion.*;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

class ChannelStatisticsServiceTest {

    private final GetVideosByChannelPort port =
            Mockito.mock(GetVideosByChannelPort.class);

    private final ChannelStatisticsService cut =
            new ChannelStatisticsService(port);

    @Test
    void shouldCalculateStatisticsByChannel() {
        Channel channel = Channel.MEGA_MOGWAI;
        given(port.videosByChannel(eq(channel))).willReturn(videos(channel));

        ChannelStatistics channelStatistics = cut.channelStatistics(channel);

        assertEquals(channelStatistics.getStartedAt(), LocalDate.of(2020, 1, 1));
        assertThat(channelStatistics.getChampions()).isNotEmpty();
        assertThat(channelStatistics.getRegions()).isNotEmpty();

        NameCount mostPopularChampion = channelStatistics.getChampions().get(0);
        assertEquals(mostPopularChampion.getName(), "Yasuo");
        assertEquals(mostPopularChampion.getCount(), 2L);

        NameCount mostPopularRegion = channelStatistics.getRegions().get(0);
        assertEquals(mostPopularRegion.getName(), "Ionia");
        assertEquals(mostPopularRegion.getCount(), 4L);
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

    private LoRVideo newLoRVideo(Channel channel, Set<LoRChampion> champions, Set<LoRRegion> regions, int index) {
        return new LoRVideo(
                "url " + index,
                "title " + index,
                "CODE" + index,
                new VideoChannel(channel),
                champions.stream().map(VideoChampion::new).collect(toUnmodifiableSet()),
                regions.stream().map(VideoRegion::new).collect(toUnmodifiableSet())        ,
                LocalDate.of(2020, 1, index).atStartOfDay(),
                null
        );
    }

}
