package co.femago.assignment.web.advisor;

import co.femago.assignment.domain.exception.ComparisionNotFoundException;
import co.femago.assignment.domain.exception.DiffAlreadyCalculatedException;
import co.femago.assignment.domain.exception.NotBase64ContentException;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handle domain controlled exceptions
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle error attempting to calculate a diff when no operators have been added
   */
  @ExceptionHandler(ComparisionNotFoundException.class)
  public ResponseEntity<String> aResourceNotFound(ComparisionNotFoundException ex) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  /**
   * Handle bad requests
   */
  @ExceptionHandler({DiffAlreadyCalculatedException.class, NotBase64ContentException.class})
  public ResponseEntity<String> handleAlreadyCalculated(Exception ex) {
	//server cannot or will not process the request due to something that is perceived to be a client error
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  /**
   * Handle errors when building a comparator with incomplete information
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> incompleteInformation(ConstraintViolationException ex) {
	//server cannot or will not process the request due to something that is perceived to be a client error
	String collect = ex.getConstraintViolations().stream()
		.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).collect(
			Collectors.joining("-"));
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage() + ": " + collect);
  }

  /**
   * Override behaviour for @Valid on controller validation
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	  HttpHeaders headers, HttpStatus status, WebRequest request) {
	String collect = ex.getBindingResult().getAllErrors().stream()
		.map(error -> (FieldError) error)
		.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
		.collect(Collectors.joining());

	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(collect);
  }
}
