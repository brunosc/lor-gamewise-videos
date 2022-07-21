package info.gamewise.lor.videos.entity.champion

import info.gamewise.lor.videos.config.LoRCacheConfig
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.port.out.GetChampionsPort
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicReference

@Service
internal open class ChampionPersistenceAdapter(private val repository: ChampionRepository) : GetChampionsPort {

    private val champions = AtomicReference<List<Champion>>(emptyList())

    @Cacheable(LoRCacheConfig.CHAMPIONS)
    override fun getChampions(): List<Champion> {
        champions.set(repository.findAll().map { mapChampion(it) })
        return champions.get()
    }

    override fun getChampionByName(champion: String): Champion? {
        if (champions.get().isEmpty()) {
            getChampions()
        }

        return champions.get()
            .firstOrNull { champion == it.code }
    }

    private fun mapChampion(entity: ChampionJpaEntity): Champion {
        return Champion(
            id = entity.riotId!!,
            code = entity.code!!,
            name = entity.name!!,
        )
    }
}