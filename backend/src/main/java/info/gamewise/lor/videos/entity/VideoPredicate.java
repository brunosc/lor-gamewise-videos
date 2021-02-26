package info.gamewise.lor.videos.entity;

import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRRegion;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import info.gamewise.lor.videos.usecase.GetVideosUseCase.SearchParams;
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

        if (!isEmpty(params.getRegions())) {
            for (String region : params.getRegions()) {
                predicate.and(q.regions.contains(LoRRegion.fromCode(region)));
            }
        }

        if (!isEmpty(params.getChampions())) {
            for (String champion : params.getChampions()) {
                predicate.and(q.champions.contains(LoRChampion.fromCardCode(champion)));
            }
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
