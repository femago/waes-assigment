package co.femago.assignment.domain.port;

import co.femago.assignment.domain.model.Comparator;
import co.femago.assignment.domain.model.ComparisonResponse;

/**
 * Operations related with persisting and loading information
 */
public interface ComparisonRepository {

  void saveRightOperator(String id, String value);

  void saveLeftOperator(String id, String value);

  Comparator locateComparision(String id);

  void saveComparisonResponse(String id, ComparisonResponse diff);
}
