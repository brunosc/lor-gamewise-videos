package info.gamewise.lor.videos.domain

import info.gamewise.lor.videos.domain.json.Champion

class VideoChampion(champion: Champion) {

    private val championsWithIdAsUrl = arrayOf("GWEN", "NORRA")

    val code: String
    val name: String
    val urlImg: String

    init {
        this.code = champion.code
        this.name = champion.name
        this.urlImg = buildUrlImgName(champion)
    }

    override fun toString(): String {
        return "VideoChampion{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", urlImage='" + urlImg + '\'' +
                '}'
    }

    private fun buildUrlImgName(champion: Champion): String {
        if (championsWithIdAsUrl.contains(champion.code)) {
            return "https://cdn-lor.mobalytics.gg/production/images/round-cards/${champion.id}.png"
        }

        val cardName = when (champion.code) {
            "KAI_SA" -> "Kaisa"
            else -> champion.name.replace(" ", "")
        }

        return "https://cdn-lor.mobalytics.gg/production/images/champions/$cardName.webp"
    }
}