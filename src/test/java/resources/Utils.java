package resources;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static  RequestSpecification req;

    public RequestSpecification requestSpecifications() {
        if(req==null){
            RestAssured.baseURI = "https://rahulshettyacademy.com";
            PrintStream file=generateLogFile();
            req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(file))
                    .addFilter(ResponseLoggingFilter.logResponseTo(file))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public PrintStream generateLogFile() {
        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = now.format(dtf);

        try {
            PrintStream file = new PrintStream(new FileOutputStream("logs_"+date+".txt"));
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getJsonResponse(Response response, String key){
        JsonPath js=new JsonPath(response.asString());
        return js.get(key).toString();
    }

}

