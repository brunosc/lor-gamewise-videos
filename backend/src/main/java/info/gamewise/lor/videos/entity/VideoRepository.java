package info.gamewise.lor.videos.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface VideoRepository extends MongoRepository<VideoJpaEntity, String>, QuerydslPredicateExecutor<VideoJpaEntity> {
    boolean existsByVideoId(String videoId);
}
