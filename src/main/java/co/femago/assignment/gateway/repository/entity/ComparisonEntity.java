package co.femago.assignment.gateway.repository.entity;

import co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult;
import co.femago.assignment.domain.model.DiffDetail;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@Document(collection = "comparison")
public class ComparisonEntity {

  @Id
  private String mongoId;

  @Indexed(unique = true)
  @NotNull
  @NotEmpty
  @NotBlank
  private String comparisonId;

  @Field
  private String left;

  @Field
  private String right;

  @Field
  private ComparisonResponseEntity response;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ComparisonResponseEntity {

	private ComparisonResult result;

	private List<DiffDetail> details = new ArrayList<>();
  }

}

