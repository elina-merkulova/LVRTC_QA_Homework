Feature: Cat breed API test

  Scenario Outline: Number of received breeds in response should match number passed in request
    When request with maximum <maximumAmount> amount of breeds displayed is made
    Then number of breeds in the response match the expected <maximumAmount>
    Examples:
      | maximumAmount |
      | 1             |
      | 6             |
      | 98            |