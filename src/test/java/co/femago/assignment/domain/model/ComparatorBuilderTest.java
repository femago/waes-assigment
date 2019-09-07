package co.femago.assignment.domain.model;

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

  @Test(expected = ConstraintViolationException.class)
  public void noLeft() {
	tested.build(null, "1");
  }

  @Test(expected = ConstraintViolationException.class)
  public void noRight() {
	tested.build("1", null);
  }

  @Test(expected = ConstraintViolationException.class)
  public void noLeftAndRight() {
	tested.build(null, null);
  }
}
