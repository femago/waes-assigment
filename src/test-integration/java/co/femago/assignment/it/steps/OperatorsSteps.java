package co.femago.assignment.it.steps;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
public class OperatorsSteps implements En {

  @Autowired
  private TestRestTemplate restTemplate;

  public OperatorsSteps() {
	Given("a {word} operator with value: {string} for a request with id: {string}",
		this::putOperator);
  }

  private void putOperator(String operator, String value, String rqId) {
	log.info("putting Operator {} with value {} for rq {}", operator, value, rqId);

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity<Object> entity = new HttpEntity<>(singletonMap("value", value), headers);

	ResponseEntity<String> restResponse = restTemplate
		.exchange("/v1/diff/{rqId}/{operator}", HttpMethod.PUT, entity, String.class,
			rqId, operator);

	assertThat(restResponse.getStatusCode())
		.describedAs("PUT operator returned unexpected status")
		.isEqualTo(HttpStatus.OK);
  }

}
