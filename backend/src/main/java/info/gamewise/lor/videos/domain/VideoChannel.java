package info.gamewise.lor.videos.domain;

public final class VideoChannel {
    private final String name;

    public VideoChannel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "VideoChannel{" +
                "name='" + name + '\'' +
                '}';
    }
}
