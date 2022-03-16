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

    @Cacheable(LoRCacheConfig.CHAMPIONS)
    override fun getChampions(): List<Champion> {
        LOG.info("Fetching champions from {}", properties.championsUrl)
        val restTemplate = RestTemplate()
        val response: ResponseEntity<List<Champion>> = restTemplate.exchange(
            properties.championsUrl,
            HttpMethod.GET,
            httpEntity(),
            object : ParameterizedTypeReference<List<Champion>>() {})
        CHAMPIONS.set(response.body)
        return CHAMPIONS.get()
    }

    override fun getChampionByName(champion: String): Champion? {
        if (CollectionUtils.isEmpty(CHAMPIONS.get())) {
            getChampions()
        }

        return CHAMPIONS.get().firstOrNull { champion == it.code }
    }

    @Cacheable(LoRCacheConfig.CHANNELS)
    override fun getChannels(): List<Channel> {
        LOG.info("Fetching channels from {}", properties.channelsUrl)
        val restTemplate = RestTemplate()
        val response: ResponseEntity<List<Channel>> = restTemplate.exchange(
            properties.channelsUrl,
            HttpMethod.GET,
            httpEntity(),
            object : ParameterizedTypeReference<List<Channel>>() {})
        CHANNELS.set(response.body)
        return CHANNELS.get()
    }

    override fun getChannelByCode(channelCode: String): Channel? {
        if (CollectionUtils.isEmpty(CHANNELS.get())) {
            getChannels()
        }

        return CHANNELS.get().firstOrNull { channelCode == it.code }
    }

    private fun httpEntity(): HttpEntity<*> {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity<Any>(headers)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(GetJsonDataService::class.java)
        private val CHANNELS = AtomicReference<List<Channel>>()
        private val CHAMPIONS = AtomicReference<List<Champion>>()
    }
}