Feature: posting story

  Background:
    Given time now is "2016-07-13 06:48:21"
    And following users exist:
      | username        | firstName | lastName |
      | bob@gmail.com   | bob       | BOB      |
      | alice@gmail.com | alice     | ALICE    |
    And I sign in as "bob@gmail.com"

  Scenario: posting new story with images
    Given client upload those files :
      | name     | fileName  | type       |
      | pictures | food1.jpg | image/jpeg |
      | pictures | food2.jpg | image/jpeg |
    And upload json file "story" with following content
    """
      {
        "content":"i am so happy that i found this restaurant"
      }
    """
    When client send uploaded files to "/rest/v1/post/story"
    Then the response code should be "201"
    And the response body should be
    """
     {
      "id": 1,
      "author": {
          "firstName": "bob",
          "lastName": "BOB"
      },
      "createdTime": "2016-07-13 06:48:21",
      "content": {
          "type": "STORY",
          "content": "i am so happy that i found this restaurant"
      },
      "likes": [],
      "comments": [],
      "liked": false,
    }
    """
