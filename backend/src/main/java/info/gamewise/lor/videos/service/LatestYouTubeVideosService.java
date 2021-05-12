package info.gamewise.lor.videos.service;

import com.github.brunosc.fetcher.YouTubeFetcher;
import com.github.brunosc.fetcher.domain.VideoDetails;
import com.github.brunosc.fetcher.domain.YouTubeFetcherParams;
import info.gamewise.lor.videos.config.LocalServerProperties;
import info.gamewise.lor.videos.domain.Channel;
import info.gamewise.lor.videos.usecase.LatestYouTubeVideosUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
class LatestYouTubeVideosService implements LatestYouTubeVideosUseCase {

    private static final Long VIDEOS_BY_CHANNEL = 3L;
    private static final String CLIENT_SECRETS = "/client_secret.json";
    private static final Logger LOG = LoggerFactory.getLogger(LatestYouTubeVideosService.class);

    private final YouTubeFetcher youTubeFetcher;

    LatestYouTubeVideosService(LocalServerProperties localServerProperties) throws GeneralSecurityException, IOException {
        this.youTubeFetcher = buildYouTubeFetcher(localServerProperties);
    }

    private YouTubeFetcher buildYouTubeFetcher(LocalServerProperties localServerProperties) throws GeneralSecurityException, IOException {
        InputStream credentials = LatestYouTubeVideosService.class.getResourceAsStream(CLIENT_SECRETS);
        YouTubeFetcherParams params = new YouTubeFetcherParams.Builder(credentials)
                .withHost(localServerProperties.getHost())
                .withPort(localServerProperties.getPort())
                .build();
        return new YouTubeFetcher(params);
    }

    @Override
    public List<VideoDetails> latestVideosByChannel(Channel channel) {
        try {
            return youTubeFetcher.fetchByPlaylistId(channel.getPlaylistId(), VIDEOS_BY_CHANNEL);
        } catch (IOException e) {
            LOG.error("There was an error to fetch the latest videos.", e);
            return emptyList();
        }
    }
}
