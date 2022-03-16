package info.gamewise.lor.videos.port.`in`

import java.time.LocalDateTime

interface GetAppSettingsUseCase {
    fun getAppSettings(): AppSettings
}

data class AppSettings(val updatedAt: LocalDateTime)
