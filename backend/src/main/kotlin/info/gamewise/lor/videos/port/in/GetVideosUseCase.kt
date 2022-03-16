package info.gamewise.lor.videos.port.`in`

import info.gamewise.lor.videos.domain.LoRVideo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GetVideosUseCase {
    fun fetchByFilter(params: SearchParams, pageable: Pageable): Page<LoRVideo>
}

data class SearchParams(val regions: List<String> = emptyList(),
                        val champions: List<String> = emptyList(),
                        val channels: List<String> = emptyList())