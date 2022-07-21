package info.gamewise.lor.videos.entity.champion

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "champions")
internal class ChampionJpaEntity(@Id var id: String? = null,
                                 var riotId: String? = null,
                                 var code: String? = null,
                                 var name: String? = null)