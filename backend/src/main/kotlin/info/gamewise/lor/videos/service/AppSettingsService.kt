package info.gamewise.lor.videos.service

import info.gamewise.lor.videos.port.`in`.AppSettings
import info.gamewise.lor.videos.port.`in`.GetAppSettingsUseCase
import info.gamewise.lor.videos.scheduler.YouTubeVideosScheduler.Companion.updatedAt
import org.springframework.stereotype.Service

@Service
internal class AppSettingsService : GetAppSettingsUseCase {

    override fun getAppSettings(): AppSettings {
        return AppSettings(updatedAt())
    }

}