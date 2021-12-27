package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.config.LoRCacheConfig;
import info.gamewise.lor.videos.domain.ChampionRecord;
import info.gamewise.lor.videos.port.out.GetChampionsPort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
class GetChampionsService implements GetChampionsPort {

    private static final String URL = "https://api.jsonbin.io/b/61c1e160435982298611f9d8";
    private static final AtomicReference<List<ChampionRecord>> CHAMPIONS = new AtomicReference<>();

    @Override
    @Cacheable(LoRCacheConfig.CHAMPIONS)
    public List<ChampionRecord> getChampions() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ChampionRecord>> response = restTemplate.exchange(URL, GET, httpEntity(), new ParameterizedTypeReference<>() {});

        CHAMPIONS.set(response.getBody());
        return CHAMPIONS.get();
    }

    @Override
    public Optional<ChampionRecord> getChampionByName(String champion) {
        if (isEmpty(CHAMPIONS.get())) {
            getChampions();
        }
        return CHAMPIONS.get().stream().filter(c -> champion.equals(c.code())).findFirst();
    }

    private HttpEntity<?> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

}
