package info.gamewise.lor.videos.port.out;

import info.gamewise.lor.videos.port.out.VideosNotInDatabaseUseCase.NewVideo;

public interface SaveVideoUseCase {
    void save(NewVideo newVideo);
}
