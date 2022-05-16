package info.gamewise.lor.videos.service

import info.gamewise.lor.videos.config.ClearCache
import info.gamewise.lor.videos.port.out.ClearCacheUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
open class ClearCacheService : ClearCacheUseCase {

    private val log = LoggerFactory.getLogger(ClearCacheService::class.java)

    @ClearCache
    override fun clearCache() {
        log.info("Cache has been cleared!")
    }

}