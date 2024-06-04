Feature: As a shopper, I want to view information about an item, so that I can decide whether I want to buy it
  Scenario: User wants to view a specific item details

    Given I am browsing items
    When I click on view product
    Then I am taken to the details page

  Scenario: Details page displays the relevant information

    Given I am browsing items
    When I click on view product
    Then I see the item image, name, availability, condition, brand, and price.