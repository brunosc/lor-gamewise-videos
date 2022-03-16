package info.gamewise.lor.videos.domain

data class LoRVideoFilter(val regions: List<VideoRegion>,
                          val channels: List<VideoChannel>,
                          val champions: List<VideoChampion>)