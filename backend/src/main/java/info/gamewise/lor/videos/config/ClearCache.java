package info.gamewise.lor.videos.config;

import org.springframework.cache.annotation.CacheEvict;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static info.gamewise.lor.videos.config.LoRCacheConfig.CHAMPIONS;
import static info.gamewise.lor.videos.config.LoRCacheConfig.CHANNELS;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@CacheEvict(value = { CHAMPIONS, CHANNELS })
public @interface ClearCache {
}
