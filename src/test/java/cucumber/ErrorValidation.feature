
@tag
Feature: Error Validation
  I want to use this template for my feature file

  @ErrorValidationTest
  Scenario Outline: Negative Test for Login Error page
    Given I landed on Ecommerce Page
    And Logged in with username <name> and password <password>
    Then "Incorrect email or password." message is displayed 

    Examples: 
      | name  							| 	password 		| 
      | aruvaidya@gmail.com |  	Test123			| 
