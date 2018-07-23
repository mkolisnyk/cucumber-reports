Feature: Retry check with custom exception
	Scenario: Throwing exception 1
		When I throw "Exception1" exception
		Then I should see nothing
		
	Scenario: Throwing exception 2
		When I throw "Exception2" exception
		Then I should see nothing
		
	Scenario: Throwing exception 3
		When I throw "Exception3" exception
		Then I should see nothing		
		
	Scenario Outline: Throwing exceptions
		When I throw "<Exception>" exception
		Then I should see nothing
		
		Examples:
			| Exception  |
			| Exception1 |
			| Exception2 |
			| Exception3 |
		
	Scenario Outline: Retries count
		When I do retry after <Count> fails
		Then I should see nothing
		
		Examples:
			| Count |
			| 2     |
			| 1     |
			| 0     |