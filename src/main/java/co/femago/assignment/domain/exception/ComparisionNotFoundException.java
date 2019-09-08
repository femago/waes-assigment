package co.femago.assignment.domain.exception;

public class ComparisionNotFoundException extends RuntimeException {

  public ComparisionNotFoundException(String comparisonId) {
	super("Requested Id wasn't found: \"" + comparisonId + "\"");
  }
}
