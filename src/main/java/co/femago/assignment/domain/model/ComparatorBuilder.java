package co.femago.assignment.domain.model;

import java.lang.reflect.Constructor;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import org.springframework.stereotype.Component;

@Component
public class ComparatorBuilder {

  private final Validator validator;
  private final Constructor<Comparator> simpleConstructor;
  private final Constructor<Comparator> allArgsConstructor;

  public ComparatorBuilder(Validator validator) throws NoSuchMethodException {
	this.validator = validator;
	simpleConstructor = Comparator.class.getDeclaredConstructor(String.class, String.class);
	allArgsConstructor = Comparator.class
		.getDeclaredConstructor(String.class, String.class, ComparisonResponse.class);
  }

  public Comparator build(String left, String right) {
	checkConstructorParameters(simpleConstructor, new Object[]{left, right});
	return new Comparator(left, right);
  }

  public Comparator build(String left, String right, ComparisonResponse response) {
	checkConstructorParameters(allArgsConstructor, new Object[]{left, right, response});
	return new Comparator(left, right, response);
  }

  private void checkConstructorParameters(Constructor<Comparator> constructor,
	  Object[] parameters) {
	ExecutableValidator executableValidator = validator.forExecutables();
	Set<ConstraintViolation<Comparator>> constraintViolations = executableValidator
		.validateConstructorParameters(constructor, parameters);
	if (!constraintViolations.isEmpty()) {
	  throw new ConstraintViolationException("Not enough parameters to create a comparator",
		  constraintViolations);
	}
  }
}
