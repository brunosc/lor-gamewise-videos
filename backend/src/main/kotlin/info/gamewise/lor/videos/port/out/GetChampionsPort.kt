package info.gamewise.lor.videos.port.out

import info.gamewise.lor.videos.domain.json.Champion
import java.util.*

interface GetChampionsPort {
    fun getChampions(): List<Champion>
    fun getChampionByName(champion: String): Champion?
}