Feature: Sample re-run feature

  Scenario: Always passed test
    Given I am in the system
    When I do nothing
    Then I should see nothing

  Scenario: Always failed test
    Given I am in the system
    When I do wrong
    Then I should see nothing

  Scenario: Flaky test
    Given I am in the system
    When I do something
    Then I should see nothing

  Scenario Outline: outlining test
    Given I am in the system
    When I do some <Value> things
    Then I should see nothing

    Examples: 
      | Value |
      | 1     |
      | 2     |
      | 3     |

  Scenario: Some undefined scenario

  Scenario: Some scenario with undefined step
    Given I am in the system
    When I do nothing
    Then I should see something
