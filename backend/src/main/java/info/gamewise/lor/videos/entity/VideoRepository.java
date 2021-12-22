package info.gamewise.lor.videos.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

interface VideoRepository extends MongoRepository<VideoJpaEntity, String>, QuerydslPredicateExecutor<VideoJpaEntity> {
    boolean existsByVideoId(String videoId);
    List<VideoJpaEntity> findByChannel(String channel);
}
