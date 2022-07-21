package info.gamewise.lor.videos.entity.video

import org.springframework.data.mongodb.repository.MongoRepository

internal interface VideoRepository : MongoRepository<VideoJpaEntity, String> {
    fun existsByVideoId(videoId: String): Boolean
    fun findByChannel(channel: String): List<VideoJpaEntity>
}