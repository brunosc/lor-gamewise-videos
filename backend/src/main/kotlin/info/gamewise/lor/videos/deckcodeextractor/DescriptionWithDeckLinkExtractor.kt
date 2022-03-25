package info.gamewise.lor.videos.deckcodeextractor

import org.springframework.stereotype.Component

@Component
class DescriptionWithDeckLinkExtractor : AbstractDeckCodeExtractor(), DeckCodeExtractor {

    override fun extract(videoDescription: String): String? {
        val mobaUrl = descriptionAsStream(videoDescription)
            .firstOrNull { filterMobalyticsLink(it) }
        return deckCodeFromMobaLinkUrl(mobaUrl)
    }

    private fun filterMobalyticsLink(word: String): Boolean {
        return word.contains("https://lor.mobalytics.gg/decks/code")
    }

    private fun deckCodeFromMobaLinkUrl(url: String?): String? {
        return url?.substring(url.lastIndexOf("/") + 1)
    }
}