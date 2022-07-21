package info.gamewise.lor.videos.entity.channel

import info.gamewise.lor.videos.config.LoRCacheConfig
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.GetChannelsPort
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicReference

@Service
internal open class ChannelPersistenceAdapter(private val repository: ChannelRepository) : GetChannelsPort {

    private val channels = AtomicReference<List<Channel>>(emptyList())

    @Cacheable(LoRCacheConfig.CHANNELS)
    override fun getChannels(): List<Channel> {
        channels.set(repository.findAll().map { mapChannel(it) })
        return channels.get()
    }

    override fun getChannelByCode(channelCode: String): Channel? {
        if (channels.get().isEmpty()) {
            getChannels()
        }

        return channels.get()
            .firstOrNull { channelCode == it.code }
    }

    private fun mapChannel(entity: ChannelJpaEntity): Channel {
        return Channel(
            code = entity.code!!,
            name = entity.name!!,
            playlistId = entity.playlistId!!,
        )
    }

}