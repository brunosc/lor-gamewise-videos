package info.gamewise.lor.videos.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "lor.you-tube.local-server")
class LocalServerProperties {
    lateinit var host: String
    lateinit var port: String
}