package info.gamewise.lor.videos.deckcodeextractor

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class DescriptionWithDeckCodeExtractorTest {

    private val extractor: DeckCodeExtractor = DescriptionWithDeckCodeExtractor()

    @Test
    fun deckCodeAndUrl() {
        val deckCode = "CEBQCAYFCAFAGCICBEISGOCLJRHVIYABAECTCAICAMEVKWYA"
        val description: String = """
            A brief title about the video
            
            https://lor.mobalytics.gg/decks/code/ANY-DECK-CODE
            
            Deck code: $deckCode
            
            Thank you!
            Have a nice day.
        """.trimIndent()

        val deckCodeOpt: String? = extractor.extract(description)
        assertNotNull(deckCodeOpt)
        assertEquals(deckCodeOpt, deckCode)
    }

    @Test
    fun onlyUrlShouldBeEmpty() {
        val description: String = """
            A brief title about the video
            
            https://lor.mobalytics.gg/decks/code/ANY-DECK-CODE
            
            Thank you!
            Have a nice day.
        """.trimIndent()

        val deckCode: String? = extractor.extract(description)
        Assertions.assertNull(deckCode)
    }
}