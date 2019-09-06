package co.femago.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.femago.assignment.domain.model.ComparisonResponseModel;
import co.femago.assignment.domain.model.ComparisonResponseModel.ComparisonResult;
import org.junit.Test;

public class ComparisonTest {

  @Test
  public void leftAndRightEqual() {
	//Given
	String toCompare = "bXkgaW50ZXJ2aWV3IGEgdHdhZXM=";
	Comparison tested = new Comparison(toCompare, toCompare);
	//When
	ComparisonResponseModel diff = tested.diff();
	//Then
	assertThat(diff.getResult()).isNotNull().isEqualTo(ComparisonResult.EQUAL);
  }
}
