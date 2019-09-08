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
