@passed
Feature: Lazy assert feature

  @lazy
  Scenario: Passed scenario
    Given I am in the system
    Then I should see nothing

  @lazy
  Scenario: Ambiguous step
    Given I am in the system
    Then I should see nothing
	And I should see ambiguous step

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
    And I do pending wrong
    And I do wrong
    Then I should see nothing
    
  @lazy
  Scenario: Another lazy assert
    Given I am in the system
    When I do another pending wrong
    And I do wrong
    Then I should see nothing
    
  @lazy
  Scenario: Undefined step
    Given I am in the system
    When I do other pending wrong
    And I do wrong
    Then I should see nothing
    
  @lazy
  Scenario: Undefined step with error
    Given I am in the system
    When I do a bit wrong
    And I do other pending wrong
    And I do wrong
    Then I should see nothing