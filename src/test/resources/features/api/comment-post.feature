Feature: comment on post

  Background:
    Given time now is "2016-07-13 06:48:21"
    And following users exist:
      | username        | firstName | lastName |
      | bob@gmail.com   | bob       | BOB      |
      | alice@gmail.com | alice     | ALICE    |
    And I sign in as "bob@gmail.com"

  Scenario: posting new story with images
    Given those stories
      | content                             | author          |
      | this recipe is both tasty and cheap | alice@gmail.com |
    When client request "POST" "/rest/v1/feed/1/comment" with json data:
    """
      this is a delicious meal
    """
    Then the response code should be "201"
    And the response body should be
    """
        {
            "id": null,
            "author": {
                "id": 1,
                "firstName": "bob",
                "lastName": "BOB",
                "picture": "/assets/images/unknown_user.png"
            },
            "content": "  this is a delicious meal",
            "createdOn": "2016-07-13 06:48:21"
        }
    """
    And the comment of "bob@gmail.com" should be attached to the post with id "1"
    And commented on post notification was sent to post author "alice@gmail.com"