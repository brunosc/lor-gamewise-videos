package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.config.JsonDataProperties;
import info.gamewise.lor.videos.config.LoRCacheConfig;
import info.gamewise.lor.videos.domain.json.Champion;
import info.gamewise.lor.videos.domain.json.Channel;
import info.gamewise.lor.videos.port.out.GetChampionsPort;
import info.gamewise.lor.videos.port.out.GetChannelsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
class GetJsonDataService implements GetChannelsPort, GetChampionsPort {

    private static final Logger LOG = LoggerFactory.getLogger(GetJsonDataService.class);
    private static final AtomicReference<List<Channel>> CHANNELS = new AtomicReference<>();
    private static final AtomicReference<List<Champion>> CHAMPIONS = new AtomicReference<>();

    private final JsonDataProperties properties;

    GetJsonDataService(JsonDataProperties properties) {
        this.properties = properties;
    }

    @Override
    @Cacheable(LoRCacheConfig.CHAMPIONS)
    public List<Champion> getChampions() {
        LOG.info("Fetching champions from {}", properties.getChampionsUrl());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Champion>> response = restTemplate.exchange(properties.getChampionsUrl(), GET, httpEntity(), new ParameterizedTypeReference<>() {});

        CHAMPIONS.set(response.getBody());
        return CHAMPIONS.get();
    }

    @Override
    public Optional<Champion> getChampionByName(String champion) {
        if (isEmpty(CHAMPIONS.get())) {
            getChampions();
        }
        return CHAMPIONS.get().stream().filter(c -> champion.equals(c.code())).findFirst();
    }

    @Override
    @Cacheable(LoRCacheConfig.CHANNELS)
    public List<Channel> getChannels() {
        LOG.info("Fetching channels from {}", properties.getChannelsUrl());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Channel>> response = restTemplate.exchange(properties.getChannelsUrl(), GET, httpEntity(), new ParameterizedTypeReference<>() {});

        CHANNELS.set(response.getBody());
        return CHANNELS.get();
    }

    @Override
    public Optional<Channel> getChannelByCode(String channelCode) {
        if (isEmpty(CHANNELS.get())) {
            getChannels();
        }
        return CHANNELS.get().stream().filter(c -> channelCode.equals(c.code())).findFirst();
    }

    private HttpEntity<?> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }
}
