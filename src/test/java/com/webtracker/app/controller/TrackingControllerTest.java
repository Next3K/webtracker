package com.webtracker.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static com.webtracker.app.dataForTests.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

class TrackingControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void should_throw_exception_when_technologies_are_null() {

        given().body(trackUserDto_noTechnologies)
                .contentType("application/json")
                .when()
                .post("/track")
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("errors[0]", equalTo("technologies: must not be null"));
    }

    @Test
    void should_throw_exception_when_client_is_null() {

        given().body(trackUserDto_noClient)
                .contentType("application/json")
                .when()
                .post("/track")
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("errors[0]", equalTo("client: must not be null"));
    }

    @Test
    void should_throw_exception_when_githubUsername_is_null() {

        given().body(trackUserDto_noGithubUsername)
                .contentType("application/json")
                .when()
                .post("/track")
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("errors[0]", equalTo("githubUsername: must not be blank"));
    }

    @Test
    void should_throw_exception_when_observerType_is_null() {

        given().body(trackUserDto_noObserverType)
                .contentType("application/json")
                .when()
                .post("/track")
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("errors[0]", equalTo("observerType: must not be null"));
    }

    @Test
    void should_throw_exception_when_observer_type_is_incorrect() {

        given().body(trackUserDto_incorrectObserverType)
                .contentType("application/json")
                .when()
                .post("/track")
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("exceptionMessage", equalTo("Observer type not supported"));
    }

    @Test
    void should_throw_exception_when_try_delete_user_not_found() {

        given().body(deleteUserDto)
                .contentType("application/json")
                .when()
                .delete("/track")
                .then()
                .statusCode(404)
                .contentType("application/json")
                .body("exceptionMessage", equalTo("User not found"));
    }


}
