package info.gamewise.lor.videos.domain;

import java.time.LocalDate;
import java.util.List;

public record ChannelStatistics(LocalDate startedAt,
                                List<NameCount> champions,
                                List<NameCount> regions) {

    public record NameCount(String name, long count) {
    }

}
