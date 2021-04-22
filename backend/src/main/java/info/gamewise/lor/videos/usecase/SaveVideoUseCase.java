package info.gamewise.lor.videos.usecase;

import info.gamewise.lor.videos.usecase.VideosNotInDatabaseUseCase.NewVideo;

public interface SaveVideoUseCase {
    void save(NewVideo newVideo);
}
