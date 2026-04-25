package com.project.bookingya.screenplay.tasks;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/* Clase: CrearGuest
   Objetivo:
   Representar la acción de crear un huésped en el sistema.

   Esta tarea permite generar un guest con datos dinámicos y almacenar
   su ID en la memoria del actor para ser utilizado en otras operaciones.
*/

public class CrearGuest implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        long unique = System.currentTimeMillis();

        String body = "{"
                + "\"identification\": \"QA-" + unique + "\","
                + "\"name\": \"QA User\","
                + "\"email\": \"qa" + unique + "@bookingya.com\""
                + "}";

        Response response = SerenityRest.given()
                .contentType("application/json")
                .body(body)
                .post("/guest");

        String guestId = response.jsonPath().getString("id");

        if (guestId == null) {
            throw new RuntimeException(
                    "No se pudo crear el guest. Status: " + response.statusCode()
                            + " Body: " + response.asString()
            );
        }

        actor.remember("guestId", guestId);
        actor.remember("guestResponse", response);
    }

    public static CrearGuest enSistema() {
        return Tasks.instrumented(CrearGuest.class);
    }
}