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

  Scenario: get all notifications
    When client send GET request "/rest/v1/me/notifications"
    Then the response code should be "200"
    And the response body should be
    """
     [
      {
        "id": 1,
        "type": "LIKED",
        "createdAt" : "2016-07-13 06:48:21",
        "sender": {
            "id": 2,
            "firstName": "alice",
            "lastName": "ALICE"
        },
        "postId": 1
      },
      {
        "id": 2,
        "type": "MENTIONED",
        "createdAt" : "2016-07-13 06:48:22",
        "sender": {
            "id": 2,
            "firstName": "alice",
            "lastName": "ALICE"
        },
        "postId": 2
      },
      {
        "id": 4,
        "type": "COMMENT_ON_POST",
        "createdAt" : "2016-07-13 06:48:24",
        "sender": {
            "id": 2,
            "firstName": "alice",
            "lastName": "ALICE"
        },
        "postId": 4
      }

     ]
    """

  Scenario: get all unread notifications
    When client send GET request "/rest/v1/me/notifications?unread=true"
    Then the response code should be "200"
    And the response body should be
    """
     [
      {
        "id":1,
        "type":"LIKED",
        "createdAt" : "2016-07-13 06:48:21",
        "sender": {
            "id": 2,
            "firstName": "alice",
            "lastName": "ALICE"
        },
        "postId": 1
      },
      {
        "id":2,
        "type":"MENTIONED",
        "createdAt" : "2016-07-13 06:48:22",
        "sender": {
            "id": 2,
            "firstName": "alice",
            "lastName": "ALICE"
        },
        "postId": 2
      }
     ]
    """
