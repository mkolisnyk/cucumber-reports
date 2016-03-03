@sample @feature
Feature: Sample re-run feature

  @passed @consistent
  Scenario: Always passed test
    Given I am in the system
    When I do nothing
    Then I should see nothing

  @failed
  Scenario: Always failed test
    Given I am in the system
    When I do wrong
    Then I should see nothing

  @flaky @passed
  Scenario: Flaky test
    Given I am in the system
    When I do something
    Then I should see nothing

   @passed
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

  @consistent
  Scenario: Some scenario with undefined step
    Given I am in the system
    When I do nothing
    Then I should see something

  @nc_outline
  Scenario Outline: Some scenario outline with undefined step
    Given I am in the system
    When I do nothing
    Then I should see <Data> something

    Examples: 
      | Data |
      | 1    |
      | 2    |
      | 3    |
      | 4    |
