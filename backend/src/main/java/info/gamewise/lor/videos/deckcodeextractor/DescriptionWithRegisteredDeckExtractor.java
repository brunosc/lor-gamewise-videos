package info.gamewise.lor.videos.deckcodeextractor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class DescriptionWithRegisteredDeckExtractor extends AbstractDeckCodeExtractor implements DeckCodeExtractor {

    private static final String URL_MOBA = "https://lor.mobalytics.gg/";
    private static final String URL_DECKS = URL_MOBA + "decks";
    private static final String URL_DECK_BY_ID = URL_MOBA + "api/v2/decks/library/by-id/";

    public static void main(String[] args) {
        DeckCodeExtractor extractor = new DescriptionWithRegisteredDeckExtractor();
        Optional<String> code = extractor.extract("https://lor.mobalytics.gg/decks/c18gl41509nhf1e6st80");
        code.ifPresentOrElse(System.out::println, () -> System.out.println("no deck code!"));
    }

    @Override
    public Optional<String> extract(String videoDescription) {
        return descriptionAsStream(videoDescription)
                .filter(this::filterMobalyticsLink)
                .findFirst()
                .map(this::deckCodeFromRegisteredDeck);
    }

    private String deckCodeFromRegisteredDeck(String url) {
        String deckId = url.substring(url.lastIndexOf("/") + 1);
        ResponseEntity<DeckResponse> response = requestMobalytics(URL_DECK_BY_ID + deckId);

        return ofNullable(response.getBody())
                .map(DeckResponse::getExportUID)
                .orElse(null);
    }

    private ResponseEntity<DeckResponse> requestMobalytics(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, GET, httpEntity(), DeckResponse.class);
    }

    private HttpEntity<?> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    private boolean filterMobalyticsLink(String word) {
        return word.contains(URL_DECKS) && !word.contains("code");
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class DeckResponse implements Serializable {
        private String exportUID;

        public String getExportUID() {
            return exportUID;
        }

        public void setExportUID(String exportUID) {
            this.exportUID = exportUID;
        }
    }
}
