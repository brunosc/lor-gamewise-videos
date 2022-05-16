package info.gamewise.lor.videos.port.out

import info.gamewise.lor.videos.domain.json.Channel
import java.util.*

interface GetChannelsPort {
    fun getChannels(): List<Channel>
    fun getChannelByCode(channelCode: String): Channel?
}