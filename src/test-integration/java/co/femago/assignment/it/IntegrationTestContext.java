package co.femago.assignment.it;

import co.femago.assignment.it.model.ComparisonResponseIt;

public final class IntegrationTestContext {

  private ComparisonResponseIt responseEntity;

  public ComparisonResponseIt getResponseEntity() {
	return responseEntity;
  }

  public void setResponseEntity(ComparisonResponseIt responseEntity) {
	this.responseEntity = responseEntity;
  }

  /**
   * Holder to reference context from Spring
   */
  public static class ContextHolder {

	private IntegrationTestContext context;

	public ContextHolder() {
	  reset();
	}

	void reset() {
	  this.context = new IntegrationTestContext();
	}

	public IntegrationTestContext getContext() {
	  return context;
	}
  }
}
