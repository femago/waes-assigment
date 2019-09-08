package co.femago.assignment.domain.exception;

import co.femago.assignment.domain.model.Operator;

public class NotBase64ContentException extends RuntimeException {

  public NotBase64ContentException(Operator operator) {
	super(operator.toString() + " Operator is not Base64 encoded");
  }
}
