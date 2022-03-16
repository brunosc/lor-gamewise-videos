package info.gamewise.lor.videos.deckcodeextractor

class DescriptionWithDeckCodeExtractor : AbstractDeckCodeExtractor(), DeckCodeExtractor {

    override fun extract(videoDescription: String): String? {
        // TODO refactor
        return descriptionAsStream(videoDescription)
            .filter { word: String -> filterDeckCode(word) }
            .findFirst()
            .orElse(null)
//        return descriptionAsStream(videoDescription)
//            .filter(Predicate { word: String -> filterDeckCode(word) })
//            .findFirst()
    }

    private fun filterDeckCode(word: String): Boolean {
        return word.length >= MIN_CODE_LENGTH && !word.contains("http")
    }

    companion object {
        private const val MIN_CODE_LENGTH = 43
    }
}