package co.femago.assignment.domain.model;

import static co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult.EQUAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import org.junit.Before;
import org.junit.Test;

public class ComparatorBuilderTest {

  private ComparatorBuilder tested;

  @Before
  public void setUp() throws Exception {
	tested = new ComparatorBuilder(Validation.buildDefaultValidatorFactory().getValidator());
  }

  @Test
  public void exceptionThrowWithInvalidOperators() {
	assertThat(catchThrowable(() -> tested.build(null, "1")))
		.isInstanceOf(ConstraintViolationException.class);

	assertThat(catchThrowable(() -> tested.build("1", null)))
		.isInstanceOf(ConstraintViolationException.class);

	assertThat(catchThrowable(() -> tested.build(null, null)))
		.isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  public void exceptionThrowWithInvalidOperatorsAndResponse() {
	assertThat(catchThrowable(() -> tested.build(null, "1", new ComparisonResponse(EQUAL))))
		.isInstanceOf(ConstraintViolationException.class);
	assertThat(catchThrowable(() -> tested.build("1", null, new ComparisonResponse(EQUAL))))
		.isInstanceOf(ConstraintViolationException.class);
	assertThat(catchThrowable(() -> tested.build(null, null, new ComparisonResponse(EQUAL))))
		.isInstanceOf(ConstraintViolationException.class);
	assertThat(catchThrowable(() -> tested.build("1", "1", null)))
		.isInstanceOf(ConstraintViolationException.class);
	assertThat(catchThrowable(() -> tested.build(null, null, null)))
		.isInstanceOf(ConstraintViolationException.class);

  }

}
