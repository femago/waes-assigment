package co.femago.assignment.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ZohhakRunner.class)
public class ComparatorTest {

  @Test
  public void leftAndRightEqual() {
	//Given
	String toCompare = "bXkgaW50ZXJ2aWV3IGF0IHdhZXM=";
	Comparator tested = new Comparator(toCompare, toCompare);
	//When
	ComparisonResponse diff = tested.diff();
	ComparisonResponse secondDiff = tested.diff();
	//Then
	assertThat(diff.getResult()).isNotNull().isEqualTo(ComparisonResult.EQUAL);
	assertThat(diff).isEqualTo(secondDiff);
  }

  @TestWith({
	  "bXkgaW50ZXJ2aWV3IGEgdHdhZXM=, RmVsaXBlIE1hcnRpbmV6IFNlbmlvciBTb2Z0d2FyZSBFbmdpbmVlcg==",
	  "RmVsaXBlIE1hcnRpbmV6IFNlbmlvciBTb2Z0d2FyZSBFbmdpbmVlcg==, bXkgaW50ZXJ2aWV3IGEgdHdhZXM="
  })
  public void differentSizeParameters(String left, String right) {
	//Given
	Comparator tested = new Comparator(left, right);
	//When
	ComparisonResponse diff = tested.diff();
	//Then
	assertThat(diff.getResult()).isNotNull().isEqualTo(ComparisonResult.DIFF_SIZE);
  }

  @Test
  public void differentWithSameSize() {
	//Given
	String left = "NUdRMk9KUzFpZXZYTWxCT2dXWlUKRlN6cmZMYlJZalFWNXhHMTZhYVIKVDlYd011bXNOZmVQemFESG1vTm8=";
	String right = "NUdRMk9KUzFpZXZYTWxCT2dXWlUKVTlLOU96amRiVjRNVGd1RVo2SVgKVDlYd011bXNOZmVQemFESG1vTm8=";
	Comparator tested = new Comparator(left, right);
	//When
	ComparisonResponse diff = tested.diff();
	//Then
	assertThat(diff.getResult()).isNotNull().isEqualTo(ComparisonResult.DIFF);
	assertThat(diff.getDetails()).isNotEmpty().hasSize(2);

	assertThat(diff.getDetails().get(0))
		.extracting(DiffDetail::getStartIndex,
					DiffDetail::getLength)
		//First diff starts at 28 with length 25
		.containsExactly(28, 25);

	assertThat(diff.getDetails().get(1))
		.extracting(DiffDetail::getStartIndex,
					DiffDetail::getLength)
		//Second diff starts at 54 with length 1
		.containsExactly(54, 1);
  }
}
