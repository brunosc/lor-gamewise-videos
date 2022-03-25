package info.gamewise.lor.videos.deckcodeextractor

import org.springframework.stereotype.Component

private const val MIN_CODE_LENGTH = 43

@Component
class DescriptionWithDeckCodeExtractor : AbstractDeckCodeExtractor(), DeckCodeExtractor {

    override fun extract(videoDescription: String): String? {
        return descriptionAsStream(videoDescription)
            .firstOrNull{ filterDeckCode(it) }
    }

    private fun filterDeckCode(word: String): Boolean {
        return word.length >= MIN_CODE_LENGTH && !word.contains("http")
    }

}