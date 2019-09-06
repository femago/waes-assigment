package co.femago.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.femago.assignment.domain.model.ComparisonResponseModel;
import co.femago.assignment.domain.model.ComparisonResponseModel.ComparisonResult;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ZohhakRunner.class)
public class ComparisonTest {

  @Test
  public void leftAndRightEqual() {
	//Given
	String toCompare = "bXkgaW50ZXJ2aWV3IGF0IHdhZXM=";
	Comparison tested = new Comparison(toCompare, toCompare);
	//When
	ComparisonResponseModel diff = tested.diff();
	//Then
	assertThat(diff.getResult()).isNotNull().isEqualTo(ComparisonResult.EQUAL);
  }

  @TestWith({
	  "bXkgaW50ZXJ2aWV3IGEgdHdhZXM=, RmVsaXBlIE1hcnRpbmV6IFNlbmlvciBTb2Z0d2FyZSBFbmdpbmVlcg==",
	  "RmVsaXBlIE1hcnRpbmV6IFNlbmlvciBTb2Z0d2FyZSBFbmdpbmVlcg==, bXkgaW50ZXJ2aWV3IGEgdHdhZXM="
  })
  public void differentSizeParameters(String left, String right) {
	//Given
	Comparison tested = new Comparison(left, right);
	//When
	ComparisonResponseModel diff = tested.diff();
	//Then
	assertThat(diff.getResult()).isNotNull().isEqualTo(ComparisonResult.DIFF_SIZE);
  }
}
