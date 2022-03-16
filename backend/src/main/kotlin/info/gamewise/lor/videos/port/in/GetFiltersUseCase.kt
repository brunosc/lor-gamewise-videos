package info.gamewise.lor.videos.port.`in`

import info.gamewise.lor.videos.domain.LoRVideoFilter

interface GetFiltersUseCase {
    fun getFilters(): LoRVideoFilter
}