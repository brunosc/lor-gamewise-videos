package info.gamewise.lor.videos.domain;

import java.time.LocalDateTime;

public class AppSettings {
    private final LocalDateTime updatedAt;

    public AppSettings(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
