package info.gamewise.lor.videos.port.out;

import info.gamewise.lor.videos.port.out.VideosNotInDatabasePort.NewVideo;

import java.util.List;

public interface SaveVideoUseCase {
    void save(List<NewVideo> newVideos);
}
