package info.gamewise.lor.videos

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCaching
@EnableScheduling
@SpringBootApplication
open class LoRVideosApplication

fun main(args: Array<String>) {
    SpringApplication.run(LoRVideosApplication::class.java, *args)
}