package co.femago.assignment.domain.port;

import co.femago.assignment.domain.model.ComparisonResponse;

public interface ComparisonPort {

  ComparisonResponse compare(String id);
}
