package co.femago.assignment.web.controller;

import co.femago.assignment.domain.model.ComparisonResponse;
import co.femago.assignment.domain.port.ComparisonPort;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for comparison related entry points
 */
@RestController
@RequestMapping("/v1/diff")
public class ComparisonController {

  private final ComparisonPort comparisonPort;

  public ComparisonController(ComparisonPort comparisonPort) {
	this.comparisonPort = comparisonPort;
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<ComparisonResponse> compare(
	  @PathVariable("id") @NotNull @NotBlank @NotEmpty String id) {
	return ResponseEntity.ok(comparisonPort.compare(id));
  }

}
