package info.gamewise.lor.videos.domain;

public final class VideoChannel {
    private final String name;
    private final String code;

    public VideoChannel(LoRChannel channel) {
        this.name = channel.name();
        this.code = channel.code();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "VideoChannel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
