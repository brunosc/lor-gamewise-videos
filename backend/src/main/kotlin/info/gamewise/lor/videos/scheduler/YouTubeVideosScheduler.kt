package info.gamewise.lor.videos.scheduler

import info.gamewise.lor.videos.port.out.SaveVideoUseCase
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.atomic.AtomicReference

private const val HOUR = 2
private const val IMPORT_RATE = HOUR * 60 * 60 * 1000

@Component
@ConditionalOnProperty(value = ["lor.you-tube.scheduler.enabled"], havingValue = "true")
internal class YouTubeVideosScheduler(private val videosNotInDatabaseUseCase: VideosNotInDatabasePort,
                                      private val saveVideoUseCase: SaveVideoUseCase) {

    @Scheduled(fixedRate = IMPORT_RATE.toLong())
    fun fetch() {
        val latestVideos = videosNotInDatabaseUseCase.fetchNewVideos()
        saveVideoUseCase.save(latestVideos)
        UPDATED_AT.set(LocalDateTime.now(ZoneOffset.UTC))
    }

    companion object {
        private val UPDATED_AT = AtomicReference(LocalDateTime.now(ZoneOffset.UTC))

        @JvmStatic
        fun updatedAt(): LocalDateTime {
            return UPDATED_AT.get()
        }
    }
}