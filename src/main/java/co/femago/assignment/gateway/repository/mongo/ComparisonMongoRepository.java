package co.femago.assignment.gateway.repository.mongo;

import co.femago.assignment.gateway.repository.entity.ComparisonEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonMongoRepository extends MongoRepository<ComparisonEntity, String> {

  Optional<ComparisonEntity> findByComparisonId(String comparisonId);
}
