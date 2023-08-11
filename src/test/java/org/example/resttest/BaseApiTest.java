package org.example.resttest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseApiTest {
    private static final String URL = "https://reqres.in";
    public static RequestSpecification getRequestBaseSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .build();
    }
    public static ResponseSpecification getResponseBase200Spec(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();
    }
    public static ResponseSpecification getResponseBase201Spec(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(201)
                .build();
    }
    public static void setBaseRestAssuredSpec(){
        RestAssured.requestSpecification = getRequestBaseSpec();
        RestAssured.responseSpecification = getResponseBase200Spec();
    }
    public static void setBaseRestAssuredCreateSpec(){
        RestAssured.requestSpecification = getRequestBaseSpec();
        RestAssured.responseSpecification = getResponseBase201Spec();
    }
}
