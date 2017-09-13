Feature: Test1

  Scenario: Test11
    Given I activate app with a valid code
    Then I am on home screen and in disconnected state

  Scenario: Test12
    Given I activate app with a valid code
    Then I am on home screen and in disconnected state
    And I disabled internet connection
