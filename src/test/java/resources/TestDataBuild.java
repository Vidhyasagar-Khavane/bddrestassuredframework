package resources;

import pojo.AddPlacePojo;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {
    public AddPlacePojo addPlace(String name, String language, String address){
        AddPlacePojo addPlaceJson=new AddPlacePojo();
        Location location=new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlaceJson.setLocation(location);
        addPlaceJson.setAccuracy(50);
        addPlaceJson.setName(name);
        addPlaceJson.setPhone_number("(+91)983 893 3937");
        addPlaceJson.setAddress(address);
        List<String> typesList=new ArrayList<>();
        typesList.add("shoe park");
        typesList.add("shop");
        addPlaceJson.setTypes(typesList);
        addPlaceJson.setWebsite("http://google.com");
        addPlaceJson.setLanguage(language);
        return addPlaceJson;
    }

    public String deletePlaceData(String placeId)
    {
        return "{\n" +
                " \"place_id\"=\""+placeId+"\"   \n" +
                "}";
    }}

