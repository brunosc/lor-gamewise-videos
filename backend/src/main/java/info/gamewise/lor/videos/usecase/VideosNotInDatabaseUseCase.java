package info.gamewise.lor.videos.usecase;

import com.github.brunosc.fetcher.domain.VideoDetails;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import info.gamewise.lor.videos.domain.Channel;

import java.util.List;
import java.util.Set;

public interface VideosNotInDatabaseUseCase {

    List<NewVideo> fetch();

    class NewVideo {
        private final String deckCode;
        private final VideoDetails details;
        private final Channel channel;
        private final Set<LoRRegion> regions;
        private final Set<LoRChampion> champions;

        public NewVideo(String deckCode, VideoDetails details, Channel channel, Set<LoRRegion> regions, Set<LoRChampion> champions) {
            this.deckCode = deckCode;
            this.details = details;
            this.channel = channel;
            this.regions = regions;
            this.champions = champions;
        }

        public String getDeckCode() {
            return deckCode;
        }

        public VideoDetails getDetails() {
            return details;
        }

        public Channel getChannel() {
            return channel;
        }

        public Set<LoRRegion> getRegions() {
            return regions;
        }

        public Set<LoRChampion> getChampions() {
            return champions;
        }
    }
}
