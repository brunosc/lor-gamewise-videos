package info.gamewise.lor.videos.entity.video

import com.github.brunosc.fetcher.domain.VideoThumbnails
import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.port.out.NewVideo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "videos")
internal class VideoJpaEntity(@Id var id: String? = null,
                              @Indexed(unique = true) var videoId: String? = null,
                              var url: String? = null,
                              var title: String? = null,
                              var deckCode: String? = null,
                              var channel: String? = null,
                              var regions: Set<LoRRegion>? = null,
                              var champions: Set<String>? = null,
                              var publishedAt: LocalDateTime? = null,
                              var thumbnails: VideoThumbnails? = null) {


    constructor(newVideo: NewVideo) : this(
        videoId = newVideo.details.id,
        url = newVideo.details.url,
        title = newVideo.details.title,
        deckCode = newVideo.deckCode,
        publishedAt = newVideo.details.publishedAt,
        thumbnails = newVideo.details.thumbnails,
        channel = newVideo.channel.code,
        regions = newVideo.regions,
        champions = newVideo.champions.map { it.code }.toSet()
    )
}