package co.femago.assignment.domain.port;

import co.femago.assignment.domain.model.ComparisonResponse;

public interface ComparisonPort {

  /**
   * Business definition for comparison/diff functionality
   *
   * @param id request id to extrac related operators
   * @return response with result and diff details: <br/>
   * <ul>
   *   <li>If equal returns: {@link co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult#EQUAL}</li>
   *   <li>If not of equal size just return: {@link co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult#NOT_EQUAL_SIZE}</li>
   *   <li>If of same size provide insight in where the diffs are, actual diffs are not needed (offsets + length)<br/>
   *     {@link co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult#NOT_EQUAL_SIZE} and list of diffs</li>
   * </ul>
   */
  ComparisonResponse compare(String id);
}
