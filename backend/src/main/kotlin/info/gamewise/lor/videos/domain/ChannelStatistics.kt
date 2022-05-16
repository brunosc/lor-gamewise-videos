package info.gamewise.lor.videos.domain

import java.time.LocalDate

data class ChannelStatistics(val startedAt: LocalDate,
                             val champions: List<NameCount>,
                             val regions: List<NameCount>)

data class NameCount(val name: String, val count: Int)