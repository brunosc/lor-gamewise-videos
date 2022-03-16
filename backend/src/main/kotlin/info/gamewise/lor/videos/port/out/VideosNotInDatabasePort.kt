package info.gamewise.lor.videos.port.out

import com.github.brunosc.fetcher.domain.VideoDetails
import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.domain.json.Channel

interface VideosNotInDatabasePort {
    fun fetchNewVideos(): List<NewVideo>
}

data class NewVideo(val deckCode: String,
                    val details: VideoDetails,
                    val channel: Channel,
                    val regions: Set<LoRRegion>,
                    val champions: Set<Champion>)