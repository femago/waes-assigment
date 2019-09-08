package co.femago.assignment.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

public class ComparisonResponse {

  public enum ComparisonResult {
	EQUAL, NOT_EQUAL_SIZE, DIFF
  }

  @Getter
  private final ComparisonResult result;

  private final List<DiffDetail> details = new ArrayList<>();

  public ComparisonResponse(ComparisonResult result) {
	this.result = result;
  }

  public void addDetail(DiffDetail detail){
	 details.add(detail);
  }

  public List<DiffDetail> getDetails() {
	return Collections.unmodifiableList(details);
  }
}
