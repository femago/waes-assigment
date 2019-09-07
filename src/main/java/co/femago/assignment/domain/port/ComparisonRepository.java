package co.femago.assignment.domain.port;

import co.femago.assignment.domain.model.Comparator;

public interface ComparisonRepository {

  void saveRightOperator(String id, String value);

  void saveLeftOperator(String id, String value);

  Comparator locateComparision(String id);

}
