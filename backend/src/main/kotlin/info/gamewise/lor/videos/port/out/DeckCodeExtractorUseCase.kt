package info.gamewise.lor.videos.port.out

interface DeckCodeExtractorUseCase {
    fun extractDeckCode(videoDescription: String?): String?
}