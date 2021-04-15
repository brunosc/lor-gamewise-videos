package info.gamewise.lor.videos.domain;

import com.github.brunosc.lor.domain.LoRRegion;

public final class VideoRegion implements Comparable<VideoRegion> {
    private final String code;
    private final String description;

    public VideoRegion(LoRRegion region) {
        this.code = region.getCode();
        this.description = region.prettyName();
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(VideoRegion o) {
        return this.getCode().compareTo(o.getCode());
    }

    @Override
    public String toString() {
        return "VideoRegion{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
