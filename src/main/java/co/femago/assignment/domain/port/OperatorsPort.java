package co.femago.assignment.domain.port;

public interface OperatorsPort {

  enum Operator {
	LEFT,
	RIGHT
  }

  void saveOperator(String id, Operator operator, String value);
}
