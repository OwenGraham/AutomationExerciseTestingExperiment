Feature: As a customer, I want to be able to navigate to certain product categories so that I can find a specific kind of product.
  Scenario: Navigate to a product category
    Given I am on the home page
    When I click on a product category link in the navigation menu (e.g., "Women -> Dress")
    Then I should be redirected to the "Women's Dresses" category page
    And The heading above the products should say "WOMEN - DRESS PRODUCTS"
    And the products related to the "Women" category should be displayed