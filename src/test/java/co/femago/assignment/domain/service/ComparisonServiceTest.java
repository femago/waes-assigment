package co.femago.assignment.domain.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import co.femago.assignment.domain.port.ComparisonRepository;
import co.femago.assignment.domain.port.OperatorsPort.Operator;
import org.junit.Before;
import org.junit.Test;

public class ComparisonServiceTest {

  private static final String SAVE_VALUE = "QmFzZTY0U3RyaW5n";
  private static final String ID = "ID";
  private ComparisonService tested;
  private ComparisonRepository repository;

  @Before
  public void setUp() {
	repository = mock(ComparisonRepository.class);
	tested = new ComparisonService(repository);
  }

  @Test
  public void saveLeftOperator() {
	//When
	tested.saveOperator("ID", Operator.LEFT, SAVE_VALUE);
	//Then
	verify(repository, times(1)).saveLeftOperator(ID, SAVE_VALUE);
	verifyNoMoreInteractions(repository);
  }

  @Test
  public void saveRightOperator() {
	//When
	tested.saveOperator("ID", Operator.RIGHT, SAVE_VALUE);
	//Then
	verify(repository, times(1)).saveRightOperator(ID, SAVE_VALUE);
	verifyNoMoreInteractions(repository);
  }
}
