package com.project.bookingya.screenplay.tasks;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/* Clase: CrearRoom
   Objetivo:
   Representar la acción de crear una habitación en el sistema.

   Esta tarea permite generar una habitación disponible con datos dinámicos
   y almacenar su ID en la memoria del actor para futuras operaciones.
*/

public class CrearRoom implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        long unique = System.currentTimeMillis();

        String body = "{"
                + "\"code\": \"HAB-" + unique + "\","
                + "\"name\": \"Habitacion QA Screenplay\","
                + "\"city\": \"Medellin\","
                + "\"maxGuests\": 3,"
                + "\"nightlyPrice\": 150000,"
                + "\"available\": true"
                + "}";

        Response response = SerenityRest.given()
                .contentType("application/json")
                .body(body)
                .post("/room");

        String roomId = response.jsonPath().getString("id");

        if (roomId == null) {
            throw new RuntimeException(
                    "No se pudo crear la habitacion. Status: " + response.statusCode()
                            + " Body: " + response.asString()
            );
        }

        actor.remember("roomId", roomId);
        actor.remember("roomResponse", response);
    }

    public static CrearRoom disponible() {
        return Tasks.instrumented(CrearRoom.class);
    }
}