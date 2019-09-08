package co.femago.assignment.domain.model;

import lombok.Getter;

/**
 * Holds offsets + length of a found diff while comparing strings
 */
public class DiffDetail {

  @Getter
  private final int startIndex;
  @Getter
  private int length;

  public DiffDetail(int startIndex) {
	this.startIndex = startIndex;
	this.length = 0;
  }

  public void increaseLength() {
	length++;
  }
}
