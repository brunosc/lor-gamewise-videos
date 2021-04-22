package info.gamewise.lor.videos.scheduler;

import info.gamewise.lor.videos.usecase.SaveVideoUseCase;
import info.gamewise.lor.videos.usecase.VideosNotInDatabaseUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "lor.you-tube.scheduler.enabled", havingValue = "true")
class YouTubeVideosScheduler {

    private static final int HOUR = 2;
    private static final int IMPORT_RATE = HOUR * 60 * 60 * 1000;

    private final VideosNotInDatabaseUseCase videosNotInDatabaseUseCase;
    private final SaveVideoUseCase saveVideoUseCase;

    YouTubeVideosScheduler(VideosNotInDatabaseUseCase videosNotInDatabaseUseCase, SaveVideoUseCase saveVideoUseCase) {
        this.videosNotInDatabaseUseCase = videosNotInDatabaseUseCase;
        this.saveVideoUseCase = saveVideoUseCase;
    }

    @Scheduled(fixedRate = IMPORT_RATE)
    void fetch() {
        final var latestVideos = videosNotInDatabaseUseCase.fetchNewVideos();
        latestVideos.forEach(saveVideoUseCase::save);
    }

}
