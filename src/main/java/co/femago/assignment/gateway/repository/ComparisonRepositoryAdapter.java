package co.femago.assignment.gateway.repository;

import co.femago.assignment.domain.exception.ComparisionNotFoundException;
import co.femago.assignment.domain.exception.DiffAlreadyCalculatedException;
import co.femago.assignment.domain.model.Comparator;
import co.femago.assignment.domain.port.ComparisonRepository;
import co.femago.assignment.gateway.repository.entity.ComparisonEntity;
import co.femago.assignment.gateway.repository.mongo.ComparisonMongoRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ComparisonRepositoryAdapter implements ComparisonRepository {

  private final ComparisonMongoRepository mongoRepository;

  public ComparisonRepositoryAdapter(ComparisonMongoRepository mongoRepository) {
	this.mongoRepository = mongoRepository;
  }

  @Override
  public void saveRightOperator(String id, String value) {
	Optional<ComparisonEntity> byId = mongoRepository.findByComparisonId(id);
	ComparisonEntity comparison = byId.orElse(new ComparisonEntity());
	checkDiffNotCalculated(comparison);
	comparison.setComparisonId(id);
	comparison.setRight(value);
	mongoRepository.save(comparison);
  }

  @Override
  public void saveLeftOperator(String id, String value) {
	Optional<ComparisonEntity> byId = mongoRepository.findByComparisonId(id);
	ComparisonEntity comparison = byId.orElse(new ComparisonEntity());
	checkDiffNotCalculated(comparison);
	comparison.setComparisonId(id);
	comparison.setLeft(value);
	mongoRepository.save(comparison);
  }

  private void checkDiffNotCalculated(ComparisonEntity comparison) {
	if (comparison.getResponse() != null) {
	  throw new DiffAlreadyCalculatedException();
	}
  }

  @Override
  public Comparator locateComparision(String id) {
	Optional<ComparisonEntity> byId = mongoRepository.findByComparisonId(id);
	byId.orElseThrow(ComparisionNotFoundException::new);
	return null;
  }
}
