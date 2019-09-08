package co.femago.assignment.domain.model;

import static co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult.DIFF;
import static co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult.EQUAL;
import static co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult.NOT_EQUAL_SIZE;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Business logic to compare two strings.
 * @see ComparatorBuilder
 */
public class Comparator {

  @Getter
  private final String left;
  @Getter
  private final String right;

  private ComparisonResponse response;

  Comparator(@NotNull String left, @NotNull String right) {
	this.left = left;
	this.right = right;
  }

  Comparator(@NotNull String left, @NotNull String right,
	  @NotNull ComparisonResponse response) {
	this(left, right);
	this.response = response;
  }

  public ComparisonResponse diff() {
	if (response != null) {
	  return response;
	}

	if (left.length() != right.length()) {
	  response = new ComparisonResponse(NOT_EQUAL_SIZE);
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
