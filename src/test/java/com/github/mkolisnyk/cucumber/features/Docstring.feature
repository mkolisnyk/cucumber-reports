@docstring
Feature: Tests with docstrings

  Scenario: Sample
    When I use the following text:
      """
      Some multiline
      docstring text
      """
    Then I should see the following text:
      """
      Another multiline
      docstring output
      """

