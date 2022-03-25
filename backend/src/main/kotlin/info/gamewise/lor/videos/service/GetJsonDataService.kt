package info.gamewise.lor.videos.service

import info.gamewise.lor.videos.config.JsonDataProperties
import info.gamewise.lor.videos.config.LoRCacheConfig
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.GetChampionsPort
import info.gamewise.lor.videos.port.out.GetChannelsPort
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestTemplate
import java.util.concurrent.atomic.AtomicReference

@Service
internal open class GetJsonDataService(private val properties: JsonDataProperties) : GetChannelsPort, GetChampionsPort {

    private val log = LoggerFactory.getLogger(GetJsonDataService::class.java)
    private val channels = AtomicReference<List<Channel>>(emptyList())
    private val champions = AtomicReference<List<Champion>>(emptyList())

    @Cacheable(LoRCacheConfig.CHAMPIONS)
    override fun getChampions(): List<Champion> {
        log.info("Fetching champions from {}", properties.championsUrl)
        val restTemplate = RestTemplate()
        val response: ResponseEntity<List<Champion>> = restTemplate.exchange(
            properties.championsUrl,
            HttpMethod.GET,
            httpEntity(),
            object : ParameterizedTypeReference<List<Champion>>() {})
        champions.set(response.body)
        return champions.get()
    }

    override fun getChampionByName(champion: String): Champion? {
        if (champions.get().isEmpty()) {
            getChampions()
        }

        return champions.get().firstOrNull { champion == it.code }
    }

    @Cacheable(LoRCacheConfig.CHANNELS)
    override fun getChannels(): List<Channel> {
        log.info("Fetching channels from {}", properties.channelsUrl)
        val restTemplate = RestTemplate()
        val response: ResponseEntity<List<Channel>> = restTemplate.exchange(
            properties.channelsUrl,
            HttpMethod.GET,
            httpEntity(),
            object : ParameterizedTypeReference<List<Channel>>() {})
        channels.set(response.body)
        return channels.get()
    }

    override fun getChannelByCode(channelCode: String): Channel? {
        if (channels.get().isEmpty()) {
            getChannels()
        }

        return channels.get().firstOrNull { channelCode == it.code }
    }

    private fun httpEntity(): HttpEntity<*> {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity<Any>(headers)
    }

}