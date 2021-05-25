package info.gamewise.lor.videos.domain;

import java.time.LocalDate;
import java.util.List;

public class ChannelStatistics {

    private final LocalDate startedAt;
    private final List<NameCount> champions;
    private final List<NameCount> regions;

    public ChannelStatistics(LocalDate startedAt, List<NameCount> champions, List<NameCount> regions) {
        this.startedAt = startedAt;
        this.champions = champions;
        this.regions = regions;
    }

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public List<NameCount> getChampions() {
        return champions;
    }

    public List<NameCount> getRegions() {
        return regions;
    }

    public static class NameCount {
        private final String name;
        private final long count;

        public NameCount(String name, long count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public long getCount() {
            return count;
        }
    }

}
