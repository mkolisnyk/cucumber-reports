Feature: Tests with backgrounds

  Background: 
    Given I am in the system
    When I do nothing

  @passed_background
  Scenario: Always passed test
    When I do nothing
    Then I should see nothing

    
  @passed_background
  Scenario: Always passed test 2
    When I do nothing
    Then I should see nothing

  @passed_background
  Scenario: Always failing test 2
    When I do wrong
    Then I should see nothing 