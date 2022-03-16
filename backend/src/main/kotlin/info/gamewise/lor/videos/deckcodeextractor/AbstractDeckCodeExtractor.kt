package info.gamewise.lor.videos.deckcodeextractor

import java.util.stream.Collectors
import java.util.stream.Stream

abstract class AbstractDeckCodeExtractor {

    protected fun descriptionAsStream(description: String): Stream<String> {
        // TODO refactor
        val splitBySpace = listOf(*description.split(" ".toRegex()).toTypedArray())
        return splitBySpace.stream()
            .map { text: String -> splitByEnter(text) }
            .collect(Collectors.toUnmodifiableList())
            .stream()
            .flatMap { obj: List<String?> -> obj.stream() }
    }

    private fun splitByEnter(text: String): List<String> {
        return listOf(*text.split("\n".toRegex()).toTypedArray())
    }
}