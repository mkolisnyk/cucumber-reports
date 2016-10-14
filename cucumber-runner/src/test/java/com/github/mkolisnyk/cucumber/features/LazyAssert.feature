@passed
Feature: Lazy assert feature

  @lazy
  Scenario: Fails in the middle
    Given I am in the system
    When I do nothing
    And I do a bit wrong
    Then I should see nothing

  @lazy
  Scenario: Both lazy and non-lazy assert
    Given I am in the system
    When I do a bit wrong
    And I do wrong
    Then I should see nothing