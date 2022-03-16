package info.gamewise.lor.videos.port.out

interface VideoIsInDatabaseUseCase {
    fun isInDatabase(videoId: String): Boolean
}