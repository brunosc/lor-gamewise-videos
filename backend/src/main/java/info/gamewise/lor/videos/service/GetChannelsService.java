package info.gamewise.lor.videos.service;

import info.gamewise.lor.videos.config.LoRCacheConfig;
import info.gamewise.lor.videos.domain.LoRChannel;
import info.gamewise.lor.videos.port.out.GetChannelsPort;
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
class GetChannelsService implements GetChannelsPort {

    private static final String URL = "https://api.jsonbin.io/b/61bc3d49bccca038dfab2775/3";
    private static final AtomicReference<List<LoRChannel>> CHANNELS = new AtomicReference<>();

    @Override
    @Cacheable(LoRCacheConfig.CHANNELS)
    public List<LoRChannel> getChannels() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!! GET CHANNELS !!!!!!!!!!!!!!!!!!!!!!");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<LoRChannel>> response = restTemplate.exchange(URL, GET, httpEntity(), new ParameterizedTypeReference<>() {});

        CHANNELS.set(response.getBody());
        return CHANNELS.get();
    }

    @Override
    public Optional<LoRChannel> getChannelByCode(String channelCode) {
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
