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