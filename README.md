# Java - Assignment Scalable Web	

## Assigment
* Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints 
`<host>/v1/diff/<ID>/left` and `<host>/v1/diff/<ID>/right`
* The provided data needs to be diff-ed and the results shall be available on a third end 
point `<host>/v1/diff/<ID>`
* The results shall provide the following info in JSON format 
    * If equal return that 
    * If not of equal size just return that 
    * If of same size provide insight in where the diffs are, actual diffs are not needed (offsets + length) 
* Make assumptions in the implementation explicit, choices are good but need to be communicated	

## Assumptions
* **left** and **right** endpoints are implemented using http **PUT** method since they are implemented as a create/update
* **left** or **right** endpoints can be called many times to update the operators values for a given **ID** as 
long as no diff operation have been called for the given **ID**
    * when **left** or **right** endpoints are called for an already calculated diff following error 
    response will be returned
```
HTTP/1.1 400 
Content-Type: text/plain;charset=UTF-8

Operators cannot be changed for a previously processed Id: "example"   
```
* calling **diff** endpoint before calling **left** or **right** will return and error
```
HTTP/1.1 400 
Content-Type: text/plain;charset=UTF-8

Not enough parameters to create a comparator: Comparator.right: cannot be null
```
* calling **diff** endpoint before any operator will return and error
``` 
HTTP/1.1 404 
Content-Type: text/plain;charset=UTF-8

Requested Id wasn't found: "example5"
```
* **diff** endpoint returns the diff result in the node `{"result": "____",}` containing one of the following 
values: `EQUAL`, `NOT_EQUAL_SIZE`, `DIFF`
* after successfully calling a **diff** endpoint, the result is saved in a repository. Subsequent calls 
with the same ID will retrieve the result from the database instead of calculating it again. Since the 
operator cannot be updated, the result will remain de same after the first calculation. 

## Implementation
### Running the application
1. clone the repository
2. run `./gradlew bootRun`
3. Application starts on port 8080
4. Call **left** and **right** endpoints. Example:
```
PUT http://localhost:8080/v1/diff/example/left
Content-Type: application/json

{
  "value": "SW50ZWdyYXRpb25UZXN0T3BlcmF0b3I="
}
```
```
PUT http://localhost:8080/v1/diff/example/right
Content-Type: application/json

{
  "value": "SW50Z1dyYX22b25UZ333T3BlcmF0b3I="
}
```
5. Call **diff** endpoint. Example:
```
GET http://localhost:8080/v1/diff/example

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8

{
  "result": "DIFF",
  "details": [
    {
      "startIndex": 5,
      "length": 1
    },
    {
      "startIndex": 10,
      "length": 2
    },
    {
      "startIndex": 17,
      "length": 3
    }
  ]
}
```
### Test
Runs using `./gradlew test`
#### Unit Tests
Implemented using junit, located under folder `.\src\test`

#### Integration test
Implemented using cucumber, located under folder `.\src\test-integration`.
* `co.femago.assignment.it.IntegrationTestRunner` bootstrap class to start the test suite 
* Functionalities definition example:
```gherkin
Feature: Comparing left and right operators returns an expected response

  Scenario: Both operators are equal
    Given a left operator with value: "SW50ZWdyYXRpb25UZXN0T3BlcmF0b3I=" for a request with id: "req1"
    And a right operator with value: "SW50ZWdyYXRpb25UZXN0T3BlcmF0b3I=" for a request with id: "req1"
    When a diff request with id: "req1" is sent
    Then the response result is "EQUAL"
    And no diffs are returned

  Scenario: Operators have different size
    Given a left operator with value: "SW50ZWdyYXRpb25UZXN0T3BlcmF0b3I=" for a request with id: "req2"
    And a right operator with value: "SW50ZWdyYXRpb25UZXN0T3BlcmF0b3JMZW5ndGg=" for a request with id: "req2"
    When a diff request with id: "req2" is sent
    Then the response result is "NOT_EQUAL_SIZE"
    And no diffs are returned

  Scenario: Operators have diffs
    Given a left operator with value: "SW50ZWdyYXRpb25UZXN0T3BlcmF0b3I=" for a request with id: "req3"
    And a right operator with value: "SW50Z1dyYX22b25UZ333T3BlcmF0b3I=" for a request with id: "req3"
    When a diff request with id: "req3" is sent
    Then the response result is "DIFF"
    And 3 diffs are returned:
      | start index | length |
      | 5           | 1      |
      | 10          | 2      |
      | 17          | 3      |
```
