package com.project.bookingya.screenplay.actors;

import com.project.bookingya.screenplay.abilities.CallBookingApi;
import io.restassured.RestAssured;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

public class BookingActor {

    private static final String BASE_URL = "http://localhost:8080/api";

    public static Actor qaUser() {
        RestAssured.baseURI = BASE_URL;

        return Actor.named("QA User")
                .whoCan(CallAnApi.at(BASE_URL))
                .whoCan(CallBookingApi.at(BASE_URL));
    }
}