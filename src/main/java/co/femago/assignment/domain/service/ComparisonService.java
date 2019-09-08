package co.femago.assignment.domain.service;

import co.femago.assignment.domain.model.Comparator;
import co.femago.assignment.domain.model.ComparisonResponse;
import co.femago.assignment.domain.model.Operator;
import co.femago.assignment.domain.port.ComparisonPort;
import co.femago.assignment.domain.port.ComparisonRepository;
import co.femago.assignment.domain.port.OperatorsPort;
import org.springframework.stereotype.Service;

/**
 * Business implementation of exposed services
 */
@Service
class ComparisonService implements ComparisonPort, OperatorsPort {

  private final ComparisonRepository repository;

  public ComparisonService(ComparisonRepository repository) {
	this.repository = repository;
  }

  @Override
  public ComparisonResponse compare(String id) {
	Comparator comparator = repository.locateComparision(id);
	ComparisonResponse diff = comparator.diff();
	repository.saveComparisonResponse(id, diff);
	return diff;
  }

  @Override
  public void saveOperator(String id, Operator operator, String value) {
	switch (operator) {
	  case LEFT:
		repository.saveLeftOperator(id, value);
		return;
	  case RIGHT:
		repository.saveRightOperator(id, value);
		return;
	}
  }
}
