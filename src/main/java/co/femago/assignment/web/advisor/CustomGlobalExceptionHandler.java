package co.femago.assignment.web.advisor;

import co.femago.assignment.domain.exception.ComparisionNotFoundException;
import co.femago.assignment.domain.exception.DiffAlreadyCalculatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ComparisionNotFoundException.class)
  public ResponseEntity<String> aResourceNotFound(Exception ex) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(DiffAlreadyCalculatedException.class)
  public ResponseEntity<String> clientError(Exception ex) {
	//server cannot or will not process the request due to something that is perceived to be a client error
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
