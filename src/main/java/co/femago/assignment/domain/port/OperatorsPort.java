package co.femago.assignment.domain.port;

import co.femago.assignment.domain.model.Operator;

/**
 * Operators related functionalities
 */
public interface OperatorsPort {

  void saveOperator(String id, Operator operator, String value);
}
