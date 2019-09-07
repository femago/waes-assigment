package co.femago.assignment.web.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatorValue {

  @NotNull
  private String value;
}
