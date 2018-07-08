Feature: Calculate realtime statistic from the last 60 seconds

  Scenario: Process a valid transaction
    When a request is made to process a transaction with input as
      | amount  | timestamp |
      | 10.0    | NOW       |
    Then response code is 201

  Scenario: Process an invalid transaction
    When a request is made to process a transaction with input as
      | amount  | timestamp |
      | 10.0    | EXPIRED   |
    Then response code is 204

  Scenario: Get statistics of the valid transactions
    Given Below are the transactions
      | amount  | timestamp |
      | 10.0    | NOW,10    |
      | 20.0    | NOW,20    |
      | 30.0    | NOW,30    |
      | 40.0    | NOW,40    |
      | 50.0    | NOW,-62   |
    When a request is made to get the transaction statistics
    Then the valid transaction statistics are
      | sum   | average | maximum | minimum | count |
      | 100.0 | 25.0    | 40.0    | 10.0    | 4     |