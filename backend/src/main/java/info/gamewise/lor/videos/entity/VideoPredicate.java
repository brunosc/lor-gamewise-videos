package info.gamewise.lor.videos.entity;

import com.github.brunosc.lor.domain.LoRRegion;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import info.gamewise.lor.videos.port.in.GetVideosUseCase.SearchParams;
import org.springframework.data.domain.Sort;

import static java.util.Collections.singletonList;
import static org.springframework.util.CollectionUtils.isEmpty;

class VideoPredicate {

    private final SearchParams params;
    private final Sort sort;

    VideoPredicate(SearchParams params) {
        this.params = params;
        this.sort = sortByPublishedAt();
    }

    Predicate predicate() {
        QVideoJpaEntity q = new QVideoJpaEntity("videos");
        BooleanBuilder predicate = new BooleanBuilder();

        if (!isEmpty(params.regions())) {
            for (String region : params.regions()) {
                predicate.and(q.regions.contains(LoRRegion.fromCode(region)));
            }
        }

        if (!isEmpty(params.champions())) {
            for (String champion : params.champions()) {
                predicate.and(q.champions.contains(champion));
            }
        }

        if (!isEmpty(params.channels())) {
            predicate.and(q.channel.in(params.channels()));
        }

        return predicate;
    }

    Sort sort() {
        return this.sort;
    }

    private Sort sortByPublishedAt() {
        Sort.Order sortOrderPublishedAt = new Sort.Order(Sort.Direction.DESC, "publishedAt").ignoreCase();
        return Sort.by(singletonList(sortOrderPublishedAt));
    }
}
