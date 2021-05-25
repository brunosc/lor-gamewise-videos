package info.gamewise.lor.videos.port.in;

import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.domain.ChannelStatistics;

public interface GetChannelStatisticsUseCase {
    ChannelStatistics channelStatistics(Channel channel);
}
