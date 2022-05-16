package info.gamewise.lor.videos.domain

import com.github.brunosc.lor.domain.LoRRegion

class VideoRegion(region: LoRRegion) : Comparable<VideoRegion> {
    val code: String
    val description: String

    init {
        this.code = region.code
        description = region.prettyName()
    }

    override fun compareTo(other: VideoRegion): Int {
        return this.code.compareTo(other.code)
    }

    override fun toString(): String {
        return "VideoRegion{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}'
    }
}