package info.gamewise.lor.videos.entity.channel

import org.springframework.data.mongodb.repository.MongoRepository

internal interface ChannelRepository : MongoRepository<ChannelJpaEntity, String>