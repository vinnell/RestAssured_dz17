package org.example.resttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqResApiTest {
    @Test
    public void listResourceGetTest() {
        BaseApiTest.setBaseRestAssuredSpec();
        List<UserObject> nameOfColors = RestAssured.
                given()
                .when()
                .get("/api/unknown")
                .then()
                .extract()
                .jsonPath()
                .getList("data", UserObject.class);
         Assertions.assertTrue(nameOfColors.stream().anyMatch(e ->e.getName().contains("blue turquoise")));
        Assertions.assertTrue(nameOfColors.size() > 0);
    }
            @Test
        public void createUserPostTest(){
            BaseApiTest.setBaseRestAssuredCreateSpec();
            CreateObj createCredentials = new CreateObj("morpheus","leader");
        given()
                .when()
                .body(createCredentials)
                .post("/api/users")
                .then()
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("leader"));
    }

    @Test
    public void loginSuccessfulPostTest() throws IOException {
        File userJson = new File("src/test/java/org/example/resttest/user.json");
        LoginSuccessObj loginUser = new ObjectMapper().readValue(userJson, LoginSuccessObj.class);
        BaseApiTest.setBaseRestAssuredSpec();
              given()
                .when()
                .body(userJson)
                .post("/api/login")
                .then()
                .body("token", Matchers.equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void updateUserPutTest(){
        BaseApiTest.setBaseRestAssuredSpec();
        UpdateObj updateCred = new UpdateObj("morpheus","zion resident");
        given()
                .when()
                .body(updateCred)
                .put("/api/users/2")
                .then()
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("zion resident"));
    }
    @Test
    public void deleteUserTest(){

        RequestSpecification request = RestAssured.given()
                .when()
                .accept(ContentType.JSON)
                .baseUri("https://reqres.in/api/users/2");

        Response response = request.request(Method.DELETE);
        Assertions.assertEquals(response.getStatusCode(), 204);
    }
    }


