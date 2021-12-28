package info.gamewise.lor.videos.port.out;

import info.gamewise.lor.videos.domain.json.Channel;

import java.util.List;
import java.util.Optional;

public interface GetChannelsPort {
    List<Channel> getChannels();
    Optional<Channel> getChannelByCode(String channelCode);
}
