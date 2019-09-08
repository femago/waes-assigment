package co.femago.assignment.gateway.repository;

import co.femago.assignment.domain.exception.ComparisionNotFoundException;
import co.femago.assignment.domain.exception.DiffAlreadyCalculatedException;
import co.femago.assignment.domain.model.Comparator;
import co.femago.assignment.domain.model.ComparatorBuilder;
import co.femago.assignment.domain.model.ComparisonResponse;
import co.femago.assignment.domain.port.ComparisonRepository;
import co.femago.assignment.gateway.repository.entity.ComparisonEntity;
import co.femago.assignment.gateway.repository.entity.ComparisonEntity.ComparisonResponseEntity;
import co.femago.assignment.gateway.repository.mongo.ComparisonMongoRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class ComparisonRepositoryAdapter implements ComparisonRepository {

  private final ComparisonMongoRepository mongoRepository;
  private final ComparatorBuilder comparatorBuilder;

  public ComparisonRepositoryAdapter(ComparisonMongoRepository mongoRepository,
	  ComparatorBuilder comparatorBuilder) {
	this.mongoRepository = mongoRepository;
	this.comparatorBuilder = comparatorBuilder;
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
	  throw new DiffAlreadyCalculatedException(comparison.getComparisonId());
	}
  }

  @Override
  public void saveComparisonResponse(String id, ComparisonResponse diff) {
	Optional<ComparisonEntity> byId = mongoRepository.findByComparisonId(id);
	byId.orElseThrow(() -> new ComparisionNotFoundException(id)).setResponse(
		ComparisonResponseEntity.builder()
			.result(diff.getResult())
			.details(diff.getDetails())
			.build()
	);
	mongoRepository.save(byId.get());
  }

  @Override
  public Comparator locateComparision(String id) {
	Optional<ComparisonEntity> byId = mongoRepository.findByComparisonId(id);
	return byId.map(this::comparatorEntityToDomain).orElseThrow(() -> new ComparisionNotFoundException(id));
  }

  private Comparator comparatorEntityToDomain(ComparisonEntity comparisonEntity) {
	Optional<ComparisonResponseEntity> response = Optional
		.ofNullable(comparisonEntity.getResponse());
	if (response.isPresent()) {
	  return comparatorBuilder.build(comparisonEntity.getLeft(), comparisonEntity.getRight(),
		  response.map(this::responseEntityToDomain).get());
	} else {
	  return comparatorBuilder.build(comparisonEntity.getLeft(), comparisonEntity.getRight());
	}
  }

  private ComparisonResponse responseEntityToDomain(ComparisonResponseEntity entity) {
	ComparisonResponse response = new ComparisonResponse(entity.getResult());
	Optional.ofNullable(entity.getDetails())
		.map(Collection::stream).orElse(Stream.empty())
		.forEach(response::addDetail);
	return response;
  }
}
