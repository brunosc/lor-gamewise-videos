package info.gamewise.lor.videos.deckcodeextractor

class DescriptionWithDeckLinkExtractor : AbstractDeckCodeExtractor(), DeckCodeExtractor {

    override fun extract(videoDescription: String): String? {
        return descriptionAsStream(videoDescription)
            .filter { word: String -> filterMobalyticsLink(word) }
            .findFirst()
            .map { url: String -> deckCodeFromMobaLinkUrl(url) }
            .orElse(null)
    }

    private fun filterMobalyticsLink(word: String): Boolean {
        return word.contains("https://lor.mobalytics.gg/decks/code")
    }

    private fun deckCodeFromMobaLinkUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }
}