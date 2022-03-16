package info.gamewise.lor.videos.deckcodeextractor

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.http.*
import org.springframework.web.client.RestTemplate
import java.io.Serializable
import java.util.*
import java.util.function.Function

class DescriptionWithRegisteredDeckExtractor : AbstractDeckCodeExtractor(), DeckCodeExtractor {

    override fun extract(videoDescription: String): String? {
        return descriptionAsStream(videoDescription)
            .filter { word: String -> filterMobalyticsLink(word) }
            .findFirst()
            .map { url: String -> deckCodeFromRegisteredDeck(url) }
            .orElse(null)
    }

    private fun deckCodeFromRegisteredDeck(url: String): String? {
        val deckId = url.substring(url.lastIndexOf("/") + 1)
        val response = requestMobalytics(URL_DECK_BY_ID + deckId)
        return Optional.ofNullable(response.body)
            .map { obj: DeckResponse -> obj.exportUID }
            .orElse(null)
    }

    private fun requestMobalytics(url: String): ResponseEntity<DeckResponse> {
        val restTemplate = RestTemplate()
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity(), DeckResponse::class.java)
    }

    private fun httpEntity(): HttpEntity<*> {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity<Any>(headers)
    }

    private fun filterMobalyticsLink(word: String): Boolean {
        return word.contains(URL_DECKS) && !word.contains("code")
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class DeckResponse(val exportUID: String? = null) : Serializable

    companion object {
        private const val URL_MOBA = "https://lor.mobalytics.gg/"
        private const val URL_DECKS = URL_MOBA + "decks"
        private const val URL_DECK_BY_ID = URL_MOBA + "api/v2/decks/library/by-id/"
    }
}