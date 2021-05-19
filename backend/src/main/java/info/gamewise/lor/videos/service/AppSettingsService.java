package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.domain.AppSettings;
import info.gamewise.lor.videos.scheduler.YouTubeVideosScheduler;
import info.gamewise.lor.videos.usecase.GetAppSettingsUseCase;
import org.springframework.stereotype.Service;

@Service
class AppSettingsService implements GetAppSettingsUseCase {

    @Override
    public AppSettings getAppSettings() {
        return new AppSettings(YouTubeVideosScheduler.updatedAt());
    }

}
