package info.gamewise.lor.videos.domain;

public final class VideoRegion implements Comparable<VideoRegion> {
    private final String code;
    private final String description;

    public VideoRegion(String code, String description) {
        this.code = code;
        this.description = description;
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
