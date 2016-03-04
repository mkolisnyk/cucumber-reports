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

  Scenario Outline: Portfolio Positions - Sort
    Given I log into FSAI with any user
    When I navigate to PortFolio Positions
    And I select <allocation> for the Portfolio Positions grid
    Then I verify sort Portfolio Positions
    @RT-01503
    Examples:
        | allocation   |
        | Counterparty |    
    @RT-01508
    Examples:
        | allocation   |
        | Industry     |
    @RT-01509
    Examples:
        | allocation   |
        | Issuer       | 
    @RT-01505
    Examples:
        | allocation   |
        | Product      |
    @RT-01507
    Examples:
        | allocation   |
        | Sector       |   
    @RT-01506
    Examples:
        | allocation   |
        | Strategy     |   