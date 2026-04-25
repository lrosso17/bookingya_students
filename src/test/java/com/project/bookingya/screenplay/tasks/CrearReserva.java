package com.project.bookingya.screenplay.tasks;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/* Clase: CrearReserva
   Objetivo:
   Representar la acción de crear una reserva en el sistema.

   Esta tarea utiliza el guestId y roomId almacenados en la memoria
   del actor para generar una reserva válida mediante una petición POST.
*/

public class CrearReserva implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        String guestId = actor.recall("guestId");
        String roomId = actor.recall("roomId");

        if (guestId == null || roomId == null) {
            throw new RuntimeException("guestId o roomId son null");
        }

        String body = "{"
                + "\"guestId\": \"" + guestId + "\","
                + "\"roomId\": \"" + roomId + "\","
                + "\"checkIn\": \"2026-07-01T14:00:00\","
                + "\"checkOut\": \"2026-07-05T12:00:00\","
                + "\"guestsCount\": 2,"
                + "\"notes\": \"Reserva Screenplay\""
                + "}";

        Response response = SerenityRest.given()
                .contentType("application/json")
                .body(body)
                .post("/reservation");

        String reservationId = response.jsonPath().getString("id");

        actor.remember("reservationId", reservationId);
        actor.remember("lastResponse", response);
    }

    public static CrearReserva conDatos() {
        return Tasks.instrumented(CrearReserva.class);
    }
}