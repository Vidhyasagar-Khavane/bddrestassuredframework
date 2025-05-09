package stepDefinitions;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import pojo.AddPlacePojo;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinitions extends Utils {
    TestDataBuild data=new TestDataBuild();
    RequestSpecification reqspec;
    ResponseSpecification resspec;
    Response response;

    public static String placeId;


    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload(String name, String lanugage, String address) {

       reqspec= given().spec(requestSpecifications()).body(data.addPlace(name, lanugage, address));
    }
    @When("User calls {string} with {string} http request")
    public void user_calls_http_request(String resource, String method) {
        APIResources resources=APIResources.valueOf(resource);
      resspec=  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

      if(method.equalsIgnoreCase("POST")){
          response=  reqspec.when().post(resources.getResource());
      }else if(method.equalsIgnoreCase("GET")){
          response=  reqspec.when().get(resources.getResource());
      }else if(method.equalsIgnoreCase("DELETE")){
          response=  reqspec.when().delete(resources.getResource());
      }
//      response=  reqspec.when().post(resources.getResource()).then().spec(resspec).extract().response();

    }
    @Then("The API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(int statusCode) {
        assertEquals(response.getStatusCode(), statusCode);
    }
    @Then("{string} in response body is {string}")
    public void verifyFieldsInResponseBody(String key, String value) {
        assertEquals(getJsonResponse(response, key), value);
    }
    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_placeId_created_maps_using(String name, String resource){
        placeId = getJsonResponse(response, "place_id");
        reqspec.queryParam("place_id", placeId);
        user_calls_http_request(resource, "GET");
        assertEquals(name, getJsonResponse(response, "name"));
    }
    @Given("Delete Place API with PlaceId")
    public void delete_Place_API_with_PlaceId(){
        reqspec= given().spec(requestSpecifications()).body(data.deletePlaceData(placeId));
        user_calls_http_request("getPlaceAPI", "GET");
    }

}
