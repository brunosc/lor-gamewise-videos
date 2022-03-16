package info.gamewise.lor.videos.entity

import info.gamewise.lor.videos.port.`in`.SearchParams
import org.springframework.data.domain.Sort

internal class VideoPredicate(params: SearchParams) {

    private val params: SearchParams
    private val sort: Sort

    init {
        this.params = params
        sort = sortByPublishedAt()
    }

    fun sort(): Sort {
        return sort
    }

    private fun sortByPublishedAt(): Sort {
        val sortOrderPublishedAt = Sort.Order(Sort.Direction.DESC, "publishedAt")
        return Sort.by(listOf(sortOrderPublishedAt))
    }
}