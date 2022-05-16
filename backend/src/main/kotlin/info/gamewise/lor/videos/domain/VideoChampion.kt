package info.gamewise.lor.videos.domain

import info.gamewise.lor.videos.domain.json.Champion

class VideoChampion(champion: Champion) {
    val code: String
    val name: String
    val urlImgName: String

    init {
        this.code = champion.code
        this.name = champion.name
        this.urlImgName = buildUrlImgName(champion)
    }

    override fun toString(): String {
        return "VideoChampion{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", urlImgName='" + urlImgName + '\'' +
                '}'
    }

    private fun buildUrlImgName(champion: Champion): String {
        return champion
            .name
            .replace(" ", "")
    }
}