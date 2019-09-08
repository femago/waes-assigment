package co.femago.assignment.gateway.repository;

import static org.assertj.core.api.Assertions.assertThat;

import co.femago.assignment.domain.exception.ComparisionNotFoundException;
import co.femago.assignment.domain.exception.DiffAlreadyCalculatedException;
import co.femago.assignment.domain.model.Comparator;
import co.femago.assignment.domain.model.ComparatorBuilder;
import co.femago.assignment.domain.model.ComparisonResponse;
import co.femago.assignment.domain.model.ComparisonResponse.ComparisonResult;
import co.femago.assignment.domain.model.DiffDetail;
import co.femago.assignment.gateway.repository.config.MongoConfig;
import co.femago.assignment.gateway.repository.entity.ComparisonEntity;
import co.femago.assignment.gateway.repository.entity.ComparisonEntity.ComparisonResponseEntity;
import co.femago.assignment.gateway.repository.mongo.ComparisonMongoRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MongoConfig.class, ComparatorBuilder.class})
public class ComparisonRepositoryAdapterTest {

  private static final String A_TEST_ID = "TestId";
  private static final String OPERATOR_VALUE = "AnOperatorValue";

  @Autowired
  private ComparisonRepositoryAdapter tested;

  @Autowired
  private ComparisonMongoRepository repository;

  @Before
  public void setUp() {
	repository.deleteAll();
  }

  @Test
  public void saveNewRightOperator() {
	//When
	tested.saveRightOperator(A_TEST_ID, OPERATOR_VALUE);
	//Then
	ComparisonEntity actual = verifyEntity(A_TEST_ID);
	assertThat(actual.getRight()).isEqualTo(OPERATOR_VALUE);
	assertThat(actual.getLeft()).isNull();
  }

  @Test
  public void updateRightOperator() {
	//Given
	ComparisonEntity entity = new ComparisonEntity();
	entity.setComparisonId(A_TEST_ID);
	entity.setRight("PreviousRight");
	repository.save(entity);
	//When
	tested.saveRightOperator(A_TEST_ID, OPERATOR_VALUE);
	//Then
	ComparisonEntity actual = verifyEntity(A_TEST_ID);
	assertThat(actual.getRight()).isEqualTo(OPERATOR_VALUE);
	assertThat(actual.getLeft()).isNull();
  }

  @Test
  public void saveNewLeftOperator() {
	//When
	tested.saveLeftOperator(A_TEST_ID, OPERATOR_VALUE);
	//Then
	ComparisonEntity actual = verifyEntity(A_TEST_ID);
	assertThat(actual.getLeft()).isEqualTo(OPERATOR_VALUE);
	assertThat(actual.getRight()).isNull();
  }

  @Test
  public void updateLeftOperator() {
	//Given
	ComparisonEntity entity = new ComparisonEntity();
	entity.setComparisonId(A_TEST_ID);
	entity.setLeft("PreviousLeft");
	repository.save(entity);
	//When
	tested.saveLeftOperator(A_TEST_ID, OPERATOR_VALUE);
	//Then
	ComparisonEntity actual = verifyEntity(A_TEST_ID);
	assertThat(actual.getLeft()).isEqualTo(OPERATOR_VALUE);
	assertThat(actual.getRight()).isNull();
  }

  @Test
  public void setRightOnExistingLeft() {
	//Given
	ComparisonEntity entity = new ComparisonEntity();
	entity.setComparisonId(A_TEST_ID);
	entity.setLeft(OPERATOR_VALUE);
	repository.save(entity);
	//When
	tested.saveRightOperator(A_TEST_ID, OPERATOR_VALUE);
	//Then
	ComparisonEntity actual = verifyEntity(A_TEST_ID);
	assertThat(actual.getLeft()).isEqualTo(OPERATOR_VALUE);
	assertThat(actual.getRight()).isEqualTo(OPERATOR_VALUE);
  }

  @Test
  public void setLeftOnExistingRight() {
	//Given
	ComparisonEntity entity = new ComparisonEntity();
	entity.setComparisonId(A_TEST_ID);
	entity.setRight(OPERATOR_VALUE);
	repository.save(entity);
	//When
	tested.saveLeftOperator(A_TEST_ID, OPERATOR_VALUE);
	//Then
	ComparisonEntity actual = verifyEntity(A_TEST_ID);
	assertThat(actual.getLeft()).isEqualTo(OPERATOR_VALUE);
	assertThat(actual.getRight()).isEqualTo(OPERATOR_VALUE);
  }

