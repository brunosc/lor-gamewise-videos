package info.gamewise.lor.videos.entity.video

import info.gamewise.lor.videos.domain.LoRVideo
import info.gamewise.lor.videos.port.`in`.GetVideosUseCase
import info.gamewise.lor.videos.port.`in`.SearchParams
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort
import info.gamewise.lor.videos.port.out.NewVideo
import info.gamewise.lor.videos.port.out.SaveVideoUseCase
import info.gamewise.lor.videos.port.out.VideoIsInDatabaseUseCase
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
internal class VideoPersistenceAdapter(private val repository: VideoRepository,
                                       private val mapper: VideoMapper,
                                       private val mongoTemplate: MongoTemplate
) :
    GetVideosUseCase, VideoIsInDatabaseUseCase, SaveVideoUseCase, GetVideosByChannelPort {

    override fun fetchByFilter(params: SearchParams, pageable: Pageable): Page<LoRVideo> {
        val query = VideoQueryBuilder(params, pageable).build()
        val videosCount = getVideosCount(query)
        val page = PageImpl(videosCount.videos, pageable, videosCount.count)

        return mapper.toDomainPage(page, pageable)
    }

    private fun getVideosCount(videoQuery: Query): VideosCount {
        return VideosCount(
            videos = mongoTemplate.find(videoQuery, VideoJpaEntity::class.java),
            count = mongoTemplate.count(Query(), VideoJpaEntity::class.java)
        )
    }

    override fun isInDatabase(videoId: String): Boolean {
        return repository.existsByVideoId(videoId)
    }

    override fun save(newVideos: List<NewVideo>) {
        val videoEntities = newVideos.map { VideoJpaEntity(it) }
        repository.saveAll(videoEntities)
    }

    override fun videosByChannel(channel: String): List<LoRVideo> {
        return repository.findByChannel(channel)
            .map { mapper.toDomain(it) }
    }
}

private class VideosCount(val videos: List<VideoJpaEntity>,
                          val count: Long)