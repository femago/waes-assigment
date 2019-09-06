package co.femago.assignment.domain.model;

import lombok.Getter;

public class ComparisonResponseModel {

  public enum ComparisonResult {
	EQUAL, DIFF_SIZE, DIFF
  }

  @Getter
  private final ComparisonResult result;

  public ComparisonResponseModel(ComparisonResult result) {
	this.result = result;
  }


}
