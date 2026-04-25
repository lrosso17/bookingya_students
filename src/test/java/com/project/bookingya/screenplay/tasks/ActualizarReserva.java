package com.project.bookingya.screenplay.tasks;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/* Clase: ActualizarReserva
   Objetivo:
   Representar la acción de actualizar una reserva existente en el sistema.

   Esta tarea permite al actor modificar los datos de una reserva utilizando
   el ID almacenado previamente en su memoria y ejecutando una petición PUT.
*/

public class ActualizarReserva implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        String reservationId = actor.recall("reservationId");
        String guestId       = actor.recall("guestId");
        String roomId        = actor.recall("roomId");

        if (reservationId == null) {
            throw new RuntimeException("No hay reservationId en memoria del actor");
        }

        String body = "{"
                + "\"guestId\": \"" + guestId + "\","
                + "\"roomId\": \"" + roomId + "\","
                + "\"checkIn\": \"2026-08-10T14:00:00\","
                + "\"checkOut\": \"2026-08-15T12:00:00\","
                + "\"guestsCount\": 1,"
                + "\"notes\": \"Reserva actualizada por Screenplay\""
                + "}";

        Response response = SerenityRest.given()
                .contentType("application/json")
                .body(body)
                .put("/reservation/" + reservationId);

        actor.remember("lastResponse", response);
    }

    public static ActualizarReserva conDatos() {
        return Tasks.instrumented(ActualizarReserva.class);
    }
}