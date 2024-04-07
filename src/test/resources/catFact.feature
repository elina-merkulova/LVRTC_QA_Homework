@all @testFactPipeline
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

 #bug found: In some cases f.e. {"fact":"Female cats are polyestrous","length":28} have really only 27 characters
  Scenario Outline: Length and amount of the facts received should NOT exceed limit set in request
    When request for list of random facts with limit <length> and maximum length <amount> is made
    Then length provided for each received fact matches actual length of the fact
    And number of facts received matches the requested amount of <amount> and their length does not exceed limit <length>
    Examples:
      | length | amount |
      | 20     | 1      |
      | 25     | 2      |
      | 40     | 5      |
     #| 60     | 10     |
      #| 80     | 20     |
      #| 100    | 300    |