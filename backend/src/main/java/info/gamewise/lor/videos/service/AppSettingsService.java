package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.port.in.GetAppSettingsUseCase;
import info.gamewise.lor.videos.scheduler.YouTubeVideosScheduler;
import org.springframework.stereotype.Service;

@Service
class AppSettingsService implements GetAppSettingsUseCase {

    @Override
    public AppSettings getAppSettings() {
        return new AppSettings(YouTubeVideosScheduler.updatedAt());
    }

}
