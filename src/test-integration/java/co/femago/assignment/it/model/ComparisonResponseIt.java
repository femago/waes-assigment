package co.femago.assignment.it.model;

import co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ComparisonResponseIt {

  private ComparisonResult result;
  private List<DiffDetailIt> details = new ArrayList<>();

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class DiffDetailIt {
	private int startIndex;
	private int length;
  }
}
