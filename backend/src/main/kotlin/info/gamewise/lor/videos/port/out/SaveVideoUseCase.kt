package info.gamewise.lor.videos.port.out

interface SaveVideoUseCase {
    fun save(newVideos: List<NewVideo>)
}