package co.femago.assignment.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.femago.assignment.domain.port.OperatorsPort;
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
@WebMvcTest(controllers = {OperatorsController.class})
@DirtiesContext(classMode= ClassMode.AFTER_EACH_TEST_METHOD)
public class OperatorsControllerTest {
  @MockBean
  private OperatorsPort operatorsPort;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void putLeftOperator() throws Exception {
	//When
	this.mockMvc.perform(put("/v1/diff/new/left")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\"value\":\"1\"}"))
		.andDo(print())
		//Then
		.andExpect(status().isOk())
		.andExpect(content().string(""));

  }
  @Test
  public void putRightOperator() throws Exception {
	//When
	this.mockMvc.perform(put("/v1/diff/new/right")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\"value\":\"1\"}"))
		.andDo(print())
		//Then
		.andExpect(status().isOk())
		.andExpect(content().string(""));

  }
}
