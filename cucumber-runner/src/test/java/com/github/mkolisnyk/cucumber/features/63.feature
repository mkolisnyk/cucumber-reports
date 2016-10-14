@passed @63
Feature: Overview chart check

  Background:
  	When I do wrong
  Scenario Outline: Always failed test
    Then I should see nothing
  Examples:
  	| Value |
  	| 1 |
  	| 2 |
  	| 3 |
  	| 4 |
  	| 5 |
  	| 6 |