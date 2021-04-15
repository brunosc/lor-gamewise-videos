package info.gamewise.lor.videos.scheduler;

import info.gamewise.lor.videos.usecase.SaveVideoUseCase;
import info.gamewise.lor.videos.usecase.VideosNotInDatabaseUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "lor.you-tube.scheduler.enabled", havingValue = "true")
class YouTubeVideosScheduler {

    private static final int HOUR = 2;
    private static final int IMPORT_RATE = HOUR * 60 * 60 * 1000;
    private static final Logger LOG = LoggerFactory.getLogger(YouTubeVideosScheduler.class);

    private final VideosNotInDatabaseUseCase videosNotInDatabaseUseCase;
    private final SaveVideoUseCase saveVideoUseCase;

    YouTubeVideosScheduler(VideosNotInDatabaseUseCase videosNotInDatabaseUseCase, SaveVideoUseCase saveVideoUseCase) {
        this.videosNotInDatabaseUseCase = videosNotInDatabaseUseCase;
        this.saveVideoUseCase = saveVideoUseCase;
    }

    @Scheduled(fixedRate = IMPORT_RATE)
    void fetch() {
        LOG.info("Fetching videos from YouTube...");
        final var latestVideos = videosNotInDatabaseUseCase.fetch();

        LOG.info("{} videos to save", latestVideos.size());
        latestVideos.forEach(saveVideoUseCase::save);

        LOG.info("Import has been finished.");
    }

}
