package info.gamewise.lor.videos.deckcodeextractor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableList;

abstract class AbstractDeckCodeExtractor {

    protected Stream<String> descriptionAsStream(String description) {
        List<String> splitBySpace = List.of(description.split(" "));
        return splitBySpace.stream()
                .map(this::splitByEnter)
                .collect(toUnmodifiableList())
                .stream()
                .flatMap(Collection::stream);
    }

    private List<String> splitByEnter(String text) {
        return List.of(text.split("\n"));
    }
}
