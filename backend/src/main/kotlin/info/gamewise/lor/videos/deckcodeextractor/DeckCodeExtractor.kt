package info.gamewise.lor.videos.deckcodeextractor

interface DeckCodeExtractor {
    fun extract(videoDescription: String): String?
}