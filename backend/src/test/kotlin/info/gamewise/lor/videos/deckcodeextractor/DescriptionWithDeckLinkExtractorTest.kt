package info.gamewise.lor.videos.deckcodeextractor

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DescriptionWithDeckLinkExtractorTest {
    private val extractor: DeckCodeExtractor = DescriptionWithDeckLinkExtractor()

    @Test
    fun mobaDecksCode() {
        val deckCode = "CEBQCAYFCAFAGCICBEISGOCLJRHVIYABAECTCAICAMEVKWYA"
        val description: String = """
            A brief title about the video
            
            https://lor.mobalytics.gg/decks/code/$deckCode
            
            Thank you!
            Have a nice day.
        """.trimIndent()

        val extractedDeckCode: String? = extractor.extract(description)
        assertNotNull(extractedDeckCode)
        assertEquals(deckCode, extractedDeckCode)
    }

    @Test
    fun mobaInvalidDecksCodeUrl() {
        val deckCode = "CEBQCAYFCAFAGCICBEISGOCLJRHVIYABAECTCAICAMEVKWYA"
        val description: String = """
            A brief title about the video
            
            https://lor.mobalytics.gg/decks/$deckCode
            
            Thank you!
            Have a nice day.
        """.trimIndent()
        val extractedDeckCode: String? = extractor.extract(description)
        assertNull(extractedDeckCode)
    }

    @Test
    fun onlyDeckCode() {
        val deckCode = "CEBQCAYFCAFAGCICBEISGOCLJRHVIYABAECTCAICAMEVKWYA"
        val description: String = """
            A brief title about the video
            
            Deck code: $deckCode
            
            Thank you!
            Have a nice day.
        """.trimIndent()
        val extractedDeckCode: String? = extractor.extract(description)
        assertNull(extractedDeckCode)
    }
}