package info.gamewise.lor.videos.entity.champion

import org.springframework.data.mongodb.repository.MongoRepository

internal interface ChampionRepository : MongoRepository<ChampionJpaEntity, String>