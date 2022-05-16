package info.gamewise.lor.videos.service

import info.gamewise.lor.videos.deckcodeextractor.DeckCodeExtractor
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckCodeExtractor
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithDeckLinkExtractor
import info.gamewise.lor.videos.deckcodeextractor.DescriptionWithRegisteredDeckExtractor
import info.gamewise.lor.videos.port.out.DeckCodeExtractorUseCase
import org.springframework.stereotype.Service

@Service
class DeckCodeExtractorService(private val extractors: List<DeckCodeExtractor>): DeckCodeExtractorUseCase {

    override fun extractDeckCode(videoDescription: String?): String? {
        for (extractor in extractors) {
            val deckCode: String? = extractor.extract(videoDescription!!)
            if (deckCode != null) {
                return sanitizeDeckCode(deckCode)
            }
        }
        return null
    }

    private fun sanitizeDeckCode(deckCode: String): String {
        return deckCode.replace("#", "")
    }
}