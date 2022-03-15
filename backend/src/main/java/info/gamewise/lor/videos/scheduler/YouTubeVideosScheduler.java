package info.gamewise.lor.videos.scheduler;

import info.gamewise.lor.videos.port.out.SaveVideoUseCase;
import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicReference;

@Component
@ConditionalOnProperty(value = "lor.you-tube.scheduler.enabled", havingValue = "true")
public class YouTubeVideosScheduler {

    private static final int HOUR = 2;
    private static final int IMPORT_RATE = HOUR * 60 * 60 * 1000;
    private static final AtomicReference<LocalDateTime> UPDATED_AT = new AtomicReference<>(LocalDateTime.now(ZoneOffset.UTC));

    private final VideosNotInDatabasePort videosNotInDatabaseUseCase;
    private final SaveVideoUseCase saveVideoUseCase;

    YouTubeVideosScheduler(VideosNotInDatabasePort videosNotInDatabaseUseCase, SaveVideoUseCase saveVideoUseCase) {
        this.videosNotInDatabaseUseCase = videosNotInDatabaseUseCase;
        this.saveVideoUseCase = saveVideoUseCase;
    }

    @Scheduled(fixedRate = IMPORT_RATE)
    void fetch() {
        final var latestVideos = videosNotInDatabaseUseCase.fetchNewVideos();
        saveVideoUseCase.save(latestVideos);
        UPDATED_AT.set(LocalDateTime.now(ZoneOffset.UTC));
    }

    public static LocalDateTime updatedAt() {
        return UPDATED_AT.get();
    }
}
