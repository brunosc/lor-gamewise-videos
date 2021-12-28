package info.gamewise.lor.videos.domain;

import info.gamewise.lor.videos.domain.json.Channel;

public final class VideoChannel {
    private final String name;
    private final String code;

    public VideoChannel(Channel channel) {
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
