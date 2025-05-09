Feature: Validating Place API's

  @AddPlaceAPI @Regression
  Scenario Outline: Verify is Place is being Successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When User calls "addPlaceAPI" with "POST" http request
    Then The API call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      | name    | language | address            |
      | AAHouse | English  | World cross center |
  #    | BBHouse | French-IN | Lane 4, Cross Street cafe|

  @DeletePlaceAPI @Regression
  Scenario: Verify the Delete Place API
    Given Delete Place API with PlaceId
    When User calls "deletePlaceAPI" with "POST" http request
    Then The API call is success with status code 200