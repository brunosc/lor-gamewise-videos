package info.gamewise.lor.videos.entity

import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.domain.LoRVideo
import info.gamewise.lor.videos.port.`in`.GetVideosUseCase
import info.gamewise.lor.videos.port.`in`.SearchParams
import info.gamewise.lor.videos.port.out.GetVideosByChannelPort
import info.gamewise.lor.videos.port.out.NewVideo
import info.gamewise.lor.videos.port.out.SaveVideoUseCase
import info.gamewise.lor.videos.port.out.VideoIsInDatabaseUseCase
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.inValues
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import org.springframework.util.CollectionUtils.isEmpty
import java.util.function.LongSupplier

@Service
internal class VideoPersistenceAdapter(private val repository: VideoRepository,
                                       private val mapper: VideoMapper,
                                       private val mongoTemplate: MongoTemplate) :
    GetVideosUseCase, VideoIsInDatabaseUseCase, SaveVideoUseCase, GetVideosByChannelPort {

    override fun fetchByFilter(params: SearchParams, pageable: Pageable): Page<LoRVideo> {
        val query = buildQuery(params, pageable)
        val videos = mongoTemplate.find(query, VideoJpaEntity::class.java)
        val count = mongoTemplate.count(Query(), VideoJpaEntity::class.java)
        val page = PageImpl(videos, pageable, count)

        return mapper.toDomainPage(page, pageable)
    }

    private fun buildQuery(params: SearchParams, pageable: Pageable): Query  {
        val query = Query()
        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)

        query
            .with(Sort.by(Sort.Direction.DESC, "publishedAt"))
            .with(pageRequest)

        if (!isEmpty(params.champions)) {
            query.addCriteria(Criteria.where("champions").all(params.champions))
        }

        if (!isEmpty(params.regions)) {
            query.addCriteria(Criteria.where("regions").all(params.regions.map { LoRRegion.fromCode(it) }))
        }

        if (!isEmpty(params.channels)) {
            query.addCriteria(Criteria.where("channel").`in`(params.channels))
        }

        return query
    }

    override fun isInDatabase(videoId: String): Boolean {
        return repository.existsByVideoId(videoId)
    }

    override fun save(newVideos: List<NewVideo>) {
        val videoEntities: List<VideoJpaEntity> = newVideos.stream()
            .map { newVideo: NewVideo? -> VideoJpaEntity(newVideo!!) }.toList()
        repository.saveAll(videoEntities)
    }

    override fun videosByChannel(channel: String): List<LoRVideo> {
        return repository.findByChannel(channel)
            .stream()
            .map { entity: VideoJpaEntity? ->
                mapper.toDomain(
                    entity!!
                )
            }.toList()
    }
}