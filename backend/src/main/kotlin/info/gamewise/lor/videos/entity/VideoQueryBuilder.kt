package info.gamewise.lor.videos.entity

import com.github.brunosc.lor.domain.LoRRegion
import info.gamewise.lor.videos.port.`in`.SearchParams
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class VideoQueryBuilder(val params: SearchParams,
                        val pageable: Pageable) {

    fun build(): Query {
        val query = initQuery()

        if (params.champions.isNotEmpty()) {
            query.addCriteria(Criteria.where("champions").all(params.champions))
        }

        if (params.regions.isNotEmpty()) {
            query.addCriteria(Criteria.where("regions").all(params.regions.map { LoRRegion.fromCode(it) }))
        }

        if (params.channels.isNotEmpty()) {
            query.addCriteria(Criteria.where("channel").`in`(params.channels))
        }

        return query
    }

    private fun initQuery(): Query {
        val query = Query()

        return query
            .with(Sort.by(Sort.Direction.DESC, "publishedAt"))
            .with(PageRequest.of(pageable.pageNumber, pageable.pageSize))
    }
}