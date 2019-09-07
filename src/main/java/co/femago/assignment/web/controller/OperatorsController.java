package co.femago.assignment.web.controller;

import co.femago.assignment.domain.port.OperatorsPort;
import co.femago.assignment.domain.port.OperatorsPort.Operator;
import co.femago.assignment.web.dto.OperatorValue;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/diff")
public class OperatorsController {

  private final OperatorsPort operatorsPort;

  public OperatorsController(OperatorsPort operatorsPort) {
	this.operatorsPort = operatorsPort;
  }

  @PostMapping(path = "/{id}/left", consumes = "application/json")
  public void saveLeftOperator(
	  @PathVariable("id") @NotNull @NotBlank @NotEmpty String id,
	  @RequestBody OperatorValue operatorValue) {
	operatorsPort.saveOperator(id, Operator.LEFT, operatorValue.getValue());
  }

  @PostMapping(path = "/{id}/right", consumes = "application/json")
  public void saveRightOperator(
	  @PathVariable("id") @NotNull @NotBlank @NotEmpty String id,
	  @RequestBody @Valid OperatorValue operatorValue) {
	operatorsPort.saveOperator(id, Operator.LEFT, operatorValue.getValue());
  }

}
