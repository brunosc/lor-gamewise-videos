package info.gamewise.lor.videos.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "lor.json-data")
class JsonDataProperties {
    lateinit var channelsUrl: String
    lateinit var championsUrl: String
}