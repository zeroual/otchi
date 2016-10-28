Feature: user fetch his feed

  Background:
    Given time now is "2016-07-13 06:48:21"
    And following users exist:
      | username        | firstName | lastName |
      | bob@gmail.com   | bob       | BOB      |
      | alice@gmail.com | alice     | ALICE    |

    And those stories
      | content                             | author          |
      | this recipe is both tasty and cheap | alice@gmail.com |

    And those recipes
      | title             | description                    | cookTime | preparationTime | author        |
      | Italian Beef Stew | This hearty beef and vegetable | 60       | 45              | bob@gmail.com |

    And those recipe ingredients
      | recipeId | name  | quantity | unit |
      | 2        | meet  | 1        | kg   |
      | 2        | onion | 200      | g    |

    And those recipe instructions
      | recipeId | instruction |
      | 2        | start with  |

    And I sign in as "bob@gmail.com"

  Scenario: fetch all stories and recipes
    When client send GET request "/rest/v1/feed"
    Then the response code should be "200"
    And the response body should be
    """
  [
      {
          "id": 1,
          "author": {
              "id": 2,
              "firstName": "alice",
              "lastName": "ALICE",
              "picture": "/assets/images/unknown_user.png"
          },
          "createdTime": "2016-07-13 06:48:21",
          "content": {
              "type": "STORY",
              "content": "this recipe is both tasty and cheap",
              "images": []
          },
          "likes": [],
          "comments": [],
          "liked": false,
          "canBeRemoved":false
      },
      {
          "id": 2,
          "author": {
              "id": 1,
              "firstName": "bob",
              "lastName": "BOB",
              "picture": "/assets/images/unknown_user.png"
          },
          "createdTime": "2016-07-13 06:48:21",
          "content": {
              "type": "RECIPE",
              "id": 2,
              "description": "This hearty beef and vegetable",
              "cookTime": 60,
              "preparationTime": 45,
              "ingredients": [
                  {
                      "id": 1,
                      "name": "meet",
                      "quantity": 1.0,
                      "unit": "kg"
                  },
                  {
                      "id": 2,
                      "name": "onion",
                      "quantity": 200.0,
                      "unit": "g"
                  }
              ],
              "instructions": [
                  {
                      "id": 1,
                      "content": "start with"
                  },
                  {
                      "id": 1,
                      "content": "start with"
                  }
              ],
              "title": "Italian Beef Stew",
              "images": []
          },
          "likes": [],
          "comments": [],
          "liked": false,
          "canBeRemoved":true
      }
  ]
    """
