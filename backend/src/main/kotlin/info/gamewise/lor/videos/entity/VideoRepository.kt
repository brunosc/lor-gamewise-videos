package info.gamewise.lor.videos.entity

import org.springframework.data.mongodb.repository.MongoRepository
import info.gamewise.lor.videos.entity.VideoJpaEntity
import org.springframework.data.querydsl.QuerydslPredicateExecutor

internal interface VideoRepository : MongoRepository<VideoJpaEntity, String> {
    fun existsByVideoId(videoId: String): Boolean
    fun findByChannel(channel: String): List<VideoJpaEntity>
}