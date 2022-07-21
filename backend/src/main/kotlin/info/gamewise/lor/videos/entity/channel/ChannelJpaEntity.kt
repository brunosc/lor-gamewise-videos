package info.gamewise.lor.videos.entity.channel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "channels")
internal class ChannelJpaEntity(@Id var id: String? = null,
                                var code: String? = null,
                                var name: String? = null,
                                var playlistId: String? = null)