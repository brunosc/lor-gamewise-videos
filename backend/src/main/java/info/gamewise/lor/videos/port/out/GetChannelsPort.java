package info.gamewise.lor.videos.port.out;

import info.gamewise.lor.videos.domain.LoRChannel;

import java.util.List;
import java.util.Optional;

public interface GetChannelsPort {
    List<LoRChannel> getChannels();
    Optional<LoRChannel> getChannelByCode(String channelCode);
}
