package co.femago.assignment.domain.service;

import static co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult.EQUAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import co.femago.assignment.domain.model.Comparator;
import co.femago.assignment.domain.model.ComparatorBuilder;
import co.femago.assignment.domain.model.ComparisonResponse;
import co.femago.assignment.domain.port.ComparisonRepository;
import co.femago.assignment.domain.port.OperatorsPort.Operator;
import javax.validation.Validation;
import org.junit.Before;
import org.junit.Test;

public class ComparisonServiceTest {

  private static final String OPERATOR_VALUE = "QmFzZTY0U3RyaW5n";
  private static final String ID = "ID";
  private ComparisonService tested;
  private ComparisonRepository repository;
  private ComparatorBuilder comparatorBuilder;

  @Before
  public void setUp() throws Exception {
	repository = mock(ComparisonRepository.class);
	tested = new ComparisonService(repository);
	comparatorBuilder = new ComparatorBuilder(Validation.buildDefaultValidatorFactory().getValidator());
  }

  @Test
  public void saveLeftOperator() {
	//When
	tested.saveOperator("ID", Operator.LEFT, OPERATOR_VALUE);
	//Then
	verify(repository, times(1)).saveLeftOperator(ID, OPERATOR_VALUE);
	verifyNoMoreInteractions(repository);
  }

  @Test
  public void saveRightOperator() {
	//When
	tested.saveOperator("ID", Operator.RIGHT, OPERATOR_VALUE);
	//Then
	verify(repository, times(1)).saveRightOperator(ID, OPERATOR_VALUE);
	verifyNoMoreInteractions(repository);
  }

  @Test
  public void compare() {
	//Given
	Comparator value = comparatorBuilder.build(OPERATOR_VALUE, OPERATOR_VALUE);
	when(repository.locateComparision(any())).thenReturn(value);
	//When
	ComparisonResponse compare = tested.compare(ID);
	//Then
	assertThat(compare.getResult()).isEqualTo(EQUAL);
  }
}
