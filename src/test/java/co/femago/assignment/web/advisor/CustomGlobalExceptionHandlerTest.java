package co.femago.assignment.web.advisor;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.femago.assignment.domain.exception.ComparisionNotFoundException;
import co.femago.assignment.domain.exception.DiffAlreadyCalculatedException;
import co.femago.assignment.domain.port.ComparisonPort;
import co.femago.assignment.domain.port.OperatorsPort;
import co.femago.assignment.web.controller.ComparisonController;
import co.femago.assignment.web.controller.OperatorsController;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {OperatorsController.class, ComparisonController.class})
@DirtiesContext(classMode= ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomGlobalExceptionHandlerTest {

  @MockBean
  private OperatorsPort operatorsPort;

  @MockBean
  private ComparisonPort comparisonPort;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testComparisionNotFoundException() throws Exception {
	//Given
	when(comparisonPort.compare("notfound"))
		.thenThrow(new ComparisionNotFoundException("notfound"));
	//when
	this.mockMvc.perform(get("/v1/diff/notfound"))
		.andDo(print())
		//Then
		.andExpect(status().isNotFound())
		.andExpect(content().string("Requested Id wasn't found: \"notfound\""));
  }

  @Test
  public void testDiffAlreadyCalculatedException() throws Exception {
	//Given
	doThrow(new DiffAlreadyCalculatedException("already")).when(operatorsPort)
		.saveOperator(anyString(), any(), anyString());

	//When
	this.mockMvc.perform(put("/v1/diff/already/left")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\"value\":\"1\"}"))
		.andDo(print())
		//Then
		.andExpect(status().isBadRequest())
		.andExpect(content()
			.string("Operators cannot be changed for a previously processed Id: \"already\""));

  }

  @Test
  public void testIncompleteInformation() throws Exception {
	//Given
	Set constraints = new HashSet();
	when(comparisonPort.compare("incomplete"))
		.thenThrow(new ConstraintViolationException("message", constraints));
	//when
	this.mockMvc.perform(get("/v1/diff/incomplete"))
		.andDo(print())
		//Then
		.andExpect(status().isBadRequest())
		.andExpect(content().string("message: "));
  }

  @Test
  public void errorWhenNoValueSavingOperator() throws Exception {

	this.mockMvc.perform(put("/v1/diff/invalid/right")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{}"))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("value:")))
		.andExpect(content().string(containsString("null")));

  }
}
