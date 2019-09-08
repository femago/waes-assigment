package co.femago.assignment.it.steps;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

import co.femago.assignment.it.IntegrationTestContext.ContextHolder;
import co.femago.assignment.it.model.ComparisonResponseIt;
import co.femago.assignment.it.model.ComparisonResponseIt.DiffDetailIt;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ComparisonSteps implements En {

  @Autowired
  private ContextHolder holder;

  @Autowired
  private TestRestTemplate restTemplate;

  public ComparisonSteps() {
	When("a diff request with id: {string} is sent", this::callDiff);
	Then("the response result is {string}", this::verifyResponseResult);
	And("no diffs are returned", this::verifyNoDiffs);
	And("{int} diffs are returned:", this::verifyDiffsContent);

  }

  private void callDiff(String rqId) {
	log.info("callDiff {}", rqId);
	ResponseEntity<ComparisonResponseIt> restResponse = restTemplate
		.getForEntity("/v1/diff/{rqId}", ComparisonResponseIt.class, rqId);
	assertThat(restResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

	holder.getContext().setResponseEntity(restResponse.getBody());
  }

  private void verifyResponseResult(String expectedResult) {
	ComparisonResponseIt responseEntity = holder.getContext().getResponseEntity();
	assertThat(responseEntity.getResult().toString()).isEqualToIgnoringCase(expectedResult);

  }

  private void verifyNoDiffs() {
	ComparisonResponseIt responseEntity = holder.getContext().getResponseEntity();
	assertThat(responseEntity.getDetails()).isEmpty();
  }

  private void verifyDiffsContent(Integer expectedDiffsNum, DataTable diffs) {
	ComparisonResponseIt responseEntity = holder.getContext().getResponseEntity();
	List<DiffDetailIt> actualDiffs = responseEntity.getDetails();
	assertThat(actualDiffs).describedAs("Expected number of diffs do not match with received")
		.hasSize(expectedDiffsNum);

	List<DiffDetailIt> expectedDiffs = diffs.cells().stream().skip(1)
		.map(strings -> new DiffDetailIt(
			parseInt(strings.get(0)), parseInt(strings.get(1)))).collect(Collectors.toList());

	assertThat(actualDiffs).containsSequence(expectedDiffs);
  }

}
