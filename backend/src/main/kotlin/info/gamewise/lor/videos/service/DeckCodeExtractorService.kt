package info.gamewise.lor.videos.service

import info.gamewise.lor.videos.deckcodeextractor.DeckCodeExtractor
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckCodeExtractor
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckLinkExtractor
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithRegisteredDeckExtractor

object DeckCodeExtractorService {

    private val EXTRACTORS = listOf<DeckCodeExtractor>(
        DescriptionWithDeckCodeExtractor(),
        DescriptionWithDeckLinkExtractor(),
        DescriptionWithRegisteredDeckExtractor()
    )

    @JvmStatic
    fun extractDeckCode(videoDescription: String?): String? {
        for (extractor in EXTRACTORS) {
            val deckCode: String? = extractor.extract(videoDescription!!)
            if (deckCode != null) {
                return sanitizeDeckCode(deckCode)
                //return deckCode.map { obj: String? -> sanitizeDeckCode() }
            }
        }
        return null
    }

    private fun sanitizeDeckCode(deckCode: String): String {
        return deckCode.replace("#", "")
    }
}