package co.femago.assignment.domain.exception;

public class DiffAlreadyCalculatedException extends RuntimeException {

  public DiffAlreadyCalculatedException(String comparisonId) {
    super("Operators cannot be changed for a previously processed Id: \"" + comparisonId + "\"");
  }
}