  @Test(expected = DiffAlreadyCalculatedException.class)
  public void errorWhenDiffAlreadyCalculatedLeft() {
	//Given
	saveEntityWithResponse();
	//When
	tested.saveLeftOperator(A_TEST_ID, OPERATOR_VALUE);
  }

  @Test(expected = DiffAlreadyCalculatedException.class)
  public void errorWhenDiffAlreadyCalculatedRight() {
	//Given
	saveEntityWithResponse();
	//When
	tested.saveRightOperator(A_TEST_ID, OPERATOR_VALUE);
  }

  @Test
  public void retrieveExistingComparison() {
	//Given
	saveEntityWithResponse();
	//When
	Comparator comparator = tested.locateComparision(A_TEST_ID);
	//Then
	ComparisonResponse diff = comparator.diff();
	assertThat(diff.getResult()).isEqualTo(ComparisonResult.EQUAL);
	assertThat(diff.getDetails()).isEmpty();
	assertThat(comparator.getLeft()).isEqualTo(OPERATOR_VALUE);
	assertThat(comparator.getRight()).isEqualTo(OPERATOR_VALUE);
  }

  @Test
  public void saveComparisonResponse() {
	//Given
	saveEntityWithResponse();
	removeResponseFromSaved();
	ComparisonResponse diff = new ComparisonResponse(ComparisonResult.DIFF);
	//When
	tested.saveComparisonResponse(A_TEST_ID, diff);
	//Then
	Optional<ComparisonEntity> byComparisonId = repository.findByComparisonId(A_TEST_ID);
	assertThat(byComparisonId).isNotEmpty();
	assertThat(byComparisonId.get().getResponse()).isNotNull();
  }

  private void removeResponseFromSaved() {
	ComparisonEntity comparisonEntity = repository.findAll().get(0);
	comparisonEntity.setResponse(null);
	repository.save(comparisonEntity);
  }

  @Test
  public void retrieveComparisonBeforeDiff() {
	//Given
	saveEntityWithResponse();
	removeResponseFromSaved();
	//When
	Comparator comparator = tested.locateComparision(A_TEST_ID);
	//Then
	assertThat(comparator.getLeft()).isEqualTo(OPERATOR_VALUE);
	assertThat(comparator.getRight()).isEqualTo(OPERATOR_VALUE);
	assertThat(comparator).extracting("response").hasSize(1).containsOnlyNulls();
  }

  @Test
  public void retrieveComparisonWithDiffs() {
	//Given
	saveEntityWithResponse();
	ComparisonEntity comparisonEntity = repository.findAll().get(0);
	comparisonEntity.setResponse(
		ComparisonResponseEntity.builder()
			.result(ComparisonResult.DIFF)
			.details(Arrays.asList(new DiffDetail(1)))
			.build()
	);
	repository.save(comparisonEntity);
	//When
	Comparator comparator = tested.locateComparision(A_TEST_ID);
	//Then
	assertThat(comparator.getLeft()).isEqualTo(OPERATOR_VALUE);
	assertThat(comparator.getRight()).isEqualTo(OPERATOR_VALUE);
	assertThat(comparator).extracting("response").hasSize(1).doesNotContainNull();
	ComparisonResponse diff = comparator.diff();
	assertThat(diff.getResult()).isEqualTo(ComparisonResult.DIFF);
	assertThat(diff.getDetails()).hasSize(1);
	assertThat(diff.getDetails().get(0)).extracting("startIndex", "length").contains(1, 0);
  }

  private void saveEntityWithResponse() {
	ComparisonEntity entity = new ComparisonEntity();
	entity.setComparisonId(A_TEST_ID);
	entity.setRight(OPERATOR_VALUE);
	entity.setLeft(OPERATOR_VALUE);
	entity.setResponse(ComparisonResponseEntity.builder()
		.result(ComparisonResult.EQUAL)
		.build()
	);
	repository.save(entity);
  }


  private ComparisonEntity verifyEntity(String testId) {
	List<ComparisonEntity> all = repository.findAll();
	assertThat(all).hasSize(1);

	ComparisonEntity actual = all.get(0);
	assertThat(actual.getComparisonId()).isEqualTo(testId);
	assertThat(actual.getResponse()).isNull();
	return actual;
  }

  @Test(expected = ComparisionNotFoundException.class)
  public void comparisonNotFound() {
	tested.locateComparision(A_TEST_ID);
  }
}
