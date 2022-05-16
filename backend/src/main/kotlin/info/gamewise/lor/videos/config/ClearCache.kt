package info.gamewise.lor.videos.config

import org.springframework.cache.annotation.CacheEvict

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@CacheEvict(value = [ LoRCacheConfig.CHAMPIONS, LoRCacheConfig.CHANNELS ])
annotation class ClearCache 