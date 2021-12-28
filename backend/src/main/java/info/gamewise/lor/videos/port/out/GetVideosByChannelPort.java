package info.gamewise.lor.videos.port.out;

import info.gamewise.lor.videos.domain.LoRVideo;

import java.util.List;

public interface GetVideosByChannelPort {
    List<LoRVideo> videosByChannel(String channel);
}
