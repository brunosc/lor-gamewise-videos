package info.gamewise.lor.videos.domain

import info.gamewise.lor.videos.domain.json.Channel

class VideoChannel(channel: Channel) {
    val name: String
    val code: String

    init {
        name = channel.name
        this.code = channel.code
    }

    override fun toString(): String {
        return "VideoChannel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}'
    }
}