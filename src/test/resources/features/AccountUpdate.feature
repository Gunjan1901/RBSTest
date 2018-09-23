Feature: Account Update
  
 The purpose of this feature is to update first name of the user in My account
  
  As a tester
  I want to validate the first name of the user 
  In order to ensure that the first name is updated in My account section
   
Scenario Outline: Validate the first name is updated in My account section
Given user opens browser <browser> and enters <url>
And  enters username <username> and password <password>
And the user is logged in to the site
And the user clicks on My Personal Information tab in Homepage
When the user updates the first name <fName>
And clicks on save button
Then the first name is updated
And the browser is closed

Examples:
|browser|url|username|password|fName|
|"chrome"|"http://automationpractice.com"|"someone@example.com"|"Password123"|"Gunjan"|