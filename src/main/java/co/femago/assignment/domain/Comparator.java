package co.femago.assignment.domain;

import static co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult.DIFF;
import static co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult.DIFF_SIZE;
import static co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult.EQUAL;

import co.femago.assignment.domain.model.ComparisonResponse;
import co.femago.assignment.domain.model.DiffDetail;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import javax.validation.constraints.NotNull;

public class Comparator {

  private final String left;
  private final String right;

  private ComparisonResponse response;

  public Comparator(@NotNull String left, @NotNull String right) {
	this.left = left;
	this.right = right;
  }

  public ComparisonResponse diff() {
	if (response != null) {
	  return response;
	}

	if (left.length() != right.length()) {
	  response = new ComparisonResponse(DIFF_SIZE);
	} else if (left.equals(right)) {
	  response = new ComparisonResponse(EQUAL);
	} else {
	  response = new ComparisonResponse(DIFF);
	  findDiffs();
	}
	return response;
  }

  private void findDiffs() {
	StringCharacterIterator leftIt = new StringCharacterIterator(left);
	StringCharacterIterator rightIt = new StringCharacterIterator(right);
	DiffDetail currentDetail = null;
	while (leftIt.current() != CharacterIterator.DONE) {
	  if (leftIt.next() != rightIt.next()) {
		currentDetail = increaseCurrentDiff(currentDetail, leftIt.getIndex());
	  } else {
		currentDetail = null;
	  }
	}
  }

  private DiffDetail increaseCurrentDiff(DiffDetail currentDetail, int index) {
	if (currentDetail == null) {
	  currentDetail = new DiffDetail(index);
	  response.addDetail(currentDetail);
	}
	currentDetail.increaseLength();
	return currentDetail;
  }
}
