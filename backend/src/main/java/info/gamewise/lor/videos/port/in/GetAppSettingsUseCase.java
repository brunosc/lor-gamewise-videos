package info.gamewise.lor.videos.port.in;

import java.time.LocalDateTime;

public interface GetAppSettingsUseCase {
    AppSettings getAppSettings();

    record AppSettings(LocalDateTime updatedAt) {
    }
}
