package info.gamewise.lor.videos.domain

import com.github.brunosc.fetcher.domain.VideoThumbnails.ThumbnailItem
import java.time.LocalDateTime

data class LoRVideo(val url: String,
                    val title: String,
                    val deckCode: String,
                    val channel: VideoChannel,
                    val champions: Set<VideoChampion>,
                    val regions: Set<VideoRegion>,
                    val publishedAt: LocalDateTime,
                    val thumbnail: ThumbnailItem)