package co.femago.assignment.domain;

import static co.femago.assignment.domain.model.ComparisonResponseModel.ComparisonResult.EQUAL;

import co.femago.assignment.domain.model.ComparisonResponseModel;
import javax.validation.constraints.NotNull;

public class Comparison {

  private final String left;
  private final String right;

  public Comparison(@NotNull String left, @NotNull String right) {
	this.left = left;
	this.right = right;
  }

  public ComparisonResponseModel diff() {
	if (left.equals(right)) {
	  return new ComparisonResponseModel(EQUAL);
	}
	return null;
  }
}
