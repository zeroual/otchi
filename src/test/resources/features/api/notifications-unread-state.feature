Feature: Notifications

  Background:
    Given time now is "2016-07-13 06:48:21"
    And following users exist:
      | username        | firstName | lastName |
      | bob@bob.com     | bob       | BOB      |
      | alice@alice.com | alice     | ALICE    |
    And I sign in as "bob@bob.com"
    And those notifications:
      | type            | username        | unread | postId | sender          | creationDate        |
      | LIKED           | bob@bob.com     | true   | 1      | alice@alice.com | 2016-07-13 06:48:21 |
      | MENTIONED       | bob@bob.com     | true   | 2      | alice@alice.com | 2016-07-13 06:48:22 |
      | ALSO_COMMENTED  | alice@alice.com | true   | 3      | bob@bob.com     | 2016-07-13 06:48:23 |
      | COMMENT_ON_POST | bob@bob.com     | false  | 4      | alice@alice.com | 2016-07-13 06:48:24 |

  Scenario: mark notification as read
    When client request "PUT" "/rest/v1/me/notifications/1" with json data:
    """
      {
        "unread": "false"
      }
    """
    Then the response code should be "200"
    And  notification with id "1" is marked as read

  Scenario: mark notification as unread
    When client request "PUT" "/rest/v1/me/notifications/4" with json data:
    """
      {
        "unread": "true"
      }
    """
    Then the response code should be "200"
    And  notification with id "4" is marked as unread