package info.gamewise.lor.videos.deckcodeextractor

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.io.Serializable
import java.util.Optional.ofNullable

private const val URL_MOBA = "https://lor.mobalytics.gg/"
private const val URL_DECKS = "${URL_MOBA}decks"
private const val URL_DECK_BY_ID = "${URL_MOBA}api/v2/decks/library/by-id/"

@Component
class DescriptionWithRegisteredDeckExtractor : AbstractDeckCodeExtractor(), DeckCodeExtractor {

    private val log = LoggerFactory.getLogger(DescriptionWithRegisteredDeckExtractor::class.java)

    override fun extract(videoDescription: String): String? {
        val mobaUrl = descriptionAsStream(videoDescription)
            .firstOrNull { filterMobalyticsLink(it) }
        return deckCodeFromRegisteredDeck(mobaUrl)
    }

    private fun filterMobalyticsLink(word: String): Boolean {
        return word.contains(URL_DECKS) && !word.contains("code")
    }

    private fun deckCodeFromRegisteredDeck(url: String?): String? {
        if (url == null) {
            return null
        }

        val deckId = url.substring(url.lastIndexOf("/") + 1)
        val response = requestMobalytics(URL_DECK_BY_ID + deckId)

        if (response.statusCode != HttpStatus.OK) {
            log.error("Deck not found for the URL={}", url)
            return null
        }

        return ofNullable(response.body)
            .map { it.exportUID }
            .orElse(null)
    }

    private fun requestMobalytics(url: String): ResponseEntity<DeckResponse> {
        return try {
            val restTemplate = RestTemplate()
            restTemplate.exchange(url, HttpMethod.GET, httpEntity(), DeckResponse::class.java)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    private fun httpEntity(): HttpEntity<*> {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity<Any>(headers)
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class DeckResponse(var exportUID: String? = null) : Serializable

}