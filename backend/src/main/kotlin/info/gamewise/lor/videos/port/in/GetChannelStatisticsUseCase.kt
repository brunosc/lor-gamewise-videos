package info.gamewise.lor.videos.port.`in`

import info.gamewise.lor.videos.domain.ChannelStatistics

interface GetChannelStatisticsUseCase {
    fun channelStatistics(channel: String): ChannelStatistics
}