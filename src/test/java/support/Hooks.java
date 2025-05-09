package support;


import io.cucumber.java.Before;
import stepDefinitions.StepDefinitions;

public class Hooks {

    StepDefinitions m=new StepDefinitions();
    @Before("@DeletePlaceAPI")
    public void beforeScenario(){
        m.add_place_payload("Vidhya", "Marathi","Devgaon");
        m.user_calls_http_request("AddPlaceAPI","POST");
        m.verify_placeId_created_maps_using("Vidhya","GetPlaceAPI");
    }
}
