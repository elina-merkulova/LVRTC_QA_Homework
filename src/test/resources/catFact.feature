Feature: Facts about cats

  Scenario Outline: Length of the fact provided should NOT exceed limit set in request
    When  request with maximum <length> length of the fact is made
    Then length provided by response matches actual length
    And length of the fact provided by response does not exceed maximum length of <length>
    Examples:
      | length |
      | 20     |
      | 40     |
      | 60     |
      | 80     |
      | 100    |