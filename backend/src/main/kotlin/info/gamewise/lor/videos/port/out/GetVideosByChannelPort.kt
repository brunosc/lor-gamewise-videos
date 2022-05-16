package info.gamewise.lor.videos.port.out

import info.gamewise.lor.videos.domain.LoRVideo

interface GetVideosByChannelPort {
    fun videosByChannel(channel: String): List<LoRVideo>
}