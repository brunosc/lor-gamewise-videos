package info.gamewise.lor.videos.service;

import com.github.brunosc.lor.domain.LoRChampion;
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

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
class GetChampionsService implements GetChampionsPort {

    private static final String URL = "https://api.jsonbin.io/b/61c1e160435982298611f9d8";

    @Override
    @Cacheable(LoRCacheConfig.CHAMPIONS)
    public List<ChampionRecord> getChampions() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ChampionRecord>> response = restTemplate.exchange(URL, GET, httpEntity(), new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    private HttpEntity<?> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

}
