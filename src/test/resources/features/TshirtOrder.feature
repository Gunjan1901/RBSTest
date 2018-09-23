Feature: Tshirt Order
  
 The purpose of this feature is to validate the order of tshirt
  
  As a tester
  I want to validate the online site
  In order to ensure that the tshirt is ordered and details present in order history
   
Scenario Outline: Validate the tshirt is ordered from XYZ Site and updated in order history
Given user opens browser <browser> and enters <url>
And  enters username <username> and password <password>
And the user is logged in to the site
When the user selects a tshirt 
And orders the same after making payment
Then the current order is present in order history 
And the browser is closed

Examples:
|browser|url|username|password|
|"chrome"|"http://automationpractice.com"|"someone@example.com"|"Password123"|