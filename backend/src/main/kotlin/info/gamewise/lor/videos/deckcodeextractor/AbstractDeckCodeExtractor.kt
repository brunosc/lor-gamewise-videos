package info.gamewise.lor.videos.deckcodeextractor

abstract class AbstractDeckCodeExtractor {

    protected fun descriptionAsStream(description: String): List<String> {
        val splitBySpace = listOf(*description.split(" ".toRegex()).toTypedArray())
        return splitBySpace
            .map { splitByEnter(it) }
            .flatten()
    }

    private fun splitByEnter(text: String): List<String> {
        return listOf(*text.split("\n".toRegex()).toTypedArray())
    }
}