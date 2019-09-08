package co.femago.assignment.it;

import co.femago.assignment.it.IntegrationTestContext.ContextHolder;
import cucumber.api.java.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Ignore //This class is just to start the application
public class IntegrationTestConfig {

  @TestConfiguration
  public static class ContextConfiguration {

	@Bean
	public ContextHolder executionContext() {
	  return new ContextHolder();
	}

  }

  @Autowired
  private ContextHolder contextHolder;

  @Before
  public void bootstrapBoot() {
	// Method annotated with cucumber to bootstrap SpringBootTest
	contextHolder.reset();
  }


}

