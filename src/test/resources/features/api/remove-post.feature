Feature: remove  post

  Background:
    Given time now is "2016-07-13 06:48:21"
    And following users exist:
      | username        | firstName | lastName |
      | bob@gmail.com   | bob       | BOB      |
      | alice@gmail.com | alice     | ALICE    |

    And those stories
      | content                             | author        |
      | this recipe is both tasty and cheap | bob@gmail.com |

  Scenario: user remove his own post
    And I sign in as "bob@gmail.com"
    When client request "DELETE" "/rest/v1/feed/1"
    Then the response code should be "200"
    And the post with id 1 is removed

  Scenario: user try to remove his not belong to him
    And I sign in as "alice@gmail.com"
    When client request "DELETE" "/rest/v1/feed/1"
    Then the response code should be "401"

  Scenario: user try to remove non-existent post
    And I sign in as "alice@gmail.com"
    When client request "DELETE" "/rest/v1/feed/193"
    Then the response code should be "404"
